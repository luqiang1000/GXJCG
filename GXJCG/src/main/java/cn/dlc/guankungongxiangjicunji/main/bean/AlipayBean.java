package cn.dlc.guankungongxiangjicunji.main.bean;

public class AlipayBean {

    /**
     * code : 1
     * msg : success
     * time : 1531379840
     * data : https://qr.alipay.com/bax00469lbiz6jp62iz18087
     */

    private int code;
    private String msg;
    private String time;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
