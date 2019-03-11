package cn.dlc.guankungongxiangjicunji.main.activity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseActivity;
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
import cn.dlc.guankungongxiangjicunji.main.widget.MyTitleBar;
import cn.dlc.guankungongxiangjicunji.main.widget.TopView;

/**
 * Created by wuyufeng on 2018/6/22.
 */
public class TakeOutActivity extends BaseActivity {
    
    @BindView(R.id.my_title_bar)
    MyTitleBar mMyTitleBar;
    @BindView(R.id.title_new)
    RelativeLayout title_new;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.top_view)
    TopView mTopView;
    
    private MediaPlayer mMediaPlayer;
    
    private CountDownTimerUtils mCountDownTimerUtils;
    private TakeOutPhoneWayFragment mTakeOutPhoneWayFragment;
    private TakeOutSafeGuideFragment mTakeOutSafeGuideFragment;
    private TakeOutSelectUseWayFragment mTakeOutSelectUseWayFragment;
    private TakeOutOpenCabinetFragment mTakeOutOpenCabinetFragment;
    private TakeOutSelectPayWayFragment mTakeOutSelectPayWayFragment;
    private TakeOutSaveSucceedFragment mTakeOutSaveSucceedFragment;
    private TakeOutPayFragment mTakeOutPayFragment;
    private TakeOutIdentifyIdCardFragment mTakeOutIdentifyIdCardFragment;
    private TakeOutIdCardWayFragment mTakeOutIdCardWayFragment;
    
    public TakeOutPhoneWayFragment getTakeOutPhoneWayFragment() {
        return mTakeOutPhoneWayFragment;
    }
    
    public TakeOutSafeGuideFragment getTakeOutSafeGuideFragment() {
        return mTakeOutSafeGuideFragment;
    }
    
    public TakeOutSelectUseWayFragment getTakeOutSelectUseWayFragment() {
        return mTakeOutSelectUseWayFragment;
    }
    
    public TakeOutSelectPayWayFragment getTakeOutSelectPayWayFragment() {
        return mTakeOutSelectPayWayFragment;
    }
    
    public TakeOutSaveSucceedFragment getTakeOutSaveSucceedFragment() {
        return mTakeOutSaveSucceedFragment;
    }
    
    public TakeOutPayFragment getTakeOutPayFragment() {
        return mTakeOutPayFragment;
    }
    
    public TakeOutOpenCabinetFragment getTakeOutOpenCabinetFragment() {
        return mTakeOutOpenCabinetFragment;
    }
    
    public TakeOutIdentifyIdCardFragment getTakeOutIdentifyIdCardFragment() {
        return mTakeOutIdentifyIdCardFragment;
    }
    
    public TakeOutIdCardWayFragment getTakeOutIdCardWayFragment() {
        return mTakeOutIdCardWayFragment;
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_take_out;
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

//    public void playMedia(String name){
//        try {
//            Field idField = R.raw.class.getDeclaredField(name);
//            int res = idField.getInt(idField);
////            mMediaPlayer=MediaPlayer.create(this, res);
////            mMediaPlayer.setDataSource(idF);
//            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + name));
//            try {
//                mMediaPlayer.prepare();
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mMediaPlayer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    
    
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
                        if (mTopView != null) {
                            mTopView.setCountdown((int) pMillisUntilFinished / 1000);
                        }
                    }
                });
    }
    
    private void initFragment() {
        mTakeOutSafeGuideFragment = new TakeOutSafeGuideFragment();
        mTakeOutSelectUseWayFragment = new TakeOutSelectUseWayFragment();
        mTakeOutPhoneWayFragment = new TakeOutPhoneWayFragment();
        mTakeOutSelectPayWayFragment = new TakeOutSelectPayWayFragment();
        mTakeOutPayFragment = new TakeOutPayFragment();
        mTakeOutOpenCabinetFragment = new TakeOutOpenCabinetFragment();
        mTakeOutSaveSucceedFragment = new TakeOutSaveSucceedFragment();
        mTakeOutIdentifyIdCardFragment = new TakeOutIdentifyIdCardFragment();
        mTakeOutIdCardWayFragment = new TakeOutIdCardWayFragment();
        
        showFirstFragment();
    }
    
    private void showFirstFragment() {
        switchFragment(mTakeOutSafeGuideFragment, null);
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
            } else if (step == 6) {
                mTopView.setVisibility(View.GONE);
            }
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
    
    public void switchFragment(Fragment targetFragment, Bundle bundle) {
        if (targetFragment instanceof TakeOutSafeGuideFragment) {
            mMyTitleBar.setVisibility(View.VISIBLE);
            title_new.setVisibility(View.GONE);
        } else {
            title_new.setVisibility(View.VISIBLE);
            mMyTitleBar.setVisibility(View.GONE);
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
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
        }
        super.onDestroy();
        RichText.clear(this);
        RichText.recycle();
    }
}
