package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.AlipayBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.PayBean;
import cn.dlc.guankungongxiangjicunji.main.bean.WxPayBean;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class PayFragment extends BaseFragment {

    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_spec)
    TextView mTvSpec;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_code)
    ImageView mIvCode;
    @BindView(R.id.tv_pay_tip)
    TextView mTvPayTip;
    @BindView(R.id.btn_back)
    Button mBtnBack;



    private int mPayType = 1;//1 微信支付 2 支付宝支付
    private String payLog = "";
    private String money;
    private String token;
    private String goodsno;
    private CabinetListBean.DataBean mDataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_way;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPayType = getArguments().getInt("payType");
        payLog = getArguments().getString("paylog");
        money = getArguments().getString("money");
        token = getArguments().getString("token");
        goodsno = getArguments().getString("goodsno");
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");
        setView();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPayResut(BaseBean baseBean) {
        Bundle mBundle = new Bundle();
        mBundle.putString("goodsno", goodsno);
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getOpenCabinetFragment(),mBundle);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getOpenCabinetFragment(),mBundle);
        }
      //  mActivity.switchFragment(mActivity.getOpenCabinetFragment(), mBundle);
    }

    public void setView() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }
        //mActivity.startCountDownTime();
        mTvMoney.setText(String.valueOf(money));
        mTvSpec.setText(mDataBean.charging_info.title + " (" + mDataBean.charging_info.memo + ")");
        mTvPrice.setText("超出起步时长后" + mDataBean.charging_info.normal_price + "元/" + mDataBean.charging_info.unit + ")");
        mTvTime.setText(mDataBean.charging_info.starting_time + mDataBean.charging_info.unit);

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
                    if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                            || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        mActivity.finish();
                    } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        ((HalfSizeActivty) mActivity).closeActivity();
                    }
                }
            });
        } else {
            mTvPayTip.setText("支付宝扫二维码支付");
            MainHttp.get().getAliQRCode(money, payLog, token, new Bean01Callback<AlipayBean>() {
                @Override
                public void onSuccess(AlipayBean alipayBean) {
                    dismissWaitingDialog();
                    Glide.with(mActivity).load(getQrBitmap(alipayBean.getData(), R.mipmap.zhifubao)).into(mIvCode);

                }

                @Override
                public void onFailure(String message, Throwable tr) {
                    dismissWaitingDialog();
                    showOneToast(message);
                    if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                            || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        mActivity.finish();
                    } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                        ((HalfSizeActivty) mActivity).closeActivity();
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
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    ((SaveActivity) mActivity).closeActivity();
                }
               // mActivity.closeActivity();
                break;
        }
    }


    private Bitmap getQrBitmap(String content, int resId) {
        Bitmap centerBitmap = BitmapFactory.decodeResource(getResources(), resId);
        // 生成二维码
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(mActivity, 150), Color
                .parseColor("#000000"), centerBitmap);
        return bitmap;
    }
}
