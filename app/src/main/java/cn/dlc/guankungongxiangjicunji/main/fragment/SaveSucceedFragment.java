package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class SaveSucceedFragment extends BaseFragment {

    @BindView(R.id.tv_content)
    TextView mTvContent;
   


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_save_succeed;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delayCloseActivity();
    }
    
    public void delayCloseActivity(){
        //设置3秒后关闭界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))
                        || "NOID".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    mActivity.finish();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }
            }
        },3*1000);
    }


   
}
