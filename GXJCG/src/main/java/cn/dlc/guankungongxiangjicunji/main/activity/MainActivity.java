package cn.dlc.guankungongxiangjicunji.main.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dlc.vendingcabinets.Constant;
import com.dlc.vendingcabinets.TemplateVendingCabinets;
import com.dlc.vendingcabinets.mInterface.LogListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.MainUrls;
import cn.dlc.guankungongxiangjicunji.main.bean.BaseBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CountDownTiemBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MainInfoBean;
import cn.dlc.guankungongxiangjicunji.main.widget.ImageTextView;

import static java.lang.System.currentTimeMillis;

/**
 * Created by wuyufeng on 2018/6/22.
 */
public class MainActivity extends BaseActivity {
    
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    //    @BindView(R.id.tv_name)
//    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_qr_Official)
    ImageView mIvQrOfficial;
    //    @BindView(R.id.tv_phone)
//    TextView mTvPhone;
    @BindView(R.id.iv_qr_wechat)
    ImageView mIvQrWechat;
    @BindView(R.id.iv_qr_ali)
    ImageView mIvQrAli;
    @BindView(R.id.btn_save)
    ImageButton mBtnSave;
    @BindView(R.id.btn_take_out)
    ImageButton mBtnTakeOut;
    @BindView(R.id.btn_guide)
    ImageButton mBtnGuide;
//    @BindView(R.id.item_save)
//    ImageTextView mItemSave;
//    @BindView(R.id.item_change)
//    ImageTextView mItemChange;
//    @BindView(R.id.item_gift)
//    ImageTextView mItemGift;
//    @BindView(R.id.tv_wx_title)
//    TextView mTvWxTitle;
//    @BindView(R.id.tv_qr_code_1)
//    TextView mTvQRCode1;
//    @BindView(R.id.tv_qr_code_2)
//    TextView mTvQRCode2;
//    @BindView(R.id.tv_device_no)
//    TextView mTvDeviceNo;
    
    public static SecondDisplay mSecondDisplay;
    
    public static MediaPlayer mMediaPlayer = new MediaPlayer();
    
    public static Context mContext;
    
    private Intent serviceIntent;
    
    private long exittime = 0;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    //    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));
//            }
//        }
//    };
    //private Handler mHandler = new Handler();
    private boolean canClick = true;//防止多次点击
    
    private MyHandler mHandler = new MyHandler();
    
    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
    
    
    private TimeChangeReceiver timeChangeReceiver;
    
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
    protected int getLayoutID() {
        return R.layout.activity_main;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //  getTime();
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Constant.MACNO = ANDROID_ID;
//        Constant.MACNO = "14564497c08614cf";
//        mTvDeviceNo.setText("设备号: " + Constant.MACNO);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区o
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);
        mTvTime.setText(mSimpleDateFormat.format(System.currentTimeMillis()));
        showWaitingDialog("上传token中", true);
        uploadToken();
        getCountDownTime();
//        serviceIntent = new Intent(this, HomeService.class);
//        startService(serviceIntent);
        // getPermission();
//        Intent intent = new Intent(this, HomeService.class);
//        tService(intent);
    }
    
    
    private void uploadToken() {
        mHandler.postDelayed(() -> {
            LogUtils.e(MainUrls.umToken + " " + PrefUtil.getDefault().getString("Type", "FULL"));
            if (TextUtils.isEmpty(MainUrls.umToken)) {
                uploadToken();
            } else {
                Log.i("Jim", "to uploadToken");
                MainHttp.get().uploadUMToken(MainUrls.umToken, Constant.MACNO, new Bean01Callback<BaseBean>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        Log.i("Jim", baseBean.toString());
                        dismissWaitingDialog();
                        connectTcp();
                        initView();
                        showSecondDisplay();
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
        LogUtils.e(DeviceUtils.getMacAddress() + "" + DeviceUtils.getAndroidID() + " " + DeviceUtils.getModel());
//        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
//        Constant.MACNO = ANDROID_ID;
////        Constant.MACNO = "14564497c08614cf";
//        mTvDeviceNo.setText("设备号: " + Constant.MACNO );
        //双屏，则用双屏串口地址，否则用USB地址
//        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
//            //{"data":{"water":"20180804150726962818206","status":-1,"oid":"20180804150726962818206"},"type":"openDoor","msg":"出货失败，未检测到商品掉落","macno":"b44b60fa8b62ecd4"}
//            Constant.DEVICE_PATH = "/dev/ttyS1";
//        } else {
//            Constant.DEVICE_PATH = "/dev/ttyS0";
//        }
        Constant.DEVICE_PATH = "/dev/ttyS0";
        Constant.ADDRESS = "10.105.145.18";//39.108.111.205
        Constant.MACNO = "bf4b69e0d5e2a2bb";
        try {
//          Constant.ADDRESS = "47.106.183.95";
            TemplateVendingCabinets.getVendingCabinets().start(Constant.MACNO, Constant.ADDRESS, Constant.PORT, Constant.DEVICE_PATH, Constant.BAUDRATE);
            //设置列数
            TemplateVendingCabinets.getVendingCabinets().setColumns(0, 6);
            TemplateVendingCabinets.getVendingCabinets().setLogListener(new LogListener() {
                @Override
                public void onLog(String log) {
                    LogUtils.e("TCP返回信息" + log);
                }
            });
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }
    
    private void getCountDownTime() {
        MainHttp.get().countDowntime(new Bean01Callback<CountDownTiemBean>() {
            @Override
            public void onSuccess(CountDownTiemBean countDownTiemBean) {
                PrefUtil.getDefault().saveLong("TOTAL_COUNT_DOWN_TIME", Long.valueOf(countDownTiemBean.getData().get(1).getValue()) * 1000);
            }
            
            @Override
            public void onFailure(String message, Throwable tr) {
                showToast(message);
            }
        });
    }
    
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onResume() {
        super.onResume();
        canClick = true;
        if (mSecondDisplay != null) {
            mSecondDisplay.setActivity(this);
        }
    }
    
    private void initView() {
        mIvLogo.setImageResource(R.mipmap.logo_icon);
//        mTvName.setText("共享寄存柜");
        MainHttp.get().getMainInfo(new Bean01Callback<MainInfoBean>() {
            @Override
            public void onSuccess(MainInfoBean mainInfoBean) {
                //设置左上角二维码信息
//                mTvWxTitle.setText(mainInfoBean.data.wxcode.title);
                Glide.with(MainActivity.this)
                        .load(mainInfoBean.data.wxcode.value)
                        .apply(new RequestOptions().transform(new CenterCrop()))
                        .into(mIvQrOfficial);
                //设置客服电话
//                mTvPhone.setText(String.format(ResUtil.getString(R.string.service_phone_), mainInfoBean.data.call.link));
                //设置右上角第一个二维码信息
//                mTvQRCode1.setText(mainInfoBean.data.wxpay.title);
                Glide.with(MainActivity.this)
                        .load(mainInfoBean.data.wxpay.value)
                        .apply(new RequestOptions().transform(new CenterCrop()))
                        .into(mIvQrWechat);
                //设置右上角第二个二维码信息
//                mTvQRCode2.setText(mainInfoBean.data.alipay.title);
                Glide.with(MainActivity.this)
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
    
    
    @OnClick({R.id.btn_save, R.id.btn_take_out, R.id.btn_guide})
    public void onViewClicked(View view) {
        Long value = PrefUtil.getDefault().getLong("TOTAL_COUNT_DOWN_TIME", 0L);
        if (value == 0L) {
            showToast("正在获取数据...");
            getCountDownTime();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_save://存物
                if (canClick) {
                    canClick = false;
                    startActivity(SaveActivity.class);
                }
//                playMedia("save10");
                
                break;
            case R.id.btn_take_out://取物
                if (canClick) {
                    canClick = false;
                    startActivity(TakeOutActivity.class);
                }
                break;
            case R.id.btn_guide://使用说明 
                if (canClick) {
                    canClick = false;
                    startActivity(ProductDescriptionActivity.class);
                }
                
                break;
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
    
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(serviceIntent);
        unregisterReceiver(timeChangeReceiver);
//        if (mSecondDisplay != null && mSecondDisplay.mVideo != null) {
//            mSecondDisplay.mVideo.stopPlayback();
//        }
        
        if (mSecondDisplay != null) {
            mSecondDisplay.cancel();
        }
    }
    
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showSecondDisplay() {
        DisplayManager mDisplayManager;// 屏幕管理类
        mDisplayManager = (DisplayManager) this
                .getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();

//        if (mSecondDisplay == null) {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            mSecondDisplay = new SecondDisplay(MainActivity.this, displays[displays.length - 1], MainActivity.this);// displays[1]是副屏
            mSecondDisplay.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            mSecondDisplay.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mSecondDisplay.show();
            mSecondDisplay.setActivity(MainActivity.this);
        }
//        }
    
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
    
    public static void playMedia(String name) {
        try {
            Field idField = R.raw.class.getDeclaredField(name);
            int res = idField.getInt(idField);
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(mContext, res);
            } else {
                mMediaPlayer = MediaPlayer.create(mContext, res);
            }
//            mMediaPlayer.setDataSource(idF);
//            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + name));
            try {
//                mMediaPlayer.prepare();
            
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
}
