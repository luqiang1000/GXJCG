package cn.dlc.guankungongxiangjicunji.main.bean;

public class MakeCupboardOrderBean {


    /**
     * data : {"money":"5.00","goodsno":"2","paylog":"order_20180703133529830795"}
     * msg : success
     * time : 1530596129
     * code : 1
     */

    public DataBean data;
    public String msg;
    public String time;
    public int code;

    public static class DataBean {
        /**
         * money : 5.00
         * goodsno : 2
         * paylog : order_20180703133529830795
         */

        public String money;
        public String goodsno;
        public String paylog;
    }
}
