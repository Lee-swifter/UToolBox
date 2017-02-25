package lic.swifter.box.api.model;
/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class SiteSecurity {
    public int state;
    public int webstate;
    public String msg;
    public String scantime;
    public InfoData data;

    public class InfoData {
        public InfoDataBug loudong;
        public InfoDataTrojan guama;
        public InfoDataFake xujia;
        public InfoDataDistrot cuangai;
        public InfoDataSideNote pangzhu;
        public InfoDataScore score;
        public InfoDataViolation violation;
        public InfoDataGoogle google;

        public class InfoDataBug {
            public String high;
            public String mid;
            public String low;
            public String info;
        }

        public class InfoDataTrojan {
            public int level;
            public String msg;
        }

        public class InfoDataFake {
            public int level;
            public String msg;
        }

        public class InfoDataDistrot {
            public int level;
            public String msg;
        }

        public class InfoDataSideNote {
            public int level;
            public String msg;
        }

        public class InfoDataScore {
            public int level;
            public String msg;
        }

        public class InfoDataViolation {
            public int violation;
            public String msg;
        }

        public class InfoDataGoogle {
            public int level;
            public String msg;
        }
    }
}
