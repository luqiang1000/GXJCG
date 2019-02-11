package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class SelectUseWayFragment extends BaseFragment {

    @BindView(R.id.fl_btn_phone)
    FrameLayout mFlBtnPhone;
    @BindView(R.id.fl_btn_id_card)
    FrameLayout mFlBtnIdCard;
    @BindView(R.id.btn_back)
    Button mBtnBack;


    private CabinetListBean.DataBean mDataBean;

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_use_way;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_use_way_hs;
        }
        return R.layout.fragment_select_use_way;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }else{
            //无身份证布局
            ((SaveActivity) mActivity).startCountDownTime();
            mFlBtnIdCard.setVisibility(View.GONE);
        }
        //mActivity.startCountDownTime();
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");
    }


    @OnClick({R.id.btn_back, R.id.fl_btn_phone, R.id.fl_btn_id_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    ((SaveActivity) mActivity).closeActivity();
                }
                //  mActivity.closeActivity();
                break;
            case R.id.fl_btn_phone:
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("mDataBean", mDataBean);
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                        || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).setStep(4, false, false);
                    ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getPhoneWayFragment(), mBundle);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).setStep(4, false, false);
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getPhoneWayFragment(), mBundle);
                }

                break;
            case R.id.fl_btn_id_card:
                Bundle mBundle1 = new Bundle();
                mBundle1.putSerializable("mDataBean", mDataBean);
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).setStep(3, false, true);
                    ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getIdentifyIdCardFragment(), mBundle1);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).setStep(3, false, true);
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getIdentifyIdCardFragment(), mBundle1);
                }
                break;
        }
    }


}
