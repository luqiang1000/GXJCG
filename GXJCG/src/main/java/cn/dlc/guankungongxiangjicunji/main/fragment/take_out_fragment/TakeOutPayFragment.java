package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseBean;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.AlipayBean;
import cn.dlc.guankungongxiangjicunji.main.bean.WxPayBean;
import okhttp3.Request;

import com.bumptech.glide.Glide;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutPayFragment extends BaseFragment {

    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_spec)
    TextView mTvSpec;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_time_title)
    TextView mTvTimeTitle;
    @BindView(R.id.iv_code)
    ImageView mIvCode;
    @BindView(R.id.tv_pay_tip)
    TextView mTvPayTip;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.tv_use_time)
    TextView mTvUseTime;
    @BindView(R.id.standard_layout)
    LinearLayout standardLayout;


    private int mPayType = 1;//1 微信支付 2 支付宝支付
    private String payLog = "";
    private String money;
    private String token;
    private int overtime;
    private int usedtime;
    private String goodsno;
    private String unit;

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_pay_way_take_out;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_pay_way_take_out_hs;
        }
        return R.layout.fragment_pay_way_take_out;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mPayType = getArguments().getInt("payType");
        payLog = getArguments().getString("paylog");
        money = getArguments().getString("money");
        token = getArguments().getString("token");
        overtime = getArguments().getInt("overtime");
        usedtime = getArguments().getInt("usedtime");
        goodsno = getArguments().getString("goodsno");
        unit = getArguments().getString("unit");
        setView();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPayResut(BaseBean baseBean) {
        Bundle mBundle = new Bundle();
        mBundle.putString("goodsno", goodsno);
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutOpenCabinetFragment(), mBundle);
        }else{
            ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle);
        }
        // mActivity.switchFragment(mActivity.getTakeOutOpenCabinetFragment(), mBundle);
    }

    public void setView() {

        standardLayout.setVisibility(View.GONE);

        mTvMoney.setText(String.valueOf(money));
        mTvTimeTitle.setText("超时时长:");


        if(unit.equals("day")){
            mTvTime.setText(overtime + "天");
            mTvUseTime.setText(usedtime + "天");
        }else if(unit.equals("hour")){
            mTvTime.setText(overtime + "小时");
            mTvUseTime.setText(usedtime + "小时");
        }else{
            mTvTime.setText(overtime + "分钟");
            mTvUseTime.setText(usedtime + "分钟");
        }


        showWaitingDialog("获取二维码中", true);
        if (mPayType == 1) {
            mTvPayTip.setText("微信扫二维码支付");
            MainHttp.get().getWxPayQRCode(money, payLog, token, new Bean01Callback<WxPayBean>() {
                @Override
                public void onSuccess(WxPayBean wxPayBean) {
                    dismissWaitingDialog();

                    Glide.with(mActivity).load(getQrBitmap(wxPayBean.data.code_url, R.mipmap.weixinzhifu)).into(mIvCode);

                }

                @Override
                public void onFailure(String message, Throwable tr) {
                    dismissWaitingDialog();
                    showOneToast(message);
                    if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        mActivity.finish();
                    } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        ((HalfSizeActivty) mActivity).closeActivity();
                    }else{
                        mActivity.finish();
                    }
                }
            });
        } else {
            mTvPayTip.setText("支付宝扫二维码支付");
            MainHttp.get().getAliQRCode(money, payLog, token, new Bean01Callback<AlipayBean>() {
                @Override
                public void onSuccess(AlipayBean alipayBean) {
                    dismissWaitingDialog();
                    Glide.with(mActivity).load(getQrBitmap(alipayBean.getData(), R.mipmap.zhifubaozhifu)).into(mIvCode);

                }

                @Override
                public void onFailure(String message, Throwable tr) {
                    dismissWaitingDialog();
                    showOneToast(message);
                    if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        mActivity.finish();
                    } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        ((HalfSizeActivty) mActivity).closeActivity();
                    }else{
                        mActivity.finish();
                    }
                }
            });
        }

    }

    @OnClick({R.id.btn_back,})
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
                // mActivity.finish();
                break;
        }
    }

    private Bitmap getQrBitmap(String content, int resId) {
        Bitmap centerBitmap = BitmapFactory.decodeResource(getResources(), resId);
        // 生成二维码
        Bitmap bitmap =
                QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(getContext(), 100),
                        Color.parseColor("#000000"), centerBitmap);
        return bitmap;
    }

}
