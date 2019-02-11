package cn.dlc.guankungongxiangjicunji.main.bean;

import java.util.List;

public class CountDownTiemBean {

    /**
     * code : 1
     * msg : success
     * time : 1530708142
     * data : [{"id":3,"title":"倒计时方式1","keyname":"countdownMode1","value":"30"},{"id":4,"title":"倒计时方式2","keyname":"countdownMode2","value":"60"}]
     */

    private int code;
    private String msg;
    private String time;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * title : 倒计时方式1
         * keyname : countdownMode1
         * value : 30
         */

        private int id;
        private String title;
        private String keyname;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeyname() {
            return keyname;
        }

        public void setKeyname(String keyname) {
            this.keyname = keyname;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
