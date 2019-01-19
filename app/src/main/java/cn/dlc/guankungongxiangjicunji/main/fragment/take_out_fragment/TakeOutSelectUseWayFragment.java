package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

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
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutSelectUseWayFragment extends BaseFragment {

    @BindView(R.id.fl_btn_phone)
    FrameLayout mFlBtnPhone;
    @BindView(R.id.fl_btn_id_card)
    FrameLayout mFlBtnIdCard;
    @BindView(R.id.btn_back)
    Button mBtnBack;


    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
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
//            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
//            ((HalfSizeActivty) mActivity).startCountDownTime();
        }else{
            //无身份证布局
//            ((TakeOutActivity) mActivity).startCountDownTime();
            mFlBtnIdCard.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.btn_back, R.id.fl_btn_phone, R.id.fl_btn_id_card})
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
            case R.id.fl_btn_phone:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).setStep(3, false, false);
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutPhoneWayFragment(), null);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).setTakeoutStep(3, false, false);
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutPhoneWayFragment(), null);
                }else{
                    ((TakeOutActivity) mActivity).setStep(3, false, false);
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutPhoneWayFragment(), null);
                }
                // mActivity.setStep(3, false, false);
                //  mActivity.switchFragment(mActivity.getTakeOutPhoneWayFragment(), null);
                break;
            case R.id.fl_btn_id_card:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).setStep(3, false, true);
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutIdentifyIdCardFragment(), null);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).setTakeoutStep(3, false, true);
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutIdentifyIdCardFragment(), null);
                }else{
                    ((TakeOutActivity) mActivity).setStep(3, false, true);
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutIdentifyIdCardFragment(), null);
                }

                break;
        }
    }


}
