package cn.dlc.guankungongxiangjicunji.main.fragment;

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
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.IdCardBean;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class IdentifyIdCardFragment extends BaseFragment {

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

    private CabinetListBean.DataBean mDataBean;

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_identity_id_card;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_identity_id_card_hs;
        }
        return R.layout.fragment_identity_id_card;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");
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
            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }


    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }
                //mActivity.closeActivity();
                break;
        }
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
                    mBundle.putSerializable("mDataBean", mDataBean);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getIdCardWayFragment(), mBundle);
                            } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getIdCardWayFragment(), mBundle);
                            }else{
                                ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getIdCardWayFragment(), mBundle);
                            }
                        }
                    });

                    // mActivity.switchFragment(mActivity.getIdCardWayFragment(),mBundle);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (timerTask != null)
            timerTask.cancel();
        if (timer != null)
            timer.cancel();
    }

    //    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(hidden){
//            //当前被隐藏
//            if(timerTask != null)
//                timerTask.cancel();
//            if(timer != null)
//                timer.cancel();
//        }else{
//            //没有被隐藏
//            readCard();
//        }
//    }
}
