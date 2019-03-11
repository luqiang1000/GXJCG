package cn.dlc.guankungongxiangjicunji.main.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.ConstantInf;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
import cn.dlc.guankungongxiangjicunji.main.MainUrls;
import cn.dlc.guankungongxiangjicunji.main.fragment.IdCardWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.IdentifyIdCardFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.OpenCabinetFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.PayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.PhoneWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SafeGuideFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SaveSucceedFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectPayWayFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectSpecFragment;
import cn.dlc.guankungongxiangjicunji.main.fragment.SelectUseWayFragment;
import cn.dlc.guankungongxiangjicunji.main.widget.CountDownTimerUtils;
import cn.dlc.guankungongxiangjicunji.main.widget.MyTitleBar;
import cn.dlc.guankungongxiangjicunji.main.widget.TopView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyufeng on 2018/6/22.
 */
public class SaveActivity extends BaseActivity {
    
    @BindView(R.id.my_title_bar)
    MyTitleBar mMyTitleBar;
    @BindView(R.id.title_new)
    RelativeLayout title_new;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.top_view)
    TopView mTopView;
    
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    
    private CountDownTimerUtils mCountDownTimerUtils;
    //    private Fragment currentFragment;
    private SafeGuideFragment mSafeGuideFragment;
    private SelectSpecFragment mSelectSpecFragment;
    private SelectUseWayFragment mSelectUseWayFragment;
    private PhoneWayFragment mPhoneWayFragment;
    private SelectPayWayFragment mSelectPayWayFragment;
    private PayFragment mPayFragment;
    //    private List<Fragment> mFragments = new ArrayList<>();
    private OpenCabinetFragment mOpenCabinetFragment;
    private SaveSucceedFragment mSaveSucceedFragment;
    private IdentifyIdCardFragment mIdentifyIdCardFragment;
    private IdCardWayFragment mIdCardWayFragment;
    
    public SafeGuideFragment getSafeGuideFragment() {
        return mSafeGuideFragment;
    }
    
    public SelectSpecFragment getSelectSpecFragment() {
        return mSelectSpecFragment;
    }
    
    public SelectUseWayFragment getSelectUseWayFragment() {
        return mSelectUseWayFragment;
    }
    
    public PhoneWayFragment getPhoneWayFragment() {
        return new PhoneWayFragment();
    }
    
    public SelectPayWayFragment getSelectPayWayFragment() {
        return mSelectPayWayFragment;
    }
    
    public PayFragment getPayFragment() {
        return mPayFragment;
    }
    
    public OpenCabinetFragment getOpenCabinetFragment() {
        return mOpenCabinetFragment;
    }
    
    public SaveSucceedFragment getSaveSucceedFragment() {
        return mSaveSucceedFragment;
    }
    
    public IdentifyIdCardFragment getIdentifyIdCardFragment() {
        return mIdentifyIdCardFragment;
    }
    
    public IdCardWayFragment getIdCardWayFragment() {
        return mIdCardWayFragment;
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_save;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCountDownUtil();
        initFragment();
        if (MainActivity.mSecondDisplay != null) {
            MainActivity.mSecondDisplay.setActivity(this);
        }
    }
    
    
    private void initCountDownUtil() {
        mCountDownTimerUtils = CountDownTimerUtils.getCountDownTimer()
                .setMillisInFuture(PrefUtil.getDefault().getLong("TOTAL_COUNT_DOWN_TIME", 0L))
                .setCountDownInterval(1000)
                .setTickAndFinishDelegate(new CountDownTimerUtils.TickAndFinishDelegate() {
                    @Override
                    public void onFinish() {
                        finish();
                    }
                    
                    @Override
                    public void onTick(long pMillisUntilFinished) {
                        if (mTopView != null) {
                            mTopView.setCountdown((int) pMillisUntilFinished / 1000);
                        }
                    }
                });
    }
//    public void playMedia(String name) {
//        try {
//            Field idField = R.raw.class.getDeclaredField(name);
//            int res = idField.getInt(idField);
////            mMediaPlayer = MediaPlayer.create(this, res);
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + name));
//            try {
//                mMediaPlayer.prepare();
//                if(mMediaPlayer.isPlaying()){
//                    mMediaPlayer.stop();
//                    mMediaPlayer.release();
//                    mMediaPlayer = MediaPlayer.create(this,res);
//                }
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mMediaPlayer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void initFragment() {
        mSafeGuideFragment = new SafeGuideFragment();
        mSelectSpecFragment = new SelectSpecFragment();
        mSelectUseWayFragment = new SelectUseWayFragment();
        mPhoneWayFragment = new PhoneWayFragment();
        mSelectPayWayFragment = new SelectPayWayFragment();
        mPayFragment = new PayFragment();
        mOpenCabinetFragment = new OpenCabinetFragment();
        mSaveSucceedFragment = new SaveSucceedFragment();
        mIdentifyIdCardFragment = new IdentifyIdCardFragment();
        mIdCardWayFragment = new IdCardWayFragment();
        
        showFirstFragment();
    }
    
    private void showFirstFragment() {
        switchFragment(mSafeGuideFragment, null);
        setStep(1, false, false);
    }
    
    /**
     * @param step
     * @param isQr
     * @param isIdCard 是否是刷身份证
     */
    public void setStep(int step, boolean isQr, boolean isIdCard) {
        if (mMyTitleBar != null) {
            mMyTitleBar.setStep(step);
        }
        if (mTopView != null) {
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
                    mTopView.setTitle(ResUtil.getString(R.string.shezhimima));
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
                mTopView.setTitle(ResUtil.getString(R.string.qingfukuan));
                mTopView.setVisibility(View.GONE);
                mCountDownTimerUtils.cancel();
            }
        }


//        if (step != 6) {
//            mCountDownTimerUtils.start();
//        } else {
//            mCountDownTimerUtils.cancel();
//        }
    }
    
    public void startCountDownTime() {
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.start();
        }
    }
    
    public void switchFragment(Fragment targetFragment, Bundle bundle) {
        if (targetFragment instanceof SafeGuideFragment) {
            mMyTitleBar.setVisibility(View.VISIBLE);
            title_new.setVisibility(View.GONE);
        }else{
            mMyTitleBar.setVisibility(View.GONE);
            title_new.setVisibility(View.VISIBLE);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (bundle != null) {
            //设置传递参数
            targetFragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.container, targetFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    
    public void closeActivity() {
        finish();
    }
    
    public void setTopViewText(String text) {
        if (mTopView != null) {
            mTopView.setTitle(text);
        }
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
        RichText.clear(this);
        RichText.recycle();
    }
}
