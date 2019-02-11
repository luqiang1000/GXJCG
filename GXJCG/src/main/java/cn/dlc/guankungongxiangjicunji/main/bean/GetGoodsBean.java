package cn.dlc.guankungongxiangjicunji.main.bean;

public class GetGoodsBean {


    /**
     * data : {"money":"69.00","usedtime":25,"status":"pay","goodsno":"2","overtime":23,"paylog":"order_20180704135847209986"}
     * msg : 超时支付
     * time : 1530684725
     * code : 1
     */

    public DataBean data;
    public String msg;
    public String time;
    public int code;

    public static class DataBean {
        /**
         * money : 69.00
         * usedtime : 25
         * status : pay
         * goodsno : 2
         * overtime : 23
         * paylog : order_20180704135847209986
         */

        public String money;
        public int usedtime;
        public String status;
        public String goodsno;
        public int overtime;
        public String paylog;
        public String unit;
    }
}
