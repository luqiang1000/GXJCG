package cn.dlc.guankungongxiangjicunji.main;

import com.dlc.vendingcabinets.Constant;
import com.lzy.okgo.model.HttpParams;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.guankungongxiangjicunji.main.bean.Advert;
import cn.dlc.guankungongxiangjicunji.main.bean.AlipayBean;
import cn.dlc.guankungongxiangjicunji.main.bean.ArticleBean;
import cn.dlc.guankungongxiangjicunji.main.bean.BaseBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CountDownTiemBean;
import cn.dlc.guankungongxiangjicunji.main.bean.GetGoodsBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MainInfoBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MakeCupboardOrderBean;
import cn.dlc.guankungongxiangjicunji.main.bean.UserInfo;
import cn.dlc.guankungongxiangjicunji.main.bean.VideoBean;
import cn.dlc.guankungongxiangjicunji.main.bean.WxPayBean;

public class MainHttp {
    public static String diveceNo = "";

    private final OkGoWrapper mOkGoWrapper;

    private MainHttp() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    private static class InstanceHolder {
        private static final MainHttp sInstance = new MainHttp();
    }

    public static MainHttp get() {
        return InstanceHolder.sInstance;
    }

    //设备柜子列表
    public void cabinetList(String macno, Bean01Callback<CabinetListBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("macno", macno);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/cupboard/getCupboardCharging"), null, httpParams, CabinetListBean.class, callback);
    }

    //获取手机验证密码
    public void sendCode(String mobile, Bean01Callback<BaseBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("platform", "alisms");
        httpParams.put("mobile", mobile);
        httpParams.put("event", "pickupcode");
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/sms/api/index/send"), null, httpParams, BaseBean.class, callback);
    }

    //提交存件密码
    public void checkCode(String mobile, String idcard, String username, String code, String check_type, Bean01Callback<UserInfo> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", mobile);
        httpParams.put("idcard", idcard);
        httpParams.put("username", username);
        httpParams.put("code", code);
        httpParams.put("check_type", check_type);
        httpParams.put("macno", Constant.MACNO);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/cupboardcode/save_goods_check_code"), null, httpParams, UserInfo.class, callback);

    }

    //生成订单
    public void makeCupboardOrder(String macno, String code, String checkType, String cupboardCharging, String token, Bean01Callback<MakeCupboardOrderBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("macno", macno);
        httpParams.put("code", code);
        httpParams.put("checkType", checkType);
        httpParams.put("cupboard_charging", cupboardCharging);
        httpParams.put("token", token);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/Cupboardcode/makeCupboardOrder"), null, httpParams, MakeCupboardOrderBean.class, callback);
    }

//    //微信支付
//    public void weixinpay(String money, String tradeNo, String token, Bean01Callback<WxPayBean> callback) {
//        HttpParams httpParams = new HttpParams();
//        httpParams.put("money", money);
//        httpParams.put("trade_no", tradeNo);
//        httpParams.put("token", token);
//        mOkGoWrapper.post(MainUrls.appendUrl("/vv/weixinpay/api/index/pay"), null, httpParams, WxPayBean.class, callback);
//    }
//
//    //支付宝支付
//    public void alipay(String money, String tradeNo, String tradeType, Bean01Callback<GetGoodsBean> callback) {
//        HttpParams httpParams = new HttpParams();
//        httpParams.put("money", money);
//        httpParams.put("trade_no", tradeNo);
//        httpParams.put("trade_type", tradeType);
//        mOkGoWrapper.post(MainUrls.appendUrl("vv/alipay/api/index/pay"), null, httpParams, GetGoodsBean.class, callback);
//    }

    //取件
    public void getGoods(String macno, String token, Bean01Callback<GetGoodsBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("macno", macno);
        httpParams.put("token", token);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/cupboardcode/getGoods"), null, httpParams, GetGoodsBean.class, callback);

    }

    //获取文字说明
    public void article(String keyname, Bean01Callback<ArticleBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyname", keyname);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/help/api/index/getArticleByKeyName"), null, httpParams, ArticleBean.class, callback);

    }


    //提交存件密码
    public void getGoodsCheckCode(String mobile, String idcard, String username, String code, String check_type, Bean01Callback<UserInfo> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", mobile);
        httpParams.put("idcard", idcard);
        httpParams.put("username", username);
        httpParams.put("code", code);
        httpParams.put("check_type", check_type);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/cupboardcode/get_goods_check_code"), null, httpParams, UserInfo.class, callback);

    }

    //获取首页信息
    public void getMainInfo(Bean01Callback<MainInfoBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyname", "index_info");
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/advert/api/index/getAdvertByKey"), null, httpParams, MainInfoBean.class, callback);
    }

    /**
     * 获取微信支付二维码
     *
     * @param money
     * @param paylog
     * @param token
     */
    public void getWxPayQRCode(String money, String paylog, String token, Bean01Callback<WxPayBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("money", money);
        httpParams.put("trade_no", paylog);
        httpParams.put("token", token);
        httpParams.put("trade_type", "NATIVE");
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/weixinpay/api/index/pay"), null, httpParams, WxPayBean.class, callback);
    }


    /**
     * 获取支付宝支付二维码
     *
     * @param money
     * @param paylog
     */
    public void getAliQRCode(String money, String paylog, String token, Bean01Callback<AlipayBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("money", money);
        httpParams.put("trade_no", paylog);
        httpParams.put("trade_type", "NATIVE");
        httpParams.put("token", token);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/alipay/api/index/pay"), null, httpParams, AlipayBean.class, callback);
    }


    /**
     * 上传友盟token
     *
     * @param token
     */
    public void uploadUMToken(String token, String macno, Bean01Callback<BaseBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("apptoken", token);
        httpParams.put("type", "android");
        httpParams.put("macno", macno);
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/cupboard/api/cupboardcode/setcupboardtoken"), null, httpParams, BaseBean.class, callback);
    }

    //获取倒计时
    public void countDowntime(Bean01Callback<CountDownTiemBean> callback) {
        HttpParams httpParams = new HttpParams();
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/setting/api/index/setlist"), null, httpParams, CountDownTiemBean.class, callback);
    }

    //视频下载列表
    public void videoList(Bean01Callback<VideoBean> callback) {
        HttpParams httpParams = new HttpParams();
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/alivideo/api/index/videoList"), null, null, VideoBean.class, callback);
    }

    public void getAdvertByKey(Bean01Callback<Advert> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyname","index_ad");
        httpParams.put("returntype", "arr");
        mOkGoWrapper.post(MainUrls.appendUrl("/vv/advert/api/index/getAdvertByKey"), null, httpParams, Advert.class, callback);
    }


}
