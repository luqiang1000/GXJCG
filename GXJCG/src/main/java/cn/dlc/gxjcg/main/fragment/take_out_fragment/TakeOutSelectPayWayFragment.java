package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.GetGoodsBean;
import cn.dlc.guankungongxiangjicunji.main.bean.PayBean;
import cn.dlc.guankungongxiangjicunji.main.widget.GetQrCodeDialog;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutSelectPayWayFragment extends BaseFragment {

    @BindView(R.id.fl_btn_wechat)
    FrameLayout mFlBtnWechat;
    @BindView(R.id.fl_btn_zhifubao)
    FrameLayout mFlBtnZhifubao;
    @BindView(R.id.btn_back)
    Button mBtnBack;

    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_money)
    TextView mTvMoney;

    private String paylog = "";
    private String money = "";
    private int usedtime;//已使用时间
    private int overtime;//超时时间
    private String token;
    private String goodsno;//柜号门
    private String unit;//时间单位

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_pay_way_take_out;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_pay_way_take_out_hs;
        }
        return R.layout.fragment_select_pay_way_take_out;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        paylog = getArguments().getString("paylog");
        money = getArguments().getString("money");
        usedtime = getArguments().getInt("usedtime");
        overtime = getArguments().getInt("overtime");
        token = getArguments().getString("token");
        goodsno = getArguments().getString("goodsno");
        unit = getArguments().getString("unit");
        if(unit.equals("day")){
            mTvTotalTime.setText(String.valueOf(usedtime) + "天");
            mTvTime.setText(String.valueOf(overtime) + "天");
        }else if(unit.equals("hour")){
            mTvTotalTime.setText(String.valueOf(usedtime) + "小时");
            mTvTime.setText(String.valueOf(overtime) + "小时");
        }else{
            mTvTotalTime.setText(String.valueOf(usedtime) + "分钟");
            mTvTime.setText(String.valueOf(overtime) + "分钟");
        }
        mTvMoney.setText(money);
    }


    @OnClick({R.id.btn_back, R.id.fl_btn_wechat, R.id.fl_btn_zhifubao})
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
        mBundle.putString("paylog", paylog);
        mBundle.putString("money", money);
        mBundle.putInt("payType", payType);
        mBundle.putString("token", token);
        mBundle.putInt("usedtime", usedtime);
        mBundle.putInt("overtime", overtime);
        mBundle.putString("goodsno",goodsno);
        mBundle.putString("unit",unit);

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((TakeOutActivity) mActivity).setStep(5, true, false);
            ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutPayFragment(), mBundle);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).setTakeoutStep(5, true, false);
            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutPayFragment(), mBundle);
        }else{
            ((TakeOutActivity) mActivity).setStep(5, true, false);
            ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutPayFragment(), mBundle);
        }

    }


}
