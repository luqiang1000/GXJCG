package cn.dlc.guankungongxiangjicunji.main;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.guankungongxiangjicunji.base.BaseUrls;

/**
 * Created by yinqingxiang  on  2018/5/25.
 * interface by
 */
public class MainUrls {
    public static String BASE_URL = "http://www.myqzkj.com";

    public static String appendUrl(String url) {
        return BASE_URL + url;
    }

    public static String umToken = "";//友盟token
}
