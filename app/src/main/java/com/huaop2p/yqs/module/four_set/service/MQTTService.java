package com.huaop2p.yqs.module.four_set.service;

/**
 * Created by lenovo on 2015/7/10.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.R;

import com.huaop2p.yqs.module.base.bases.PreferencesUtils;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.base.entity.BusConstants;
import com.huaop2p.yqs.module.four_set.activity.MessageActivity;
import com.huaop2p.yqs.module.four_set.entity.ResPushMsg;
import com.huaop2p.yqs.utils.DigestUtils;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.lidroid.xutils.util.LogUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class MQTTService extends Service {
    private static final String TAG = "MQTTService";

    // something unique to identify your app - used for stuff like accessing
    //   application preferences
    public static final String APP_ID = "com.huao.yupen_three.module.four_set.service";

    // constants used to notify the Activity UI of received messages
    public static final String MQTT_MSG_RECEIVED_INTENT = "com.huao.yupen_three.module.four_set.service.MSGRECVD";
    public static final String MQTT_MSG_RECEIVED_TOPIC = "com.huao.yupen_three.module.four_set.service.MSGRECVD_TOPIC";
    public static final String MQTT_MSG_RECEIVED_MSG = "com.huao.yupen_three.module.four_set.service.MSGRECVD_MSGBODY";

    // constants used to tell the Activity UI the connection status
    public static final String MQTT_STATUS_INTENT = "com.huao.yupen_three.module.four_set.service.STATUS";
    public static final String MQTT_STATUS_MSG = "com.huao.yupen_three.module.four_set.service.STATUS_MSG";

    // constant used internally to schedule the next ping event
    public static final String MQTT_PING_ACTION = "com.huao.yupen_three.module.four_set.service.PING";

    // constants used by status bar notifications
    public static final int MQTT_NOTIFICATION_ONGOING = 1;
    public static int MQTT_NOTIFICATION_UPDATE = 2;

    // constants used to define MQTT connection status
    public enum MQTTConnectionStatus {
        INITIAL,                            // initial status
        CONNECTING,                         // attempting to connect
        CONNECTED,                          // connected
        NOTCONNECTED_WAITINGFORINTERNET,    // can't connect because the phone
        //     does not have Internet access
        NOTCONNECTED_USERDISCONNECT,        // user has explicitly requested
        //     disconnection
        NOTCONNECTED_DATADISABLED,          // can't connect because the user
        //     has disabled data access
        NOTCONNECTED_UNKNOWNREASON          // failed to connect for some reason
    }

    // MQTT constants
    public static final int MAX_MQTT_CLIENTID_LENGTH = 22;


    // status of MQTT client connection
    private MQTTConnectionStatus connectionStatus = MQTTConnectionStatus.INITIAL;


    // taken from preferences
    //    host name of the server we're receiving push notifications from
    private String brokerHostName = "";
    // taken from preferences
    //    topic we want to receive messages about
    //    can include wildcards - e.g.  '#' matches anything
    private String [] topicName;


    // defaults - this sample uses very basic defaults for it's interactions
    //   with message brokers
    private int brokerPortNumber = 1883;
    private boolean cleanStart = false;
    private int[] qualitiesOfService = {1, 1};

    //  how often should the app ping the server to keep the connection alive?
    //
    //   too frequently - and you waste battery life
    //   too infrequently - and you wont notice if you lose your connection
    //                       until the next unsuccessfull attempt to ping
    //
    //   it's a trade-off between how time-sensitive the data is that your
    //      app is handling, vs the acceptable impact on battery life
    //
    //   it is perhaps also worth bearing in mind the network's support for
    //     long running, idle connections. Ideally, to keep a connection open
    //     you want to use a keep alive value that is less than the period of
    //     time after which a network operator will kill an idle connection
    private short keepAliveSeconds = 2 * 60;


    // This is how the Android client app will identify itself to the
    //  message broker.
    // It has to be unique to the broker - two clients are not permitted to
    //  connect to the same broker using the same client ID.
    private static String mqttClientId = null;

    public static String getMqttClientId() {
        return mqttClientId;
    }

    // connection to the message broker
    private MqttClient mqttClient = null;

    // receiver that notifies the Service when the phone gets data connection
    private NetworkConnectionIntentReceiver netConnReceiver;

    // receiver that notifies the Service when the user changes data use preferences
    private BackgroundDataChangeIntentReceiver dataEnabledReceiver;

    // receiver that wakes the Service up when it's time to ping the server
    private PingSender pingSender;


    // see http://developer.android.com/guide/topics/fundamentals.html#lcycles

    @Override
    public void onCreate() {
        super.onCreate();

        // reset status variable to initial state
        connectionStatus = MQTTConnectionStatus.INITIAL;

        // create a binder that will let the Activity UI send
        //   commands to the Service
        mBinder = new LocalBinder<MQTTService>(this);

        // get the broker settings out of app preferences
        //   this is not the only way to do this - for example, you could use
        //   the Intent that starts the Service to pass on configuration values
//        SharedPreferences settings = getSharedPreferences(APP_ID, MODE_PRIVATE);
//        brokerHostName = settings.getString("broker", "");
//        topicName      = settings.getString("topic",  "");
        brokerHostName ="219.143.6.71";//"106.39.123.232";//"106.39.123.229";// "192.168.11.35";//  "219.143.6.81";// "219.143.6.81";//
        //192.168.11.88
        // "115.28.86.141";
        // "10.0.1.20"; //这里改成你自己的服务器IP地址
        topicName = new String[]{"broadcast","/Android/"+generateClientId()};    //这里改成你想要subscribe的topic

        Log.i(TAG, "onCreate");
        LogUtils.e("id "+generateClientId());
        // register to be notified whenever the user changes their preferences
        //  relating to background data use - so that we can respect the current
        //  preference
        dataEnabledReceiver = new BackgroundDataChangeIntentReceiver();
        registerReceiver(dataEnabledReceiver,
                new IntentFilter(ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // define the connection to the broker
                defineConnectionToBroker(brokerHostName);
            }
        }).start();
    }




    // Static method to start the service
    public static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, MQTTService.class);
        //i.setAction(ACTION_START);
        ctx.startService(i);
    }

    @Override
    public void onStart(final Intent intent, final int startId) {
        Log.i(TAG, "onStart");
        // This is the old onStart method that will be called on the pre-2.0
        // platform.  On 2.0 or later we override onStartCommand() so this
        // method will not be called.

        new Thread(new Runnable() {
            @Override
            public void run() {
                handleStart(intent, startId);
            }
        }, "MQTTservice").start();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, final int startId) {
        Log.i(TAG, "onStartCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {
                handleStart(intent, startId);
            }
        }, "MQTTservice").start();

        // return START_NOT_STICKY - we want this Service to be left running
        //  unless explicitly stopped, and it's process is killed, we want it to
        //  be restarted
        return START_STICKY;
    }

    synchronized void handleStart(Intent intent, int startId) {
        // before we start - check for a couple of reasons why we should stop
        Log.i(TAG, "handleStart");
        if (mqttClient == null) {
            Log.i(TAG, "mqttClient === null");
            // we were unable to define the MQTT client connection, so we stop
            //  immediately - there is nothing that we can do
            stopSelf();
            return;
        }
        Log.i(TAG, "mqttClient != null");
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getBackgroundDataSetting() == false) // respect the user's request not to use data!
        {
            // user has disabled background data
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_DATADISABLED;

            // update the app to show that the connection has been disabled
            broadcastServiceStatus("Not connected - background data disabled");

            // we have a listener running that will notify us when this
            //   preference changes, and will call handleStart again when it
            //   is - letting us pick up where we leave off now
            return;
        }
        Log.i(TAG, "getBackgroundDataSetting == true");
        // the Activity UI has started the MQTT service - this may be starting
        //  the Service new for the first time, or after the Service has been
        //  running for some time (multiple calls to startService don't start
        //  multiple Services, but it does call this method multiple times)
        // if we have been running already, we re-send any stored data
        rebroadcastStatus();
        rebroadcastReceivedMessages();

        Log.i(TAG, "after rebroadcastReceivedMessages");
        // if the Service was already running and we're already connected - we
        //   don't need to do anything
        if (isAlreadyConnected() == false) {
            Log.i(TAG, "isAlreadyConnected() == false");
            // set the status to show we're trying to connect
            connectionStatus = MQTTConnectionStatus.CONNECTING;

            // we are creating a background service that will run forever until
            //  the user explicity stops it. so - in case they start needing
            //  to save battery life - we should ensure that they don't forget
            //  we're running, by leaving an ongoing notification in the status
            //  bar while we are running
//            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            Notification notification = new Notification(R.mipmap.logo_app_48,
//                    "MQTT",
//                    System.currentTimeMillis());
//            notification.flags |= Notification.FLAG_ONGOING_EVENT;
//            notification.flags |= Notification.FLAG_NO_CLEAR;
//            Intent notificationIntent = new Intent(this, NotifyActivity.class);
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                    notificationIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            notification.setLatestEventInfo(this, "MQTT", "MQTT Service is running", contentIntent);
//            nm.notify(MQTT_NOTIFICATION_ONGOING, notification);


            // before we attempt to connect - we check if the phone has a
            //  working data connection
            if (isOnline()) {
                // we think we have an Internet connection, so try to connect
                //  to the message broker
                if (connectToBroker()) {
                    // we subscribe to a topic - registering to receive push
                    //  notifications with a particular key
                    // in a 'real' app, you might want to subscribe to multiple
                    //  topics - I'm just subscribing to one as an example
                    // note that this topicName could include a wildcard, so
                    //  even just with one subscription, we could receive
                    //  messages for multiple topics
                    subscribeToTopic(topicName);
                }
            } else {
                // we can't do anything now because we don't have a working
                //  data connection
                connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;

                // inform the app that we are not connected
                broadcastServiceStatus("Waiting for network connection");
            }
        }

        // changes to the phone's network - such as bouncing between WiFi
        //  and mobile data networks - can break the MQTT connection
        // the MQTT connectionLost can be a bit slow to notice, so we use
        //  Android's inbuilt notification system to be informed of
        //  network changes - so we can reconnect immediately, without
        //  haing to wait for the MQTT timeout
        if (netConnReceiver == null) {
            netConnReceiver = new NetworkConnectionIntentReceiver();
            registerReceiver(netConnReceiver,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }

        // creates the intents that are used to wake up the phone when it is
        //  time to ping the server
        if (pingSender == null) {
            pingSender = new PingSender();
            registerReceiver(pingSender, new IntentFilter(MQTT_PING_ACTION));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("mqtt onDestroy");
        try {
            unregisterReceiver(dataEnabledReceiver);
            unregisterReceiver(netConnReceiver);
            unregisterReceiver(pingSender);
        }catch (Exception e){

        }

//        Intent svc = new Intent(this, MQTTService.class);
//        startService(svc);
    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        // disconnect immediately
//        disconnectFromBroker();
//
//        // inform the app that the app has successfully disconnected
//        broadcastServiceStatus("Disconnected");
//
//        // try not to leak the listener
//        if (dataEnabledReceiver != null) {
//            unregisterReceiver(dataEnabledReceiver);
//            dataEnabledReceiver = null;
//        }
//
//        if (mBinder != null) {
//            mBinder.close();
//            mBinder = null;
//        }
//    }


    // methods used to notify the Activity UI of something that has happened
    //  so that it can be updated to reflect status and the data received
    //  from the server

    private void broadcastServiceStatus(String statusDescription) {
        // inform the app (for times when the Activity UI is running /
        //   active) of the current MQTT connection status so that it
        //   can update the UI accordingly
        LogUtils.e("connectionLost----------connectionLost  "+statusDescription);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MQTT_STATUS_INTENT);
        broadcastIntent.putExtra(MQTT_STATUS_MSG, statusDescription);
        sendBroadcast(broadcastIntent);
    }

    private void broadcastReceivedMessage(String topic, String message) {
        // pass a message received from the MQTT server on to the Activity UI
        //   (for times when it is running / active) so that it can be displayed
        //   in the app GUI
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MQTT_MSG_RECEIVED_INTENT);
        broadcastIntent.putExtra(MQTT_MSG_RECEIVED_TOPIC, topic);
        broadcastIntent.putExtra(MQTT_MSG_RECEIVED_MSG, message);
        sendBroadcast(broadcastIntent);
    }

    // methods used to notify the user of what has happened for times when
    //  the app Activity UI isn't running

    /**
     * 显示通知
     *
     * @param alert
     * @param title
     * @param body
     */
    private void notifyUser(String alert, String title, String body) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.logo_app_96, alert,
                System.currentTimeMillis());
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.ledARGB = Color.MAGENTA;
        Intent notificationIntent = new Intent(this, MessageActivity.class);
        Bundle pushData = new Bundle();
        pushData.putBoolean(MessageActivity.INTENT_IS_A_MSG, true);
        notificationIntent.putExtras(pushData);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

//        notification.setLatestEventInfo(this, alert, title, contentIntent);
        nm.notify(MQTT_NOTIFICATION_UPDATE++, notification);
    }


    // trying to do local binding while minimizing leaks - code thanks to
    //   Geoff Bruckner - which I found at
    //   http://groups.google.com/group/cw-android/browse_thread/thread/d026cfa71e48039b/c3b41c728fedd0e7?show_docid=c3b41c728fedd0e7

    private LocalBinder<MQTTService> mBinder;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder<S> extends Binder {
        private WeakReference<S> mService;

        public LocalBinder(S service) {
            mService = new WeakReference<S>(service);
        }

        public S getService() {
            return mService.get();
        }

        public void close() {
            mService = null;
        }
    }

    //
    // public methods that can be used by Activities that bind to the Service
    //

    public MQTTConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void rebroadcastStatus() {
        String status = "";

        switch (connectionStatus) {
            case INITIAL:
                status = "Please wait";
                break;
            case CONNECTING:
                status = "Connecting...";
                break;
            case CONNECTED:
                status = "Connected";
                break;
            case NOTCONNECTED_UNKNOWNREASON:
                status = "Not connected - waiting for network connection";
                break;
            case NOTCONNECTED_USERDISCONNECT:
                status = "Disconnected";
                break;
            case NOTCONNECTED_DATADISABLED:
                status = "Not connected - background data disabled";
                break;
            case NOTCONNECTED_WAITINGFORINTERNET:
                status = "Unable to connect";
                break;
        }

        //
        // inform the app that the Service has successfully connected
        broadcastServiceStatus(status);
    }

    public void disconnect() {
        disconnectFromBroker();

        // set status
        connectionStatus = MQTTConnectionStatus.NOTCONNECTED_USERDISCONNECT;

        // inform the app that the app has successfully disconnected
        broadcastServiceStatus("Disconnected");
    }


    public void connectionLost() throws Exception {
        // we protect against the phone switching off while we're doing this
        //  by requesting a wake lock - we request the minimum possible wake
        //  lock - just enough to keep the CPU running until we've finished
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
        wl.acquire();

        LogUtils.e("connectionLost----------connectionLost");
        //
        // have we lost our data connection?
        //

        if (isOnline() == false) {
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;

            // inform the app that we are not connected any more
            broadcastServiceStatus("Connection lost - no network connection");
            LogUtils.e("connectionLost----------connectionLost");
            //
            // inform the user (for times when the Activity UI isn't running)
            //   that we are no longer able to receive messages
//            notifyUser("Connection lost - no network connection",
//                    "MQTT", "Connection lost - no network connection"); 不提示

            //
            // wait until the phone has a network connection again, when we
            //  the network connection receiver will fire, and attempt another
            //  connection to the broker
        } else {
            //
            // we are still online
            //   the most likely reason for this connectionLost is that we've
            //   switched from wifi to cell, or vice versa
            //   so we try to reconnect immediately
            //

            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

            // inform the app that we are not connected any more, and are
            //   attempting to reconnect
            broadcastServiceStatus("Connection lost - reconnecting...");

            // try to reconnect
            if (connectToBroker()) {
                subscribeToTopic(topicName);
            }
        }

        // we're finished - if the phone is switched off, it's okay for the CPU
        //  to sleep now
        wl.release();
    }


    public void publishArrived(String topic, byte[] payloadbytes, int qos, boolean retained) {
        // we protect against the phone switching off while we're doing this
        //  by requesting a wake lock - we request the minimum possible wake
        //  lock - just enough to keep the CPU running until we've finished


        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
        wl.acquire();

        //
        //  I'm assuming that all messages I receive are being sent as strings
        //   this is not an MQTT thing - just me making as assumption about what
        //   data I will be receiving - your app doesn't have to send/receive
        //   strings - anything that can be sent as bytes is valid
        String messageBody = new String(payloadbytes);

        //
        //  for times when the app's Activity UI is not running, the Service
        //   will need to safely store the data that it receives
        if (addReceivedMessageToStore(topic, messageBody)) {
            // this is a new message - a value we haven't seen before

            //
            // inform the app (for times when the Activity UI is running) of the
            //   received message so the app UI can be updated with the new data
            broadcastReceivedMessage(topic, messageBody);

            //
            // inform the user (for times when the Activity UI isn't running)
            //   that there is new data available
            ResPushMsg msgObj = GsonUtils.getDateGson(null).fromJson(messageBody, ResPushMsg.class);
//            notifyUser(getString(R.string.app_name), msgObj.getTitle(), messageBody);
            addPushMsg(msgObj);//添加到本地msg
//            notifyUser(msgObj.getTitle(), msgObj.getMessage(), messageBody);
        }

        // receiving this message will have kept the connection alive for us, so
        //  we take advantage of this to postpone the next scheduled ping
        scheduleNextPing();

        // we're finished - if the phone is switched off, it's okay for the CPU
        //  to sleep now
        wl.release();
    }

    /**
     * 添加消息推送记录
     *
     * @param msg
     */
    private void addPushMsg(ResPushMsg msg) {
        if (msg == null)
            return;
        if (msg.getSendTime() == null || msg.getSendTime().length() == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(BusConstants.date_format);
            Date ddt = new Date();
            msg.setSendTime(formatter.format(ddt));
            Log.i("DatePush", ddt.toString());
        }
        if (PreferencesUtils.getString(this, ShareLocalUser.SHARE_KEY_LOCAL_PUSH_MSG) == null) {
            List<ResPushMsg> meges = new ArrayList<ResPushMsg>();
            meges.add(msg);
            String msgstr = GsonUtils.getDateGson(null).toJson(meges);
            PreferencesUtils.putString(this, ShareLocalUser.SHARE_KEY_LOCAL_PUSH_MSG, msgstr);
        } else {
            String localkMsglist = PreferencesUtils.getString(this, ShareLocalUser
                    .SHARE_KEY_LOCAL_PUSH_MSG);
            Type type = new TypeToken<List<ResPushMsg>>() {
            }.getType();
            List<ResPushMsg> msges = GsonUtils.getDateGson(null).fromJson(localkMsglist, type);
            msges.add(msg);
            String msgstr = GsonUtils.getDateGson(null).toJson(msges);
            PreferencesUtils.putString(this, ShareLocalUser.SHARE_KEY_LOCAL_PUSH_MSG, msgstr);
        }

    }

    private void defineConnectionToBroker(String brokerHostName) {
        String mqttConnSpec = "tcp://" + brokerHostName + ":" + brokerPortNumber;

        Log.d(TAG, mqttConnSpec);
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            // define the connection to the broker
            mqttClient = new MqttClient(mqttConnSpec,generateClientId(),persistence);
            Log.d(TAG, "after createMqttClient:" + mqttConnSpec);
            // register this client app has being able to receive messages
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("android");
            connOpts.setPassword("android".toCharArray());//android@2016
            mqttClient.connect(connOpts);
            mqttClient.subscribe(topicName,new int[]{1,1});
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    LogUtils.e("connectionLost: ");

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    LogUtils.e("received: " + new String(mqttMessage.getPayload()));
                    String messageBody =new String(mqttMessage.getPayload());
                    ResPushMsg msgObj = GsonUtils.getDateGson(null).fromJson(messageBody, ResPushMsg.class);
                    notifyUser(getString(R.string.app_name), msgObj.getTitle(), messageBody);
                    addPushMsg(msgObj);//添加到本地msg
//                    notifyUser(msgObj.getTitle(), msgObj.getMessage(), messageBody);
//                    notifyUser(generateClientId(),"sssss",new String(mqttMessage.getPayload()));
//                    notifyUser("余盆",msgObj.Title,messageBody);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            //  mqttClient.registerSimpleHandler(this);
            Log.d(TAG, "after registerSimpleHandler");
        } catch (MqttException e) {
            Log.i(TAG, "MqttException:" + e.getMessage());

            // something went wrong!
            mqttClient = null;
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

            //
            // inform the app that we failed to connect so that it can update
            //  the UI accordingly
            broadcastServiceStatus("Invalid connection parameters");

            //
            // inform the user (for times when the Activity UI isn't running)
            //   that we failed to connect
//            notifyUser("Unable to connect", "MQTT", "Unable to connect");//不提示
        }
    }

    /**
     * 链接到代理推送服务
     *
     * @return
     */
    private boolean connectToBroker() {
        Log.w(TAG, "try to connect broker:clientid:" + generateClientId());
        try {
            // try to connect
//            mqttClient.connect(generateClientId(), cleanStart, keepAliveSeconds);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("android");
            connOpts.setPassword("android@2016".toCharArray());
            mqttClient.connect(connOpts);


            mqttClient.subscribe(topicName, new int[]{1,1});
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    LogUtils.e("connectionLost: ");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    LogUtils.e("received: " + new String(mqttMessage.getPayload()));
                    String messageBody =new String(mqttMessage.getPayload());
                    Type token=new TypeToken<ResPushMsg>(){}.getType();
                    ResPushMsg msgObj = GsonUtils.getDateGson(null).fromJson(messageBody.trim(), token);
//            notifyUser(getString(R.string.app_name), msgObj.getTitle(), messageBody);
                    addPushMsg(msgObj);//添加到本地msg
                    notifyUser("余盆", msgObj.getTitle(), msgObj.getMessage());
//                    notifyUser(generateClientId(),"732", new String(mqttMessage.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            Log.w(TAG, "after connect");
            //
            // inform the app that the app has successfully connected
            broadcastServiceStatus("Connected");

            // we are connected
            connectionStatus = MQTTConnectionStatus.CONNECTED;

            // we need to wake up the phone's CPU frequently enough so that the
            //  keep alive messages can be sent
            // we schedule the first one of these now
            scheduleNextPing();

            return true;
        } catch (MqttException e) {
            LogUtils.e("connect exception:" + e.getMessage());
            // something went wrong!
//            stopSelf();
//            Intent intent=new Intent(MQTTService.this,MQTTService.class);
//            startService(intent);
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

            //
            // inform the app that we failed to connect so that it can update
            //  the UI accordingly
            broadcastServiceStatus("Unable to connect");

            //
            // inform the user (for times when the Activity UI isn't running)
            //   that we failed to connect
//            notifyUser("Unable to connect", "MQTT", "Unable to connect - will retry later");//不提示

            // if something has failed, we wait for one keep-alive period before
            //   trying again
            // in a real implementation, you would probably want to keep count
            //  of how many times you attempt this, and stop trying after a
            //  certain number, or length of time - rather than keep trying
            //  forever.
            // a failure is often an intermittent network issue, however, so
            //  some limited retry is a good idea
            scheduleNextPing();

            return false;
        }
    }


    private void subscribeToTopic(String [] topicName) {
        boolean subscribed = false;

        if (isAlreadyConnected() == false) {
            // quick sanity check - don't try and subscribe if we
            //  don't have a connection

            Log.e("mqtt", "Unable to subscribe as we are not connected");
        } else {
            try {
//                String[] topics = {"","/18511892429/7404e9a4c3eadf13927fdd"};
                //"/18511892429/6ebabfd8241d804a6cbf61"
                mqttClient.subscribe(topicName, qualitiesOfService);
//                mqttClient.subscribe(new String[]{"/Android/"+generateClientId()}, qualitiesOfService);
                LogUtils.e("devId:"+generateClientId());
                subscribed = true;
            } catch (IllegalArgumentException e) {
                Log.e("mqtt", "subscribe failed - illegal argument", e);
            } catch (MqttException e) {
                Log.e("mqtt", "subscribe failed - MQTT exception", e);
            } catch (Exception e) {
                Log.e("mqtt", "subscribe failed - MQTT not connected", e);
            }
        }

        if (subscribed == false) {
            //
            // inform the app of the failure to subscribe so that the UI can
            //  display an error
            broadcastServiceStatus("Unable to subscribe");

            //
            // inform the user (for times when the Activity UI isn't running)
//            notifyUser("Unable to subscribe", "MQTT", "Unable to subscribe");//不提示
        }
    }


    private void disconnectFromBroker() {
        // if we've been waiting for an Internet connection, this can be
        //  cancelled - we don't need to be told when we're connected now
        try {
            if (netConnReceiver != null) {
                unregisterReceiver(netConnReceiver);
                netConnReceiver = null;
            }

            if (pingSender != null) {
                unregisterReceiver(pingSender);
                pingSender = null;
            }
        } catch (Exception eee) {
            // probably because we hadn't registered it
            Log.e("mqtt", "unregister failed", eee);
        }

        try {
            if (mqttClient != null) {
                mqttClient.disconnect();
            }
        } catch (MqttException e) {
            Log.e("mqtt", "disconnect failed - persistence exception", e);
        } finally {
            mqttClient = null;
        }

        // we can now remove the ongoing notification that warns users that
        //  there was a long-running ongoing service running
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }


    private boolean isAlreadyConnected() {
        return ((mqttClient != null) && (mqttClient.isConnected() == true));
    }

    private class BackgroundDataChangeIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            // we protect against the phone switching off while we're doing this
            //  by requesting a wake lock - we request the minimum possible wake
            //  lock - just enough to keep the CPU running until we've finished
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
            wl.acquire();

            ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (cm.getBackgroundDataSetting()) {
                // user has allowed background data - we start again - picking
                //  up where we left off in handleStart before
                defineConnectionToBroker(brokerHostName);
                handleStart(intent, 0);
            } else {
                // user has disabled background data
                connectionStatus = MQTTConnectionStatus.NOTCONNECTED_DATADISABLED;

                // update the app to show that the connection has been disabled
                broadcastServiceStatus("Not connected - background data disabled");

                // disconnect from the broker
                disconnectFromBroker();
            }

            // we're finished - if the phone is switched off, it's okay for the CPU
            //  to sleep now
            wl.release();
        }
    }


    private class NetworkConnectionIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            // we protect against the phone switching off while we're doing this
            //  by requesting a wake lock - we request the minimum possible wake
            //  lock - just enough to keep the CPU running until we've finished
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
            wl.acquire();

            if (isOnline()) {
                // we have an internet connection - have another try at connecting
                if (connectToBroker()) {
                    // we subscribe to a topic - registering to receive push
                    //  notifications with a particular key
                    subscribeToTopic(topicName);
                }
            }

            // we're finished - if the phone is switched off, it's okay for the CPU
            //  to sleep now
            wl.release();
        }
    }


    private void scheduleNextPing() {
        // When the phone is off, the CPU may be stopped. This means that our
        //   code may stop running.
        // When connecting to the message broker, we specify a 'keep alive'
        //   period - a period after which, if the client has not contacted
        //   the server, even if just with a ping, the connection is considered
        //   broken.
        // To make sure the CPU is woken at least once during each keep alive
        //   period, we schedule a wake up to manually ping the server
        //   thereby keeping the long-running connection open
        // Normally when using this Java MQTT client library, this ping would be
        //   handled for us.
        // Note that this may be called multiple times before the next scheduled
        //   ping has fired. This is good - the previously scheduled one will be
        //   cancelled in favour of this one.
        // This means if something else happens during the keep alive period,
        //   (e.g. we receive an MQTT message), then we start a new keep alive
        //   period, postponing the next ping.

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(MQTT_PING_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // in case it takes us a little while to do this, we try and do it
        //  shortly before the keep alive period expires
        // it means we're pinging slightly more frequently than necessary
        Calendar wakeUpTime = Calendar.getInstance();
        wakeUpTime.add(Calendar.SECOND, keepAliveSeconds);

        AlarmManager aMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        aMgr.set(AlarmManager.RTC_WAKEUP,
                wakeUpTime.getTimeInMillis(),
                pendingIntent);
    }


    public class PingSender extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Note that we don't need a wake lock for this method (even though
            //  it's important that the phone doesn't switch off while we're
            //  doing this).
            // According to the docs, "Alarm Manager holds a CPU wake lock as
            //  long as the alarm receiver's onReceive() method is executing.
            //  This guarantees that the phone will not sleep until you have
            //  finished handling the broadcast."
            // This is good enough for our needs.

            try {
//                mqttClient.ping();
            } catch (Exception e) {
                // if something goes wrong, it should result in connectionLost
                //  being called, so we will handle it there
                Log.e("mqtt", "ping failed - MQTT exception", e);

                // assume the client connection is broken - trash it
//                try {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
//                } catch (MqttPersistenceException e1) {
//                    Log.e("mqtt", "disconnect failed - persistence exception", e1);
//                }

                // reconnect
                if (connectToBroker()) {
                    subscribeToTopic(topicName);
                }
            }

            // start the next keep alive period
            scheduleNextPing();
        }
    }


    //  apps that handle very small amounts of data - e.g. updates and
    //   notifications that don't need to be persisted if the app / phone
    //   is restarted etc. may find it acceptable to store this data in a
    //   variable in the Service
    //  that's what I'm doing in this sample: storing it in a local hashtable
    //  if you are handling larger amounts of data, and/or need the data to
    //   be persisted even if the app and/or phone is restarted, then
    //   you need to store the data somewhere safely
    //  see http://developer.android.com/guide/topics/data/data-storage.html
    //   for your storage options - the best choice depends on your needs

    // stored internally

    private Hashtable<String, String> dataCache = new Hashtable<String, String>();

    private boolean addReceivedMessageToStore(String key, String value) {
        String previousValue = null;

        if (value.length() == 0) {
            previousValue = dataCache.remove(key);
        } else {
            previousValue = dataCache.put(key, value);
        }

        // is this a new value? or am I receiving something I already knew?
        //  we return true if this is something new
        return ((previousValue == null) ||
                (previousValue.equals(value) == false));
    }

    // provide a public interface, so Activities that bind to the Service can
    //  request access to previously received messages

    public void rebroadcastReceivedMessages() {
        Enumeration<String> e = dataCache.keys();
        while (e.hasMoreElements()) {
            String nextKey = e.nextElement();
            String nextValue = dataCache.get(nextKey);

            broadcastReceivedMessage(nextKey, nextValue);
        }
    }

    /**
     * 获取并生成客户端id
     *
     * @return
     */
    private String generateClientId() {
        String temp = null;
        String android_id = Settings.System.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            temp=DigestUtils.md5(android_id + Build.SERIAL  + Build.BOARD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // generate a unique client id if we haven't done so before, otherwise
        //   re-use the one we already have
        mqttClientId = PreferencesUtils.getString(this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, null);
        if (mqttClientId == null) {

            // generate a unique client ID - I'm basing this on a combination of
            //  the phone device id and the current timestamp
//            String timestamp = "" + (new Date()).getTime();

            mqttClientId = temp ;

            // truncate - MQTT spec doesn't allow client ids longer than 23 chars
            if (mqttClientId.length() > MAX_MQTT_CLIENTID_LENGTH) {
                mqttClientId = mqttClientId.substring(0, MAX_MQTT_CLIENTID_LENGTH);
            }
            PreferencesUtils.putString(this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, mqttClientId);
        }
        if (mqttClientId.length() > MAX_MQTT_CLIENTID_LENGTH) {
            mqttClientId = mqttClientId.substring(0, MAX_MQTT_CLIENTID_LENGTH);
        }
        mqttClientIdW=mqttClientId;
        return mqttClientId;
    }
    public static String mqttClientIdW;
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isAvailable() &&
                cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }

        return false;
    }




}