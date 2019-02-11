package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.app.Activity;
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
import cn.dlc.guankungongxiangjicunji.HomeService;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.MainActivity;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.ArticleBean;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class SafeGuideFragment extends BaseFragment {


    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.tv_content)
    WebView mWebView;


    private Activity mActivity;
    private String mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_safe_guide;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = PrefUtil.getDefault().getString("Type", "FULL");
        mActivity = getActivity();
        initData();
        if ("FUll".equals(mType) || "NOID".equals(mType)) {
//            ((SaveActivity) mActivity).playMedia("welcome");
//            HomeService.playMedia("welcome");
            MainActivity.playMedia("welcome");
        } else if ("HALF".equals(mType)) {
//            ((HalfSizeActivty) mActivity).playMedia("welcome");
//            HomeService.playMedia("welcome");
            MainActivity.playMedia("welcome");
        }

    }


    private void initData() {
        showWaitingDialog("请稍后...", false);
        MainHttp.get().article("safe_info", new Bean01Callback<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                if ("FUll".equals(mType) || "NOID".equals(mType)) {
                    ((SaveActivity) mActivity).startCountDownTime();
                    ((SaveActivity) mActivity).setTopViewText(articleBean.data.title);
                } else if ("HALF".equals(mType)) {
                    ((HalfSizeActivty) mActivity).startCountDownTime();
                    ((HalfSizeActivty) mActivity).setTopViewText(articleBean.data.title);
                }
//                RichText.initCacheDir(mActivity);
//                RichText.fromHtml(articleBean.data.content).into(mTvContent);
                showWebView(articleBean.data.content);
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showOneToast(message);
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


    @OnClick({R.id.btn_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(mType) || "NOID".equals(mType)) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(mType)) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }
                break;
            case R.id.btn_next:
                if ("FUll".equals(mType) || "NOID".equals(mType)) {
                    ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectSpecFragment(), null);
                    ((SaveActivity) mActivity).setStep(2, false, false);
                } else if ("HALF".equals(mType)) {
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getSelectSpecFragment(), null);
                    ((HalfSizeActivty) mActivity).setStep(2, false, false);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            mWebView.setInitialScale(50);
        }else{
            mWebView.setInitialScale(60);
        }


        mWebView.loadDataWithBaseURL(null, content, "text/html" , "utf-8", null);
    }
}