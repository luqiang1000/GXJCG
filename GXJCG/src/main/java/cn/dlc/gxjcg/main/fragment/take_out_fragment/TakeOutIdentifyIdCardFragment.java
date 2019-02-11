package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.invs.UsbSam;
import com.invs.invsIdCard;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.IdCardBean;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutIdentifyIdCardFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.iv_tip)
    ImageView mIvTip;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    private Timer timer;

    private TimerTask timerTask;

    private invsIdCard mCard;

    private Handler mHandler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_id_card;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        setShowUi(1);
        readCard();
    }

    /**
     * 根据身份证读取的步骤，来设置不同UI
     *
     * @param showType 1 表示请将身份证置于读卡区  2 正在读取  3 身份证读取完毕
     */
    public void setShowUi(int showType) {

        if (showType == 1) {
            mIvBg.setImageResource(R.mipmap.im_0001);
            mTvTip.setText(ResUtil.getString(R.string.zunjingdeyonghu));
            mTvTip.setTextColor(ResUtil.getColor(R.color.color_FA8D0D));
            mIvTip.setVisibility(View.GONE);
        } else if (showType == 2) {
            mIvBg.setImageResource(R.mipmap.im_0002);
            mTvTip.setText(ResUtil.getString(R.string.zhengzaiduqushenfenzhengxinxi));
            mTvTip.setTextColor(ResUtil.getColor(R.color.color_F55007));
            mIvTip.setVisibility(View.VISIBLE);
        } else {
            mIvBg.setImageResource(R.mipmap.im_0003);
            mTvTip.setText(ResUtil.getString(R.string.shenfengzhengduquwanbi));
            mTvTip.setTextColor(ResUtil.getColor(R.color.color_2AC43F));
            mIvTip.setVisibility(View.GONE);
        }

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((TakeOutActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }else{
            ((TakeOutActivity) mActivity).startCountDownTime();
        }
        //mActivity.startCountDownTime();


//        //这里模拟读取完毕身份证后跳转到设置密码界面(请根据需求放在合适的地方调用)
//        mActivity.switchFragment(mActivity.getTakeOutIdCardWayFragment(),null);
//        mActivity.getTakeOutIdCardWayFragment().setView(new IdCardBean("周二吉伦","35085536995222222"));
//        mActivity.setStep(4, false, true);

    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    ((TakeOutActivity) mActivity).closeActivity();
                }
                // mActivity.closeActivity();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (timerTask != null)
            timerTask.cancel();
        if (timer != null)
            timer.cancel();
    }

    private void readCard() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                UsbSam mTermb = new UsbSam();
                int iRet = mTermb.ReadCard(getActivity(), false, 50);
                if (iRet == 0) {
                    mCard = mTermb.mCard;
                    Bundle mBundle = new Bundle();
                    mBundle.putString("idCard", mCard.idNo);
                    mBundle.putString("name", mCard.name);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutIdCardWayFragment(), mBundle);
                                ((TakeOutActivity) mActivity).setStep(4, false, true);
                            } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutIdCardWayFragment(), mBundle);
                                ((HalfSizeActivty) mActivity).setStep(4, false, true);
                            }else{
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutIdCardWayFragment(), mBundle);
                                ((TakeOutActivity) mActivity).setStep(4, false, true);
                            }
                        }
                    });
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
}
