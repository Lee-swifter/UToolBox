package lic.swifter.box.api.model;

/**
 * Created by cheng on 2016/8/20.
 */

public class JokesWrapper {

    public Joke[] data;

    public class Joke {

        public String content;
        public String hashId;
        public int unixtime;
        public String updatetime;

    }
}
