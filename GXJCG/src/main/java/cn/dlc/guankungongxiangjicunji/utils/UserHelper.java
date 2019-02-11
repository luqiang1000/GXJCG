package cn.dlc.guankungongxiangjicunji.utils;

import android.support.annotation.NonNull;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.Information;
import cn.dlc.guankungongxiangjicunji.main.bean.LoginBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * 页面:尹庆祥  on  2018/3/26.
 */

public class UserHelper {
    
    private static UserHelper instance = new UserHelper();
    private final Gson mGson;
    private static final String USER_INFO = "user_info";

    /**
     * 缓存在内存中的用户信息
     */
    public static class UserInfo {
        private String id;
        private String token; // 登录token
        private String phone;
        private String nickname; // 昵称
        private String userCover; // 用户头像
        private String testMobile;// 测试用的登录保存的账号
        private String testPassword;//测试用的登录保存的密码

        public void updateFrom(LoginBean loginBean) {
            LoginBean.DataBean data = loginBean.data;
            if (data != null) {
                id = data.id;//用户id
                phone = data.phone;
                token = data.token;
            }
        }

        //public void updateFrom(PersonalCenterBean personalCenterBean) {
        //    PersonalCenterBean.DataBean data = personalCenterBean.data;
        //    if (data != null) {
        //        nickname = data.nickname;
        //        userCover = data.user_cover;
        //    }
        //}

        public void reset() {
            token = null;
            id = null;
        }
    }

    private UserInfo mUserInfo;

    private UserHelper() {
        mGson = new GsonBuilder().serializeNulls().create();
        loadUserInfoFromFile();
        if (mUserInfo == null) {
            mUserInfo = new UserInfo();
        }
    }

    public static UserHelper get() {
        return instance;
    }

    /**
     * 保存登录用户信息
     *
     * @param loginBean
     */
    public void saveUserInfo(LoginBean loginBean) {
        mUserInfo.updateFrom(loginBean);
        saveUserInfoToFile();
    }
    //
    //public void savaUserInfo(PersonalCenterBean personalCenterBean) {
    //    mUserInfo.updateFrom(personalCenterBean);
    //    saveUserInfoToFile();
    //}

    /**
     * 保存用户信息到文件中
     */
    private void saveUserInfoToFile() {
        String json = mGson.toJson(mUserInfo);
        PrefUtil.getDefault().putString(USER_INFO, json).apply();
    }

    private void loadUserInfoFromFile() {
        String json = PrefUtil.getDefault().getString(USER_INFO, "");
        try {
            UserInfo userInfo = mGson.fromJson(json, UserInfo.class);
            if (userInfo != null) {
                mUserInfo = userInfo;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出账号
     */
    public void logout() {
        // 发送退出登录消息
        mUserInfo.reset();
        saveUserInfoToFile();
    }

    private void checkEmptyAndLoad(Object value) {
        if (value == null) {
            loadUserInfoFromFile();
        }
    }

    private void checkEmptyAndLoad(int value) {
        if (value == 0) {
            loadUserInfoFromFile();
        }
    }

    private void checkEmptyAndLoad(float value) {
        if (value == 0) {
            loadUserInfoFromFile();
        }
    }

    /**
     * 返回非null的字符串
     *
     * @param ori
     * @return
     */
    private static @NonNull
    String returnNotNull(String ori) {
        return ori == null ? "" : ori;
    }

    public String getId() {
        checkEmptyAndLoad(mUserInfo.id);
        return returnNotNull(mUserInfo.id);
    }

    public String getToken() {
        checkEmptyAndLoad(mUserInfo.token);
        return returnNotNull(mUserInfo.token);
    }

    public String getPhone() {
        checkEmptyAndLoad(mUserInfo.phone);
        return returnNotNull(mUserInfo.phone);
    }

    public String getNickname() {
        checkEmptyAndLoad(mUserInfo.nickname);
        return returnNotNull(mUserInfo.nickname);
    }

    public String getUserCover() {
        checkEmptyAndLoad(mUserInfo.userCover);
        return returnNotNull(mUserInfo.userCover);
    }

    /**
     * 测试用的手机号
     *
     * @return
     */
    public String getTestPassword() {
        checkEmptyAndLoad(mUserInfo.testPassword);
        return returnNotNull(mUserInfo.testPassword);
    }

    /**
     * 测试用的密码
     *
     * @return
     */
    public String getTestMobile() {
        checkEmptyAndLoad(mUserInfo.testMobile);
        return returnNotNull(mUserInfo.testMobile);
    }

    /**
     * 保存测试用的手机号密码
     *
     * @param testMobile
     * @param testPassword
     */
    public void saveTestMobilePassword(String testMobile, String testPassword) {
        mUserInfo.testMobile = testMobile;
        if (Information.SAVE_TEST_PASSWORD) {
            mUserInfo.testPassword = testPassword;
        }
        saveUserInfoToFile();
    }
}
