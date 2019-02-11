package cn.dlc.guankungongxiangjicunji.hs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dlc.vendingcabinets.Constant;
import com.dlc.vendingcabinets.TemplateVendingCabinets;
import com.dlc.vendingcabinets.mInterface.LogListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.zzhoujay.richtext.RichText;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.MainUrls;
import cn.dlc.guankungongxiangjicunji.main.activity.SecondDisplay;
import cn.dlc.guankungongxiangjicunji.main.bean.Advert;
import cn.dlc.guankungongxiangjicunji.main.bean.BaseBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CountDownTiemBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MainInfoBean;
import cn.dlc.guankungongxiangjicunji.main.fragment.EmptyFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.IdCardWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.IdentifyIdCardFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.InstructionsFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.OpenCabinetFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.PayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.PhoneWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.ProductDescriptionFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SafeGuideFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SaveSucceedFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectPayWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectSpecFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectUseWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutIdCardWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutIdentifyIdCardFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutOpenCabinetFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutPayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutPhoneWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutSafeGuideFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutSaveSucceedFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutSelectPayWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment.TakeOutSelectUseWayFragment;
import cn.dlc.guankungongxiangjicunji.main.widget.CountDownTimerUtils;
import cn.dlc.guankungongxiangjicunji.main.widget.ImageTextView;
import cn.dlc.guankungongxiangjicunji.main.widget.MyTitleBar;
import cn.dlc.guankungongxiangjicunji.main.widget.TopView;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Administrator on 2018/7/24/024.
 */

public class HalfSizeActivty extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.fl_frame)
    FrameLayout mFlFrame;
    @BindView(R.id.iv_qr_Official)
    ImageView mIvQrOfficial;
    @BindView(R.id.tv_wx_title)
    TextView mTvWxTitle;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.iv_qr_wechat)
    ImageView mIvQrWechat;
    @BindView(R.id.iv_qr_ali)
    ImageView mIvQrAli;
    @BindView(R.id.tv_qr_code_2)
    TextView mTvQrCode2;
    @BindView(R.id.btn_save)
    ImageView mBtnSave;
    @BindView(R.id.btn_take_out)
    ImageView mBtnTakeOut;
    @BindView(R.id.btn_guide)
    ImageView mBtnGuide;
    @BindView(R.id.item_save)
    ImageTextView mItemSave;
    @BindView(R.id.item_change)
    ImageTextView mItemChange;
    @BindView(R.id.item_gift)
    ImageTextView mItemGift;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_qr_code_1)
    TextView mTvQrCode1;
    @BindView(R.id.my_title_bar)
    MyTitleBar mMyTitleBar;
    @BindView(R.id.top_view)
    TopView mTopView;
    @BindView(R.id.ll_fragment)
    LinearLayout mLlFragment;
    @BindView(R.id.fl_main)
    FrameLayout mFlMain;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_device_no)
    TextView mTvDeviceNo;
    private Timer timer;

    private TimerTask task;

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private TimeChangeReceiver timeChangeReceiver;
    private MyHandler mHandler = new MyHandler();
    private SecondDisplay mSecondDisplay;
    private FragmentTransaction fragmentTransaction;
    private EmptyFragment emptyFragment;

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    //存物
    private CountDownTimerUtils mCountDownTimerUtils;


    private MediaPlayer mMediaPlayer;
    private long exittime = 0;

    public SafeGuideFragment getmSafeGuideFragment() {
        return new SafeGuideFragment();
    }

    public SelectSpecFragment getSelectSpecFragment() {
        return new SelectSpecFragment();
    }

    public SelectUseWayFragment getSelectUseWayFragment() {
        return new SelectUseWayFragment();
    }

    public PhoneWayFragment getPhoneWayFragment() {
        return new PhoneWayFragment();
    }

    public SelectPayWayFragment getSelectPayWayFragment() {
        return new SelectPayWayFragment();
    }

    public PayFragment getPayFragment() {
        return new PayFragment();
    }

    public OpenCabinetFragment getOpenCabinetFragment() {
        return new OpenCabinetFragment();
    }

    public SaveSucceedFragment getSaveSucceedFragment() {
        return new SaveSucceedFragment();
    }

    public IdentifyIdCardFragment getIdentifyIdCardFragment() {
        return new IdentifyIdCardFragment();
    }

    public IdCardWayFragment getIdCardWayFragment() {
        return new IdCardWayFragment();
    }

    //取物fragment


    public TakeOutPhoneWayFragment getTakeOutPhoneWayFragment() {
        return new TakeOutPhoneWayFragment();
    }

    public TakeOutSafeGuideFragment getTakeOutSafeGuideFragment() {
        return new TakeOutSafeGuideFragment();
    }

    public TakeOutSelectUseWayFragment getTakeOutSelectUseWayFragment() {
        return new TakeOutSelectUseWayFragment();
    }

    public TakeOutSelectPayWayFragment getTakeOutSelectPayWayFragment() {
        return new TakeOutSelectPayWayFragment();
    }

    public TakeOutSaveSucceedFragment getTakeOutSaveSucceedFragment() {
        return new TakeOutSaveSucceedFragment();
    }

    public TakeOutPayFragment getTakeOutPayFragment() {
        return new TakeOutPayFragment();
    }

    public TakeOutOpenCabinetFragment getTakeOutOpenCabinetFragment() {
        return new TakeOutOpenCabinetFragment();
    }

    public TakeOutIdentifyIdCardFragment getTakeOutIdentifyIdCardFragment() {
        return new TakeOutIdentifyIdCardFragment();
    }

    public TakeOutIdCardWayFragment getTakeOutIdCardWayFragment() {
        return new TakeOutIdCardWayFragment();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_half_size;
    }


    private void showFirstFragment() {
        switchFragment(new SafeGuideFragment(), null);
        setStep(1, false, false);
    }

    private void showTokeoutFirstFragment() {
        switchFragment(getTakeOutSafeGuideFragment(), null);
        setStep(1, false, false);
    }

    /**
     * @param step
     * @param isQr
     * @param isIdCard 是否是刷身份证
     */
    public void setStep(int step, boolean isQr, boolean isIdCard) {
        mMyTitleBar.setStep(step);

        mTopView.setVisibility(View.VISIBLE);
        if (step == 1) {
            //mTopView.setTitle(ResUtil.getString(R.string.anquanshiyongxuzhi));
        } else if (step == 2) {
            mTopView.setTitle(ResUtil.getString(R.string.qingxuanzeguiziguige));
        } else if (step == 3) {
            if (isIdCard) {
                mTopView.setTitle(ResUtil.getString(R.string.shuashenfenzheng));
            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingxuanzeshiyongfangshi));
            }
        } else if (step == 4) {
            if (isIdCard) {
                mTopView.setTitle("输入密码");
            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingshurushoujihaomahesuijimima));
            }
        } else if (step == 5) {
            if (isQr) {
                mTopView.setTitle(ResUtil.getString(R.string.qingfukuan));
            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingxuanzezhifuleixing));
            }
        } else if (step == 6) {
            mTopView.setVisibility(View.GONE);
            mCountDownTimerUtils.cancel();
        }

//        if (step != 6) {
//            mCountDownTimerUtils.start();
//        } else {
//            mCountDownTimerUtils.cancel();
//        }
    }


    public void setTakeoutStep(int step, boolean isQr, boolean isIdCard) {
        mMyTitleBar.setStep(step);

        mTopView.setVisibility(View.VISIBLE);
        if (step == 1) {
            mTopView.setTitle(ResUtil.getString(R.string.anquanshiyongxuzhi));
        } else if (step == 2) {
            mTopView.setTitle(ResUtil.getString(R.string.qingxuanzeshiyongfangshi));
        } else if (step == 3) {
            if (isIdCard) {
                mTopView.setTitle(ResUtil.getString(R.string.shuashenfenzheng));
            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingshurushoujihaomahesuijimima));
            }
        } else if (step == 4) {
            if (isIdCard) {
                mTopView.setTitle("请输入密码");
            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingxuanzezhifuleixing));
            }
        } else if (step == 5) {
            if (isIdCard) {
                if (isQr) {
                    mTopView.setTitle(ResUtil.getString(R.string.qingfukuan));
                } else {
                    mTopView.setTitle(ResUtil.getString(R.string.qingxuanzezhifuleixing));
                }

            } else {
                mTopView.setTitle(ResUtil.getString(R.string.qingfukuan));
            }
        } else if (step == 6) {
            mTopView.setVisibility(View.GONE);
        }

        if (step != 6) {
            mCountDownTimerUtils.start();
        } else {
            mCountDownTimerUtils.cancel();
        }
    }

    public void startCountDownTime() {
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.start();
        }
    }

    private void initCountDownUtil() {
        mCountDownTimerUtils = CountDownTimerUtils.getCountDownTimer()
                .setMillisInFuture(PrefUtil.getDefault().getLong("TOTAL_COUNT_DOWN_TIME", 6000L))
                .setCountDownInterval(1000)
                .setTickAndFinishDelegate(new CountDownTimerUtils.TickAndFinishDelegate() {
                    @Override
                    public void onFinish() {
                        closeActivity();
                    }

                    @Override
                    public void onTick(long pMillisUntilFinished) {
                        mTopView.setCountdown((int) pMillisUntilFinished / 1000);
                    }
                });
    }

    public void showFragment(int index) {
        switch (index) {
            case 0:
                mTopView.setTitle("使用说明");
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_frame, new ProductDescriptionFragment()).commit();
                mCountDownTimerUtils.start();
                mTopView.setLeftIcon(R.mipmap.icon_1);
                break;
            case 1:
                setBundle("getmyself");
                mTopView.setLeftIcon(R.mipmap.ic_takeownt001);
                break;
            case 2:
                setBundle("changegift");
                mTopView.setLeftIcon(R.mipmap.ic_change001);
                break;
            case 3:
                setBundle("sendgift");
                mTopView.setLeftIcon(R.mipmap.ic_present001);
                break;
        }
    }

    private void setBundle(String keyName) {
        mTopView.setTitle("");
        InstructionsFragment sendFragment = new InstructionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyName", keyName);
        sendFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_frame, sendFragment).commit();
    }

    public void switchFragment(Fragment targetFragment, Bundle bundle) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (bundle != null) {
            //设置传递参数
            targetFragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.fl_frame, targetFragment);
        fragmentTransaction.commit();
    }

    public void closeActivity() {
        mTopView.setTitle("");
        mCountDownTimerUtils.cancel();
        mFlMain.setVisibility(View.VISIBLE);
        mLlFragment.setVisibility(View.GONE);
        mTopView.setEmpty("");
        if (emptyFragment == null) {
            emptyFragment = new EmptyFragment();
        }
        switchFragment(emptyFragment, null);
        //finish();
    }

    public void setTopViewText(String text) {
        mTopView.setTitle(text);
    }


    @OnClick({R.id.btn_save, R.id.btn_take_out, R.id.btn_guide})
    public void onClick(View view) {
        if (mCountDownTimerUtils == null) {
            showToast("正在获取数据...");
            getCountDownTime();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_save:
                mMyTitleBar.setStepViewVisiable(true);
                mMyTitleBar.setActionVisiable(true);
                mFlMain.setVisibility(View.GONE);
                mLlFragment.setVisibility(View.VISIBLE);
                showFirstFragment();
                break;
            case R.id.btn_take_out:
                mMyTitleBar.setStepViewVisiable(true);
                mMyTitleBar.setActionVisiable(true);
                mFlMain.setVisibility(View.GONE);
                mLlFragment.setVisibility(View.VISIBLE);
                showTokeoutFirstFragment();
                break;
            case R.id.btn_guide:
                mMyTitleBar.setStepViewVisiable(false);
                mMyTitleBar.setActionVisiable(false);
                mFlMain.setVisibility(View.GONE);
                mLlFragment.setVisibility(View.VISIBLE);
                showFragment(0);
                break;
        }
    }

    class TimeChangeReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_TIME_TICK:
                    //每过一分钟 触发
                    mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));
                    break;
                case Intent.ACTION_TIME_CHANGED:
                    //设置了系统时间
                    mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    //设置了系统时区的action
                    mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));
                    break;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Constant.MACNO = ANDROID_ID;
//        Constant.MACNO = "14564497c08614cf";
        mTvDeviceNo.setText("设备号: \n" + Constant.MACNO);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区Fti
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);
        mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));

        showWaitingDialog("上传token中", true);
        Log.i("Jim","6666666666");
        uploadToken();
        getCountDownTime();

//        showSecondDisplay();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fl_frame, new HSMain_fragment());
//        fragmentTransaction.commit();
    }

    private void uploadToken() {
        mHandler.postDelayed(() -> {
            if (TextUtils.isEmpty(MainUrls.umToken)) {
                uploadToken();
            } else {
                MainHttp.get().uploadUMToken(MainUrls.umToken, Constant.MACNO, new Bean01Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        dismissWaitingDialog();
                        initView();
                        connectTcp();
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {
                        showOneToast(message);
                        uploadToken();
                    }
                });
            }

        }, 1000);


    }

    private void connectTcp() {
        Constant.DEVICE_PATH = "/dev/ttyS0";

        TemplateVendingCabinets.getVendingCabinets().start(Constant.MACNO, Constant.ADDRESS, Constant.PORT, Constant.DEVICE_PATH, Constant.BAUDRATE);
        TemplateVendingCabinets.getVendingCabinets().setLogListener(new LogListener() {
            @Override
            public void onLog(String log) {

                Log.i("Jim", "TCP返回信息" + log);

            }
        });
    }

    private void initView() {

        mIvLogo.setImageResource(R.mipmap.logo_icon);
        mTvName.setText("共享寄存柜");
        advertisement();
        MainHttp.get().getMainInfo(new Bean01Callback<MainInfoBean>() {
            @Override
            public void onSuccess(MainInfoBean mainInfoBean) {
                //设置左上角二维码信息
                mTvWxTitle.setText(mainInfoBean.data.wxcode.title);
                Glide.with(HalfSizeActivty.this)
                        .load(mainInfoBean.data.wxcode.value)
                        .apply(new RequestOptions().transform(new CenterCrop()))
                        .into(mIvQrOfficial);
                //设置客服电话
                mTvPhone.setText("客服电话：\n" + mainInfoBean.data.call.link);
                //设置右上角第一个二维码信息
                mTvQrCode1.setText(mainInfoBean.data.wxpay.title);
                Glide.with(HalfSizeActivty.this)
                        .load(mainInfoBean.data.wxpay.value)
                        .apply(new RequestOptions().transform(new CenterCrop()))
                        .into(mIvQrWechat);
                //设置右上角第二个二维码信息
                mTvQrCode2.setText(mainInfoBean.data.alipay.title);
                Glide.with(HalfSizeActivty.this)
                        .load(mainInfoBean.data.alipay.value)
                        .apply(new RequestOptions().transform(new CenterCrop()))
                        .into(mIvQrAli);
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showOneToast(message);
            }
        });
    }

    private void getCountDownTime() {
        MainHttp.get().countDowntime(new Bean01Callback<CountDownTiemBean>() {
            @Override
            public void onSuccess(CountDownTiemBean countDownTiemBean) {
                PrefUtil.getDefault().saveLong("TOTAL_COUNT_DOWN_TIME", Long.valueOf(countDownTiemBean.getData().get(1).getValue()) * 1000);
                initCountDownUtil();
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showToast(message);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onDestroy() {
        unregisterReceiver(timeChangeReceiver);
        if (mSecondDisplay != null) {
            mSecondDisplay.mVideo.stopPlayback();
        }
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
        RichText.clear(this);
        RichText.recycle();
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showSecondDisplay() {
        DisplayManager mDisplayManager;// 屏幕管理类
        mDisplayManager = (DisplayManager) this
                .getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

        if (mSecondDisplay == null && displays.length > 1) {
            mSecondDisplay = new SecondDisplay(HalfSizeActivty.this, displays[displays.length - 1]);// displays[1]是副屏

            mSecondDisplay.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mSecondDisplay.show();
        }

    }

    public void playMedia(String name) {
        try {
            Field idField = R.raw.class.getDeclaredField(name);
            int res = idField.getInt(idField);
            mMediaPlayer = MediaPlayer.create(this, res);
            try {
                mMediaPlayer.prepare();
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(this, res);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if ((currentTimeMillis() - exittime) > 1000) {
            showToast("再按一次退出程序");
            exittime = currentTimeMillis();
        } else {
            finish();
        }
    }

    private void advertisement() {
        if(task != null){
            task.cancel();
        }
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                MainHttp.get().getAdvertByKey(new Bean01Callback<Advert>() {
                    @Override
                    public void onSuccess(Advert advert) {
                        List<String> strings = new ArrayList<>();
                        for (Advert.DataBean.ImgBean imgBean : advert.getData().get(0).getImg()) {
                            strings.add(imgBean.getValue());
                        }
                        mBanner.setImages(strings).setImageLoader(new GlideImageLoader()).start();
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {

                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task,0,3600 * 1000);

    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).asBitmap().load(path).into(imageView);
        }
    }
}
