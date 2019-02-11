package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.ProductDescriptionActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.ArticleBean;

public class InstructionsFragment extends BaseFragment {
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.bt_back)
    Button mBtBack;

    private String mKeyname;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_instructions;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mKeyname = getArguments().getString("keyName");
        initData();
    }

    private void initData() {
        showWaitingDialog("请稍后...", false);
        MainHttp.get().article(mKeyname, new Bean01Callback<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((ProductDescriptionActivity) mActivity).startCountDownTime();
                    ((ProductDescriptionActivity) mActivity).setTopViewText(articleBean.data.title);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).startCountDownTime();
                    ((HalfSizeActivty) mActivity).setTopViewText(articleBean.data.title);
                }else{
                    ((ProductDescriptionActivity) mActivity).startCountDownTime();
                    ((ProductDescriptionActivity) mActivity).setTopViewText(articleBean.data.title);
                }
                // mProductDescriptionActivity.startCountDownTime();
                //mProductDescriptionActivity.setTopViewText(articleBean.data.title);
//                RichText.initCacheDir(mActivity);
//                RichText.fromHtml(articleBean.data.content).into(mTvContent);
                showWebView(articleBean.data.content);

            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showToast(message);
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    mActivity.finish();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
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


    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((ProductDescriptionActivity) mActivity).showFragment(0);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).showFragment(0);
        }else{
            ((ProductDescriptionActivity) mActivity).showFragment(0);
        }
        //  mProductDescriptionActivity.showFragment(Integer.valueOf(ProductDescriptionActivity.PRODUCT_DESCRIPTION_FRAGMENT));
    }


    /**
     * 显示WebView
     * @param content
     */
    private void showWebView(String content){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setDisplayZoomControls(false);// 设定缩放控件隐藏
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //根据屏幕宽度适配WebView内容
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            mWebView.setInitialScale(60);
        }


        mWebView.loadDataWithBaseURL(null, content, "text/html" , "utf-8", null);
    }
}
