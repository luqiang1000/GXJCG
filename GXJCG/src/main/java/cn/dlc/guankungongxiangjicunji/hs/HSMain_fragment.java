package cn.dlc.guankungongxiangjicunji.hs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.guankungongxiangjicunji.main.widget.ImageTextView;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;

/**
 * Created by Administrator on 2018/7/24/024.
 */

public class HSMain_fragment extends BaseFragment {

    @BindView(R.id.iv_qr_Official)
    ImageView mIvQrOfficial;
    @BindView(R.id.tv_wx_title)
    TextView mTvWxTitle;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.iv_qr_wechat)
    ImageView mIvQrWechat;
    @BindView(R.id.iv_qr_ali)
    ImageView mIvQrAli;
    @BindView(R.id.tv_qr_code_2)
    TextView mTvQrCode2;
    @BindView(R.id.btn_save)
    ImageView mBtnSave;
    @BindView(R.id.btn_take_out)
    ImageView mBtnTakeOut;
    @BindView(R.id.btn_guide)
    ImageView mBtnGuide;
    @BindView(R.id.item_save)
    ImageTextView mItemSave;
    @BindView(R.id.item_change)
    ImageTextView mItemChange;
    @BindView(R.id.item_gift)
    ImageTextView mItemGift;
    private long exittime = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hs_main;
    }



    @OnClick({R.id.btn_save, R.id.btn_take_out, R.id.btn_guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
            case R.id.btn_take_out:
                break;
            case R.id.btn_guide:
                break;
        }
    }
}
