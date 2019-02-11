package cn.dlc.guankungongxiangjicunji.main.bean;

public class MainInfoBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"wxcode":{"title":"公众号","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""},"wxpay":{"title":"微信支付","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""},"alipay":{"title":"支付宝支付","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""},"call":{"title":"电话","value":"","link":"1333333333"}}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * wxcode : {"title":"公众号","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""}
         * wxpay : {"title":"微信支付","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""}
         * alipay : {"title":"支付宝支付","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png","link":""}
         * call : {"title":"电话","value":"","link":"1333333333"}
         */

        public WxcodeBean wxcode;
        public WxpayBean wxpay;
        public AlipayBean alipay;
        public CallBean call;

        public static class WxcodeBean {
            /**
             * title : 公众号
             * value : http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png
             * link :
             */

            public String title;
            public String value;
            public String link;
        }

        public static class WxpayBean {
            /**
             * title : 微信支付
             * value : http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png
             * link :
             */

            public String title;
            public String value;
            public String link;
        }

        public static class AlipayBean {
            /**
             * title : 支付宝支付
             * value : http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180630/e54e726f22dfb4e36e808940c61b59f0.png
             * link :
             */

            public String title;
            public String value;
            public String link;
        }

        public static class CallBean {
            /**
             * title : 电话
             * value :
             * link : 1333333333
             */

            public String title;
            public String value;
            public String link;
        }
    }
}
