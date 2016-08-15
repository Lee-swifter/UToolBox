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

}
