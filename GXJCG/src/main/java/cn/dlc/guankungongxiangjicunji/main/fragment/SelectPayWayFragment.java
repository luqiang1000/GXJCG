package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.PayBean;
import cn.dlc.guankungongxiangjicunji.main.widget.GetQrCodeDialog;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class SelectPayWayFragment extends BaseFragment {

    @BindView(R.id.fl_btn_wechat)
    FrameLayout mFlBtnWechat;
    @BindView(R.id.fl_btn_zhifubao)
    FrameLayout mFlBtnZhifubao;
    @BindView(R.id.btn_back)
    Button mBtnBack;

    private String mPayLog = "";
    private String money;
    private String token;
    private String goodsno;//需要打开的柜子
    private CabinetListBean.DataBean mDataBean;


    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_pay_way;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_pay_way_hs;
        }
        return R.layout.fragment_select_pay_way;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPayLog = getArguments().getString("paylog");
        money = getArguments().getString("money");
        token = getArguments().getString("token");
        goodsno = getArguments().getString("goodsno");
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }
    }


    @OnClick({R.id.btn_back, R.id.fl_btn_wechat, R.id.fl_btn_zhifubao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                        || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }
                // mActivity.closeActivity();
                break;

            case R.id.fl_btn_wechat:
                switchFragment(1);

                break;
            case R.id.fl_btn_zhifubao:
                switchFragment(2);
                break;
        }
    }

    private void switchFragment(int payType) {

        Bundle mBundle = new Bundle();
        mBundle.putString("paylog", mPayLog);
        mBundle.putString("money", money);
        mBundle.putInt("payType", payType);
        mBundle.putString("token", token);
        mBundle.putString("goodsno", goodsno);
        mBundle.putSerializable("mDataBean", mDataBean);

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getPayFragment(), mBundle);
            ((SaveActivity) mActivity).setStep(5, true, false);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getPayFragment(), mBundle);
            ((HalfSizeActivty) mActivity).setStep(5, true, false);
        }
  /*      mActivity.switchFragment(mActivity.getPayFragment(), mBundle);
        mActivity.setStep(5, true, false);*/

    }
}
