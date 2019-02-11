package cn.dlc.guankungongxiangjicunji.main.bean;

import java.util.List;

public class UserInfo {

    /**
     * code : 1
     * msg : 成功获取用户信息
     * time : 1530150259
     * data : {"userinfo":{"id":4,"username":"13144975750","nickname":"13144975750","mobile":"13144975750","avatar":"http://127.0.0.1/assets/img/avatar.png","score":0,"token":"f54ad09e-8265-4048-a86f-c62b65d915ef"},"thirdinfo":[],"thirdlist":[],"extra":[]}
     */

    public int code;
    public String msg;
    public DataBean data;


    public class DataBean {
        /**
         * userinfo : {"id":4,"username":"13144975750","nickname":"13144975750","mobile":"13144975750","avatar":"http://127.0.0.1/assets/img/avatar.png","score":0,"token":"f54ad09e-8265-4048-a86f-c62b65d915ef"}
         * thirdinfo : []
         * thirdlist : []
         * extra : []
         */

        public UserinfoBean userinfo;


        public class UserinfoBean {
            /**
             * id : 4
             * username : 13144975750
             * nickname : 13144975750
             * mobile : 13144975750
             * avatar : http://127.0.0.1/assets/img/avatar.png
             * score : 0
             * token : f54ad09e-8265-4048-a86f-c62b65d915ef
             */

            public int id;
            public String username;
            public String nickname;
            public String mobile;
            public int score;
            public String token;
        }
    }
}
