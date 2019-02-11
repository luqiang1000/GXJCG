package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.main.activity.ProductDescriptionActivity;

/**
 * Created by liuwenzhuo on 2018/6/22.
 */

public class WebViewFragment extends BaseFragment {
    
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.bt_back)
    Button mBtBack;

    private static final String FRAGMENT_TYPE = "fragment_type";

    private String mType;
    private ProductDescriptionActivity mProductDescriptionActivity;

    public static WebViewFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TYPE, type);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString(FRAGMENT_TYPE);
        mProductDescriptionActivity = (ProductDescriptionActivity) getActivity();
    }
    
    @OnClick({ R.id.bt_back })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                mProductDescriptionActivity.showFragment(
                    Integer.valueOf(ProductDescriptionActivity.PRODUCT_DESCRIPTION_FRAGMENT));
                break;
        }
    }
}
