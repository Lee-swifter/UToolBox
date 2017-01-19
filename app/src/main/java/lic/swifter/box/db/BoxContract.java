package lic.swifter.box.db;

import android.provider.BaseColumns;

/**
 * 用户数据库的契约类
 * Created by lic on 16-8-15.
 */
public final class BoxContract {

    public BoxContract() {}

    public static abstract class IpEntry implements BaseColumns {
        public static final String TABLE_NAME = "ip_table";
        public static final String COLUMN_NAME_SEARCH_TIME_STAMP = "search_time_stamp";
        public static final String COLUMN_NAME_SEARCH_DATA = "search_data";
        public static final String COLUMN_NAME_RESULT_AREA = "result_area";
        public static final String COLUMN_NAME_RESULT_LOCATION = "result_location";
    }

    public static abstract class IdEntry implements BaseColumns {
        public static final String TABLE_NAME = "id_table";
        public static final String COLUMN_NAME_SEARCH_TIME_STAMP = "search_time_stamp";
        public static final String COLUMN_NAME_SEARCH_ID_NUMBER = "search_id_number";
        public static final String COLUMN_NAME_RESULT_AREA = "result_area";
        public static final String COLUMN_NAME_RESULT_SEX = "result_sex";
        public static final String COLUMN_NAME_RESULT_BIRTHDAY = "result_birthday";
    }

    public static abstract class PhoneEntry implements BaseColumns {
        public static final String TABLE_NAME = "phone_table";
        public static final String COLUMN_NAME_SEARCH_TIME_STAMP = "search_time_stamp";
        public static final String COLUMN_NAME_SEARCH_PHONE_NUMBER = "search_phone_number";
        public static final String COLUMN_NAME_RESULT_PROVINCE = "result_province";
        public static final String COLUMN_NAME_RESULT_CITY = "result_city";
        public static final String COLUMN_NAME_RESULT_AREA_CODE = "result_area_code";
        public static final String COLUMN_NAME_RESULT_ZIP = "result_zip";
        public static final String COLUMN_NAME_RESULT_COMPANY = "result_company";
        public static final String COLUMN_NAME_RESULT_CARD = "result_card";
    }

    public static abstract class BaiduWeightEntry implements BaseColumns {
        public static final String TABLE_NAME = "baidu_weight_table";
        public static final String COLUMN_NAME_SEARCH_TIME_STAMP = "search_time_stamp";
        public static final String COLUMN_NAME_SEARCH_WEBSITE = "search_website";
        public static final String COLUMN_NAME_RESULT_WEIGHT = "result_weight";
        public static final String COLUMN_NAME_RESULT_WEIGHT_FROM = "result_weight_from";
        public static final String COLUMN_NAME_RESULT_WEIGHT_TO = "result_weight_to";
    }
}
