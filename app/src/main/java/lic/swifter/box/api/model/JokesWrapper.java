package lic.swifter.box.api.model;

/**
 * Created by cheng on 2016/8/20.
 */

public class JokesWrapper {

    Joke[] data;

    private class Joke {

        public String content;
        public String hashId;
        public int unixtime;
        public String updatetime;

    }
}
