package lic.swifter.box.api.model;

/**
 * Created by cheng on 2016/8/31.
 */

public class TopNewsWrapper {
    public String stat;
    public News[] data;
    
    public class News {
        public String title;
        public String date;
        public String author_name;
        public String thumbnail_pic_s;
        public String thumbnail_pic_s02;
        public String thumbnail_pic_s03;
        public String url;
        public String uniquekey;
        public String type;
        public String realtype;
    }
}
