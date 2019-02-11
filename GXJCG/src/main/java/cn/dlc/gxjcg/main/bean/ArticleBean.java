package cn.dlc.guankungongxiangjicunji.main.bean;

public class ArticleBean {


    public int code;
    public String msg;
    public String time;
    public DataBean data;


    public static class DataBean {
        public int id;
        public String title;
        public String content;
        public String video_url;
        public int ctime;
        public String pic;

    }
}
