package cn.dlc.guankungongxiangjicunji.main.bean;

/**
 * Created by wuyufeng    on  2018/6/25 0025.
 * interface by
 */

public class PayBean {
    public int id;
    public String money;
    public String spec;
    public String price;
    public String time;
    public String payQr;
    public String payedMoney;
    public String useTime;

    public PayBean(int id, String money, String spec, String price, String time, String payQr) {
        this.id = id;
        this.money = money;
        this.spec = spec;
        this.price = price;
        this.time = time;
        this.payQr = payQr;
    }

    public PayBean(int id, String money, String spec, String price, String time, String payQr,
        String payedMoney, String useTime) {
        this.id = id;
        this.money = money;
        this.spec = spec;
        this.price = price;
        this.time = time;
        this.payQr = payQr;
        this.payedMoney = payedMoney;
        this.useTime = useTime;
    }
}
