package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;

import com.dlc.vendingcabinets.Constant;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class SelectSpecFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.tv_small_name)
    TextView mTvSmallName;
    @BindView(R.id.tv_small_num)
    TextView mTvSmallNum;
    @BindView(R.id.tv_small_price)
    TextView mTvSmallPrice;
    @BindView(R.id.tv_small_description)
    TextView mTvSmallDescription;
    @BindView(R.id.ll_btn_small)
    LinearLayout mLlBtnSmall;
    @BindView(R.id.tv_small_spec)
    TextView mTvSmallSpec;
    @BindView(R.id.tv_middle_name)
    TextView mTvMiddleName;
    @BindView(R.id.tv_middle_num)
    TextView mTvMiddleNum;
    @BindView(R.id.tv_middle_price)
    TextView mTvMiddlePrice;
    @BindView(R.id.tv_middle_description)
    TextView mTvMiddleDescription;
    @BindView(R.id.ll_btn_middle)
    LinearLayout mLlBtnMiddle;
    @BindView(R.id.tv_middle_spec)
    TextView mTvMiddleSpec;
    @BindView(R.id.tv_big_name)
    TextView mTvBigName;
    @BindView(R.id.tv_big_num)
    TextView mTvBigNum;
    @BindView(R.id.tv_big_price)
    TextView mTvBigPrice;
    @BindView(R.id.tv_big_description)
    TextView mTvBigDescription;
    @BindView(R.id.ll_btn_big)
    LinearLayout mLlBtnBig;
    @BindView(R.id.tv_big_spec)
    TextView mTvBigSpec;

    private CabinetListBean mCabinetListBean;
    private String mType;

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_spec;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_select_spec_hs;
        }
        return R.layout.fragment_select_spec;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = PrefUtil.getDefault().getString("Type", "FULL");

        loadData();
    }

    //这里去进行网络获取数据
    public void loadData() {
        initView();
    }

    private void initView() {
        showWaitingDialog("请稍后...", false);
        MainHttp.get().cabinetList(Constant.MACNO, new Bean01Callback<CabinetListBean>() {
            @Override
            public void onSuccess(CabinetListBean cabinetListBean) {
                mCabinetListBean = cabinetListBean;
                for (CabinetListBean.DataBean datum : cabinetListBean.data) {
                    if ("FUll".equals(mType) || "NOID".equals(mType)) {
                        ((SaveActivity) mActivity).startCountDownTime();
                    } else if ("HALF".equals(mType)) {
                        ((HalfSizeActivty) mActivity).startCountDownTime();
                    }
                    String unit = "小时";
                    if ("min".equals(datum.charging_info.unit)) {
                        unit = "分钟";
                    } else if ("hour".equals(datum.charging_info.unit)) {
                        unit = "小时";
                    } else if ("day".equals(datum.charging_info.unit)) {
                        unit = "天";
                    }
                    if ("small".equals(datum.charging_info.keyname)) {
                        //设置小柜
                        mTvSmallName.setText(datum.charging_info.title);
                        mTvSmallNum.setText(String.valueOf(datum.charging_restnum));
                        mTvSmallPrice.setText(datum.charging_info.starting_price);
                        mTvSmallDescription.setText("(" + datum.charging_info.starting_time + unit + "后," + datum.charging_info.normal_price + "元/" + unit + ")");
                        mTvSmallSpec.setText(datum.charging_info.memo);
                    } else if ("mid".equals(datum.charging_info.keyname)) {
                        //设置中柜
                        mTvMiddleName.setText(datum.charging_info.title);
                        mTvMiddleNum.setText(String.valueOf(datum.charging_restnum));
                        mTvMiddlePrice.setText(datum.charging_info.starting_price);
                        mTvMiddleDescription.setText("(" + datum.charging_info.starting_time + unit + "后," + datum.charging_info.normal_price + "元/" + unit +")");
                        mTvMiddleSpec.setText(datum.charging_info.memo);
                    } else if ("big".equals(datum.charging_info.keyname)) {
                        //设置大柜
                        mTvBigName.setText(datum.charging_info.title);
                        mTvBigNum.setText(String.valueOf(datum.charging_restnum));
                        mTvBigPrice.setText(datum.charging_info.starting_price);
                        mTvBigDescription.setText("(" + datum.charging_info.starting_time + unit + "后," + datum.charging_info.normal_price + "元/" + unit + ")");
                        mTvBigSpec.setText(datum.charging_info.memo);
                    }
                }
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showToast(message);
                if ("FUll".equals(mType) || "NOID".equals(mType)) {
                    mActivity.finish();
                } else if ("HALF".equals(mType)) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissWaitingDialog();
            }
        });


    }


    @OnClick({R.id.btn_back, R.id.ll_btn_small, R.id.ll_btn_middle, R.id.ll_btn_big})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                //  mActivity.switchFragment(mActivity.getSafeGuideFragment(),null);
                // mActivity.setStep(1, false, false);
                if ("FUll".equals(mType) || "NOID".equals(mType)) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(mType)) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }
                break;
            case R.id.ll_btn_small:
                CabinetListBean.DataBean mDataBean = null;
                for (int i = 0; i < mCabinetListBean.data.size(); i++) {
                    if (mCabinetListBean.data.get(i).charging_info.keyname.equals("small")) {
                        if(mCabinetListBean.data.get(i).macno_status == 1){
                            if(mCabinetListBean.data.get(i).charging_restnum > 0){
                                mDataBean = mCabinetListBean.data.get(i);
                                switchFragment(mDataBean);
                            }else{
                                showOneToast("暂无柜子可用!");
                            }
                        }else{
                            showOneToast("设备维护中");
                        }

                    }
                }

                break;
            case R.id.ll_btn_middle:
                CabinetListBean.DataBean mDataBean1 = null;
                for (int i = 0; i < mCabinetListBean.data.size(); i++) {
                    if (mCabinetListBean.data.get(i).charging_info.keyname.equals("mid")) {
                        if(mCabinetListBean.data.get(i).macno_status == 1){
                            if(mCabinetListBean.data.get(i).charging_restnum > 0){
                                mDataBean1 = mCabinetListBean.data.get(i);
                                switchFragment(mDataBean1);
                            }else {
                                showOneToast("暂无柜子可用!");
                            }
                        }else{
                            showOneToast("设备维护中");
                        }
                    }
                }

                break;
            case R.id.ll_btn_big:
                CabinetListBean.DataBean mDataBean2 = null;
                for (int i = 0; i < mCabinetListBean.data.size(); i++) {
                    if (mCabinetListBean.data.get(i).charging_info.keyname.equals("big")) {
                        if(mCabinetListBean.data.get(i).macno_status == 1){
                            if(mCabinetListBean.data.get(i).charging_restnum > 0){
                                mDataBean2 = mCabinetListBean.data.get(i);
                                switchFragment(mDataBean2);
                            }else {
                                showOneToast("暂无柜子可用!");
                            }
                        }else{
                            showOneToast("设备维护中");
                        }
                    }
                }

                break;
        }
    }

    private void switchFragment(CabinetListBean.DataBean mDataBean) {

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("mDataBean", mDataBean);

        if ("FUll".equals(mType) || "NOID".equals(mType)) {
            ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectUseWayFragment(), mBundle);
            ((SaveActivity) mActivity).setStep(3, false, false);
        } else if ("HALF".equals(mType)) {
            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getSelectUseWayFragment(), mBundle);
            ((HalfSizeActivty) mActivity).setStep(3, false, false);
        }

    }


}
