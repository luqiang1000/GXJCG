package cn.dlc.guankungongxiangjicunji;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.dlc.vendingcabinets.Constant;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.commonsdk.UMConfigure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.exception.ApiException;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.JsonRequestLogger;
import cn.dlc.commonlibrary.okgo.translator.DefaultErrorTranslator;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.commonlibrary.utils.ScreenUtil;
import cn.dlc.commonlibrary.utils.SystemUtil;
import cn.dlc.guankungongxiangjicunji.main.MainUrls;
import cn.dlc.guankungongxiangjicunji.main.widget.CountDownTimerUtils;
import okhttp3.OkHttpClient;

/**
 * 存放网络请求地址
 */

public class App extends MultiDexApplication {

    private static App sInstance;
    private Handler mUiHandler;
    public CountDownTimerUtils mCountDownTimerUtils;
    Handler mHandler = new Handler();

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        initUM();
        initUMeng();
        FileDownloader.setupOnApplicationOnCreate(this);
        sInstance = this;
        Constant.MACNO = getPackageName() + "_" + android.os.Build.SERIAL;//测试设备号;

        //  Constant.MACNO = "cn.dlc.guankungongxiangjicunji_880c9e37798700000000";
        // Constant.MACNO ="112266";
//        Constant.DEVICE_PATH = "/dev/ttyUSB0";
        Constant.DEVICE_PATH = "/dev/ttyS0";
        Constant.BAUDRATE = "38400";
        mUiHandler = new Handler();
        if (SystemUtil.isMainProcess(this)) {
            ScreenUtil.init(this); // 获取屏幕尺寸
            ResUtil.init(this); // 资源
            PrefUtil.init(this); // SharedPreference
            // 网络
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
            OkGoWrapper.initOkGo(this, builder.build());
            OkGoWrapper.instance()
                    // 错误信息再格式化
                    .setErrorTranslator(new DefaultErrorTranslator())
                    // 拦截网络错误，一般是登录过期啥的
                    .setErrorInterceptor(new ErrorInterceptor() {
                        @Override
                        public boolean interceptException(Throwable tr) {
                            if (tr instanceof ApiException) {
                                ApiException ex = (ApiException) tr;
                                if (ex.getCode() == -2) {
                                    // 登录信息过期，请重新登录
                                    return true;
                                }
                            }
                            return false;
                        }
                    })
                    // 打印网络访问日志的
                    .setRequestLogger(new JsonRequestLogger(BuildConfig.DEBUG, 30));
        }
        LeakCanary.install(this);

//        initBugly();

    }
    private void initUMeng() {
        //友盟
        //调试日志
        UMConfigure.setLogEnabled(true);
        //初始化
        UMConfigure.init(this, MainUrls.umToken, "AndroidUmeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
        // 将默认Session间隔时长改为60秒。
        MobclickAgent.setSessionContinueMillis(1000 * 60);
    }
    /**
     * 初始化友盟
     */
    public void initUM() {
//        UMConfigure.init(this, "5b3733038f4a9d280e0001b8", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "a7e7dc6cd789cf45f92c1fa2676e5126");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                Log.i("Jim1", deviceToken);
//                MainUrls.umToken = deviceToken;
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.i("Jim", s + "----" + s1);
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initUM();
//                    }
//                },5000);
//
//            }
//        });
//
//        //修改友盟notify
//        initUMengMessageHandler(mPushAgent);
    }


    /**
     * 初始化bugly
     */
    private void initBugly() {
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        Bugly.init(getApplicationContext(), Information.BuglyAppId, false, strategy);
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App instance() {
        return sInstance;
    }

    public static Handler getUiHandler() {
        return instance().mUiHandler;
    }

    public static App getAppContext(Context context) {
        return (App) context.getApplicationContext();
    }

//    private void initUMengMessageHandler(PushAgent mPushAgent) {
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    default:
//                        Log.i("Jim1","收到信息");
//                        PrefUtil.getDefault().putBoolean("isRead", false).apply();
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        EventBus.getDefault().post(new BaseBean());
//                        return super.getNotification(context, msg);
//                }
//            }
//
//        };
//
//        mPushAgent.setMessageHandler(messageHandler);
//    }

}
