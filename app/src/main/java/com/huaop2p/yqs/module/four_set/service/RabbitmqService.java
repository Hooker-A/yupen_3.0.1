package com.huaop2p.yqs.module.four_set.service;

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

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.base.bases.PreferencesUtils;
import com.huaop2p.yqs.module.base.bases.ShareLocalUser;
import com.huaop2p.yqs.module.four_set.activity.MessageActivity;
import com.huaop2p.yqs.utils.DigestUtils;
import com.lidroid.xutils.util.LogUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeoutException;

/**
 * Created by zgq on 2016/5/19.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/19 14:48
 * 功能:
 */
public class RabbitmqService extends Service {

    /**
     * 1 通过网络的监听的网络变化来重新启动
     * 2 当服务杀死的时候
     * 3
     */

    public static final String EXCHANGE_NAME = "Routing";
    public static int MQTT_NOTIFICATION_UPDATE = 2;
    public static String mqttClientId = null;
    public LocalBinder<RabbitmqService> mBinder;
    public String brokerHostName = "";
    public String topicName = "";

    //监听网络状态的变化
    public BackgroundDataChangeIntentReceiver dataEnabledReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new LocalBinder<RabbitmqService>(this);
        brokerHostName = "219.143.6.71";//  106.39.123.229
        topicName = "broadcast";

        dataEnabledReceiver=new BackgroundDataChangeIntentReceiver();
        registerReceiver(dataEnabledReceiver,
                new IntentFilter(ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED));

        new Thread(new Runnable() {
            @Override
            public void run() {
                subscribeToTopic(topicName);
            }
        }).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class LocalBinder<S> extends Binder {
        public WeakReference<S> mService;

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
    Connection connection = null;
    ConnectionFactory factory=null;
    Channel channel=null;
    public void subscribeToTopic(String topicName) {
        factory = new ConnectionFactory();
        factory.setHost(brokerHostName);
        factory.setUsername("android");
        factory.setPassword("android");//android@2016

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();
            generateClientId();
            String[] routingKeys = new String[]{topicName, generateClientId()};
            for(String severity : routingKeys){
                channel.queueBind(queueName, EXCHANGE_NAME, severity);
            }
            channel.addShutdownListener(new ShutdownListener() {
                @Override
                public void shutdownCompleted(ShutdownSignalException e) {
                    LogUtils.e(e.toString() + connection.isOpen() + "");

                }
            });

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Rabbitmq");
                    wl.acquire();

                    String message = new String(body, "UTF-8");
                    LogUtils.e(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                    notifyUser("sss","sss",message);
                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                    LogUtils.e("handleShutdownSignal"+consumerTag);
                    super.handleShutdownSignal(consumerTag, sig);
                }

                @Override
                public void handleConsumeOk(String consumerTag) {
                    super.handleConsumeOk(consumerTag);
                    LogUtils.e("handleConsumeOk");
                }


            };
            channel.basicConsume(queueName, true, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();

        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    //网络状态变化的监听
    public class BackgroundDataChangeIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            // we protect against the phone switching off while we're doing this
            //  by requesting a wake lock - we request the minimum possible wake
            //  lock - just enough to keep the CPU running until we've finished
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
            wl.acquire();
            LogUtils.e("  cm.getBackgroundDataSetting()");
            ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (cm.getBackgroundDataSetting()) {
                // user has allowed background data - we start again - picking
                //  up where we left off in handleStart before
                subscribeToTopic(topicName);
                handleStart(intent, 0);
                LogUtils.e(cm.getBackgroundDataSetting()+ "  cm.getBackgroundDataSetting()");
            } else {
                // user has disabled background data
                connectionStatus = RabbitmqConnectionStatus.NOTCONNECTED_DATADISABLED;

                // update the app to show that the connection has been disabled
                broadcastServiceStatus("Not connected - background data disabled");

                // disconnect from the broker
                disconnectFromBroker();
                LogUtils.e(cm.getBackgroundDataSetting() + "  cm.getBackgroundDataSetting()");
            }

            // we're finished - if the phone is switched off, it's okay for the CPU
            //  to sleep now
            wl.release();
        }
    }

    public void disconnectFromBroker() {
        LogUtils.e("disconnectFromBroker");
    }

    // status of Rabbitmq client connection
    public RabbitmqConnectionStatus connectionStatus = RabbitmqConnectionStatus.INITIAL;

    // constants used to define Rabbitmq connection status
    public enum RabbitmqConnectionStatus {
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

    // methods used to notify the Activity UI of something that has happened
    //  so that it can be updated to reflect status and the data received
    //  from the server

    public void broadcastServiceStatus(String statusDescription) {
        // inform the app (for times when the Activity UI is running /
        //   active) of the current MQTT connection status so that it
        //   can update the UI accordingly
        LogUtils.e("connectionLost----------connectionLost  "+statusDescription);
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction(MQTT_STATUS_INTENT);
//        broadcastIntent.putExtra(MQTT_STATUS_MSG, statusDescription);
//        sendBroadcast(broadcastIntent);
    }
    synchronized void handleStart(Intent intent, int startId){
        LogUtils.e("handleStart");
    }
//    synchronized void handleStart(Intent intent, int startId) {
//        // before we start - check for a couple of reasons why we should stop
//        LogUtils.e("handleStart");
//        if (mqttClient == null) {
//            Log.i(TAG, "mqttClient === null");
//            // we were unable to define the MQTT client connection, so we stop
//            //  immediately - there is nothing that we can do
//            stopSelf();
//            return;
//        }
//        Log.i(TAG, "mqttClient != null");
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        if (cm.getBackgroundDataSetting() == false) // respect the user's request not to use data!
//        {
//            // user has disabled background data
//            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_DATADISABLED;
//
//            // update the app to show that the connection has been disabled
//            broadcastServiceStatus("Not connected - background data disabled");
//
//            // we have a listener running that will notify us when this
//            //   preference changes, and will call handleStart again when it
//            //   is - letting us pick up where we leave off now
//            return;
//        }
//        Log.i(TAG, "getBackgroundDataSetting == true");
//        // the Activity UI has started the MQTT service - this may be starting
//        //  the Service new for the first time, or after the Service has been
//        //  running for some time (multiple calls to startService don't start
//        //  multiple Services, but it does call this method multiple times)
//        // if we have been running already, we re-send any stored data
//        rebroadcastStatus();
//        rebroadcastReceivedMessages();
//
//        Log.i(TAG, "after rebroadcastReceivedMessages");
//        // if the Service was already running and we're already connected - we
//        //   don't need to do anything
//        if (isAlreadyConnected() == false) {
//            Log.i(TAG, "isAlreadyConnected() == false");
//            // set the status to show we're trying to connect
//            connectionStatus = MQTTConnectionStatus.CONNECTING;
//
//            // we are creating a background service that will run forever until
//            //  the user explicity stops it. so - in case they start needing
//            //  to save battery life - we should ensure that they don't forget
//            //  we're running, by leaving an ongoing notification in the status
//            //  bar while we are running
////            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////            Notification notification = new Notification(R.mipmap.logo_app_48,
////                    "MQTT",
////                    System.currentTimeMillis());
////            notification.flags |= Notification.FLAG_ONGOING_EVENT;
////            notification.flags |= Notification.FLAG_NO_CLEAR;
////            Intent notificationIntent = new Intent(this, NotifyActivity.class);
////            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
////                    notificationIntent,
////                    PendingIntent.FLAG_UPDATE_CURRENT);
////            notification.setLatestEventInfo(this, "MQTT", "MQTT Service is running", contentIntent);
////            nm.notify(MQTT_NOTIFICATION_ONGOING, notification);
//
//
//            // before we attempt to connect - we check if the phone has a
//            //  working data connection
//            if (isOnline()) {
//                // we think we have an Internet connection, so try to connect
//                //  to the message broker
//                if (connectToBroker()) {
//                    // we subscribe to a topic - registering to receive push
//                    //  notifications with a particular key
//                    // in a 'real' app, you might want to subscribe to multiple
//                    //  topics - I'm just subscribing to one as an example
//                    // note that this topicName could include a wildcard, so
//                    //  even just with one subscription, we could receive
//                    //  messages for multiple topics
//                    subscribeToTopic(topicName);
//                }
//            } else {
//                // we can't do anything now because we don't have a working
//                //  data connection
//                connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;
//
//                // inform the app that we are not connected
//                broadcastServiceStatus("Waiting for network connection");
//            }
//        }
//
//        // changes to the phone's network - such as bouncing between WiFi
//        //  and mobile data networks - can break the MQTT connection
//        // the MQTT connectionLost can be a bit slow to notice, so we use
//        //  Android's inbuilt notification system to be informed of
//        //  network changes - so we can reconnect immediately, without
//        //  haing to wait for the MQTT timeout
//        if (netConnReceiver == null) {
//            netConnReceiver = new NetworkConnectionIntentReceiver();
//            registerReceiver(netConnReceiver,
//                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//        }
//
//        // creates the intents that are used to wake up the phone when it is
//        //  time to ping the server
//        if (pingSender == null) {
//            pingSender = new PingSender();
//            registerReceiver(pingSender, new IntentFilter(MQTT_PING_ACTION));
//        }
//    }


    /**
     * 显示通知
     *
     * @param alert
     * @param title
     * @param body
     */
    public void notifyUser(String alert, String title, String body) {
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

    /**
     * 获取并生成客户端id
     *
     * @return
     */
    public String generateClientId() {
        String temp = null;
        String android_id = Settings.System.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            temp= DigestUtils.md5(android_id + Build.SERIAL + Build.BOARD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mqttClientId = PreferencesUtils.getString(this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, null);
        if (mqttClientId == null) {
            mqttClientId = temp ;
            PreferencesUtils.putString(this, ShareLocalUser.SHARE_KEY_PUSH_CLIENT_ID, mqttClientId);
        }
        return mqttClientId;
    }
}
