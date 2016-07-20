package com.huaop2p.yqs.module.three_mine.model.entity;

/**
 * Created by zgq on 2016/5/12.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/12 10:10
 * 功能: 城市数据管理类
 */
public class CityDBManager {
//    private final int BUFFER_SIZE = 10240;
//    private static final String PACKAGE_NAME = "com.example.citytest";
//    public static final String DB_NAME = "china_city_name.db";
//    public static final String DB_PATH = "/data"
//            + Environment.getDataDirectory().getAbsolutePath() + "/"
//            + PACKAGE_NAME + "/databases";
//    private Context mContext;
//    private SQLiteDatabase database;
//
//    public CityDBManager(Context context) {
//        this.mContext = context;
//    }
//
//    public void openDateBase() {
//        this.database = this.openDateBase(DB_PATH + "/" + DB_NAME );
//
//    }
//
//    private SQLiteDatabase openDateBase(String dbFile) {
//        File file = new File(dbFile);
//        if (!file.exists()) {
//            File folder = new File(DB_PATH);
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
////            InputStream stream = mContext.getResources().openRawResource(R.raw.china_city_name);
//            InputStream stream = mContext.getResources().openRawResource(R.anim.activity_close);
//            try {
//                FileOutputStream outputStream = new FileOutputStream(dbFile);
//                byte[] buffer = new byte[BUFFER_SIZE];
//                int count = 0;
//                while ((count = stream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, count);
//                }
//                outputStream.close();
//                stream.close();
//                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile,
//                        null);
//                return db;
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return database;
//    }
//
//    public void closeDatabase() {
//        if (database != null && database.isOpen()) {
//            this.database.close();
//        }
//    }
}
