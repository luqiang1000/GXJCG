package cn.dlc.guankungongxiangjicunji.main.bean;

import java.io.Serializable;
import java.util.List;

public class CabinetListBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * time : 1530171168
     * data : [{"cupboard_charging":1,"charging_info":{"id":1,"title":"大柜","keyname":"big","starting_time":2,"starting_price":"5.00","normal_price":"1.00","unit":"hour","cuid":1,"ctime":1530167695,"memo":"300*500*200mm"},"charging_restnum":1},{"cupboard_charging":3,"charging_info":{"id":3,"title":"中柜","keyname":"mid","starting_time":2,"starting_price":"5.00","normal_price":"3.00","unit":"hour","cuid":1,"ctime":1530169118,"memo":"200*200*400"},"charging_restnum":1}]
     */

    public int code;
    public String msg;
    public String time;
    public List<DataBean> data;


    public static class DataBean implements Serializable{

        public int cupboard_charging;
        public ChargingInfoBean charging_info;
        public int charging_restnum;
        public int macno_status;


        public static class ChargingInfoBean implements Serializable{


            public int id;
            public String title;
            public String keyname;
            public int starting_time;
            public String starting_price;
            public String normal_price;
            public String unit;
            public int cuid;
            public int ctime;
            public String memo;


        }
    }
}
