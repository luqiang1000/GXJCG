package cn.dlc.guankungongxiangjicunji.main.activity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import butterknife.BindView;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.ConstantInf;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
import cn.dlc.guankungongxiangjicunji.main.fragment.InstructionsFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.ProductDescriptionFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.WebViewFragment;
import cn.dlc.guankungongxiangjicunji.main.widget.CountDownTimerUtils;
import cn.dlc.guankungongxiangjicunji.main.widget.MyTitleBar;
import cn.dlc.guankungongxiangjicunji.main.widget.TopView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwenzhuo on 2018/6/22.
 */

public class ProductDescriptionActivity extends BaseActivity {
    
    @BindView(R.id.my_title_bar)
    MyTitleBar mMyTitleBar;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.top_view)
    TopView mTopView;
    
    public static final String PRODUCT_DESCRIPTION_FRAGMENT = "0";
    public static final String SELF_FRAGMENT = "1";
    public static final String FRIEND_FRAGMENT = "2";
    public static final String GIFT_FRAGMENT = "3";
    
    private CountDownTimerUtils mCountDownTimerUtils;
    
    private MediaPlayer mMediaPlayer;
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_product_description;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCountDownUtil();
        initFragment();
        playMedia("welcome");
        if (MainActivity.mSecondDisplay != null) {
            MainActivity.mSecondDisplay.setActivity(this);
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
    
    private void initCountDownUtil() {
        mCountDownTimerUtils = new CountDownTimerUtils().getCountDownTimer()
                .setMillisInFuture(PrefUtil.getDefault().getLong("TOTAL_COUNT_DOWN_TIME", 0L))
                .setCountDownInterval(1000)
                .setTickAndFinishDelegate(new CountDownTimerUtils.TickAndFinishDelegate() {
                    @Override
                    public void onFinish() {
                        finish();
                    }
                    
                    @Override
                    public void onTick(long pMillisUntilFinished) {
                        mTopView.setCountdown((int) pMillisUntilFinished / 1000);
                    }
                });
    }
    
    private void initFragment() {
        
        showFragment(0);
    }
    
    public void showFragment(int index) {
        switch (index) {
            case 0:
                mTopView.setTitle("使用说明");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProductDescriptionFragment()).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, sendFragment).commit();
    }
    
    
    public void startCountDownTime() {
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.start();
        }
    }
    
    public void setTopViewText(String text) {
        mTopView.setTitle(text);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
    }
}
