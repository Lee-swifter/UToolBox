package lic.swifter.box.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库处理类
 * Created by lic on 16-8-15.
 */
public class BoxDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;      //from 0.8.9
    private static final String DATABASE_NAME = "box.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String TIME_STAMP_TYPE = " TIMESTAMP";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_IP_ENTRIES =
            "CREATE TABLE " + BoxContract.IpEntry.TABLE_NAME + " (" +
                    BoxContract.IpEntry._ID + " INTEGER PRIMARY KEY," +
                    BoxContract.IpEntry.COLUMN_NAME_SEARCH_TIME_STAMP + TIME_STAMP_TYPE + COMMA_SEP +
                    BoxContract.IpEntry.COLUMN_NAME_SEARCH_DATA + TEXT_TYPE + COMMA_SEP +
                    BoxContract.IpEntry.COLUMN_NAME_RESULT_AREA + TEXT_TYPE + COMMA_SEP +
                    BoxContract.IpEntry.COLUMN_NAME_RESULT_LOCATION + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_ID_ENTRIES =
            "CREATE TABLE " + BoxContract.IdEntry.TABLE_NAME + " (" +
                    BoxContract.IdEntry._ID + " INTEGER PRIMARY KEY," +
                    BoxContract.IdEntry.COLUMN_NAME_SEARCH_TIME_STAMP + TIME_STAMP_TYPE + COMMA_SEP +
                    BoxContract.IdEntry.COLUMN_NAME_SEARCH_ID_NUMBER + TEXT_TYPE + COMMA_SEP +
                    BoxContract.IdEntry.COLUMN_NAME_RESULT_AREA + TEXT_TYPE + COMMA_SEP +
                    BoxContract.IdEntry.COLUMN_NAME_RESULT_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                    BoxContract.IdEntry.COLUMN_NAME_RESULT_SEX + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_PHONE_ENTRIES =
            "CREATE TABLE " + BoxContract.PhoneEntry.TABLE_NAME + " (" +
                    BoxContract.PhoneEntry._ID + " INTEGER PRIMARY KEY," +
                    BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_TIME_STAMP + TIME_STAMP_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_SEARCH_PHONE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_PROVINCE + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CITY + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_AREA_CODE + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_ZIP + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_COMPANY + TEXT_TYPE + COMMA_SEP +
                    BoxContract.PhoneEntry.COLUMN_NAME_RESULT_CARD + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_WEIGHT_ENTRIES =
            "CREATE TABLE " + BoxContract.BaiduWeightEntry.TABLE_NAME + " (" +
                    BoxContract.BaiduWeightEntry._ID + " INTEGER PRIMARY KEY," +
                    BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_TIME_STAMP + TIME_STAMP_TYPE + COMMA_SEP +
                    BoxContract.BaiduWeightEntry.COLUMN_NAME_SEARCH_WEBSITE + TEXT_TYPE + COMMA_SEP +
                    BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT + TEXT_TYPE + COMMA_SEP +
                    BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_FROM + TEXT_TYPE + COMMA_SEP +
                    BoxContract.BaiduWeightEntry.COLUMN_NAME_RESULT_WEIGHT_TO + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_IP_ENTRIES =
            "DROP TABLE IF EXISTS " + BoxContract.IpEntry.TABLE_NAME;

    private static final String SQL_DELETE_ID_ENTRIES =
            "DROP TABLE IF EXISTS " + BoxContract.IdEntry.TABLE_NAME;

    private static final String SQL_DELETE_PHONE_ENTRIES =
            "DROP TABLE IF EXISTS " + BoxContract.PhoneEntry.TABLE_NAME;

    private static final String SQL_DELETE_WEIGHT_ENTRIES =
            "DROP TABLE IF EXISTS " + BoxContract.BaiduWeightEntry.TABLE_NAME;

    private static BoxDbHelper instance;

    public static BoxDbHelper getInstance(Context context) {
        BoxDbHelper inst = instance;
        if (inst == null) {
            synchronized (BoxDbHelper.class) {
                inst = instance;
                if (inst == null) {
                    inst = new BoxDbHelper(context);
                    instance = inst;
                }
            }
        }
        return inst;
    }

    private BoxDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_IP_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_ID_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_PHONE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_WEIGHT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:     //before 0.8.9
                sqLiteDatabase.execSQL(SQL_CREATE_WEIGHT_ENTRIES);
                break;
            case 2:     //from 0.8.9
                break;
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_IP_ENTRIES);
        db.execSQL(SQL_DELETE_ID_ENTRIES);
        db.execSQL(SQL_DELETE_PHONE_ENTRIES);
        db.execSQL(SQL_DELETE_WEIGHT_ENTRIES);
        onCreate(db);
    }
}
