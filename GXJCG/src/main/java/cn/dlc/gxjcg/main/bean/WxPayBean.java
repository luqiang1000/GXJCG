package cn.dlc.guankungongxiangjicunji.main.bean;

public class WxPayBean {

    /**
     * code : 1
     * msg : success
     * time : 1530344938
     * data : {"return_code":"SUCCESS","return_msg":"OK","appid":"wx2c49fec17dc24b80","mch_id":"1507957931","nonce_str":"qLGQynJQZcbBgV6W","sign":"4D06EE76591166E549813130A0D218B3","result_code":"SUCCESS","prepay_id":"wx30154900341135f9b03e0bfe4290225074","trade_type":"NATIVE","code_url":"weixin://wxpay/bizpayurl?pr=SwXobxg"}
     */

    public int code;
    public String msg;
    public String time;
    public DataBean data;

    public static class DataBean {
        /**
         * return_code : SUCCESS
         * return_msg : OK
         * appid : wx2c49fec17dc24b80
         * mch_id : 1507957931
         * nonce_str : qLGQynJQZcbBgV6W
         * sign : 4D06EE76591166E549813130A0D218B3
         * result_code : SUCCESS
         * prepay_id : wx30154900341135f9b03e0bfe4290225074
         * trade_type : NATIVE
         * code_url : weixin://wxpay/bizpayurl?pr=SwXobxg
         */

        public String return_code;
        public String return_msg;
        public String appid;
        public String mch_id;
        public String nonce_str;
        public String sign;
        public String result_code;
        public String prepay_id;
        public String trade_type;
        public String code_url;
    }
}
