package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.ProductDescriptionActivity;

/**
 * Created by liuwenzhuo on 2018/6/22.
 */

public class ProductDescriptionFragment extends BaseFragment {

    @BindView(R.id.iv_self)
    ImageView mIvSelf;
    @BindView(R.id.iv_friend)
    ImageView mIvFriend;
    @BindView(R.id.iv_gift)
    ImageView mIvGift;
    @BindView(R.id.bt_back)
    Button mBtBack;

    // private ProductDescriptionActivity mProductDescriptionActivity;

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_product_description;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_product_description_hs;
        }
        return R.layout.fragment_product_description;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mProductDescriptionActivity = (ProductDescriptionActivity) getActivity();
    }

    @OnClick({R.id.iv_self, R.id.iv_friend, R.id.iv_gift, R.id.bt_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_self:
                showSelfFragment();
                break;
            case R.id.iv_friend:
                showFriendFragment();
                break;
            case R.id.iv_gift:
                showGiftFragment();
                break;
            case R.id.bt_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    mActivity.finish();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    mActivity.finish();
                }
                //mActivity.finish();
                break;
        }
    }

    private void showSelfFragment() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.SELF_FRAGMENT));
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.SELF_FRAGMENT));
        }else{
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.SELF_FRAGMENT));
        }
        // mProductDescriptionActivity.showFragment(Integer.valueOf(ProductDescriptionActivity.SELF_FRAGMENT));
    }

    private void showFriendFragment() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.FRIEND_FRAGMENT));
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.FRIEND_FRAGMENT));
        }else{
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.FRIEND_FRAGMENT));
        }
        // mProductDescriptionActivity.showFragment(Integer.valueOf(ProductDescriptionActivity.FRIEND_FRAGMENT));
    }

    private void showGiftFragment() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.GIFT_FRAGMENT));
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.GIFT_FRAGMENT));
        }else{
            ((ProductDescriptionActivity) mActivity).showFragment(Integer.valueOf(ProductDescriptionActivity.GIFT_FRAGMENT));
        }
        // mProductDescriptionActivity.showFragment(Integer.valueOf(ProductDescriptionActivity.GIFT_FRAGMENT));
    }
}
