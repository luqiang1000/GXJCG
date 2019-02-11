package cn.dlc.guankungongxiangjicunji.main.bean;

import java.util.List;

public class VideoBean {

    /**
     * code : 1
     * msg : success
     * time : 1531385435
     * data : {"total":1,"totalpage":1,"list":[{"id":2,"cuid":1,"keyname":"ad1","title":"video/f23473d70bd3ec10fa4bd47e1e42ac0e.mp4","video_size":"2.21 MB","video_suffix":"mp4","video_typeid":6,"ctime":1531295456,"down_url":"http://wan123.oss-cn-beijing.aliyuncs.com/video%2Ff23473d70bd3ec10fa4bd47e1e42ac0e.mp4"}]}
     */

    public int code;
    public String msg;
    public String time;
    public DataBean data;

    public static class DataBean {
        /**
         * total : 1
         * totalpage : 1
         * list : [{"id":2,"cuid":1,"keyname":"ad1","title":"video/f23473d70bd3ec10fa4bd47e1e42ac0e.mp4","video_size":"2.21 MB","video_suffix":"mp4","video_typeid":6,"ctime":1531295456,"down_url":"http://wan123.oss-cn-beijing.aliyuncs.com/video%2Ff23473d70bd3ec10fa4bd47e1e42ac0e.mp4"}]
         */

        public int total;
        public int totalpage;
        public List<ListBean> list;


        public static class ListBean {
            /**
             * id : 2
             * cuid : 1
             * keyname : ad1
             * title : video/f23473d70bd3ec10fa4bd47e1e42ac0e.mp4
             * video_size : 2.21 MB
             * video_suffix : mp4
             * video_typeid : 6
             * ctime : 1531295456
             * down_url : http://wan123.oss-cn-beijing.aliyuncs.com/video%2Ff23473d70bd3ec10fa4bd47e1e42ac0e.mp4
             */

            public int id;
            public int cuid;
            public String keyname;
            public String title;
            public String video_size;
            public String video_suffix;
            public int video_typeid;
            public int ctime;
            public String down_url;


        }
    }
}
