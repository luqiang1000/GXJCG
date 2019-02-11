package cn.dlc.guankungongxiangjicunji.main.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26/026.
 */

public class Advert {

    /**
     * code : 1
     * msg : 获取成功
     * data : [{"img":[{"title":"图片","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180724/34cca9d315045b9eb35db409275ed4b4.jpg","link":""},{"title":"图片","value":"http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180724/34cca9d315045b9eb35db409275ed4b4.jpg","link":""}]}]
     * type : 1
     */

    private int code;
    private String msg;
    private int type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ImgBean> img;

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class ImgBean {
            /**
             * title : 图片
             * value : http://zngxccgui.app.xiaozhuschool.com/uploads/zngxccgui/20180724/34cca9d315045b9eb35db409275ed4b4.jpg
             * link :
             */

            private String title;
            private String value;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
