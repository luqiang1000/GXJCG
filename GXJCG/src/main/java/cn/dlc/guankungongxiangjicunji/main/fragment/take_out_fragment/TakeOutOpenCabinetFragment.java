package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.HomeService;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.activity.MainActivity;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutOpenCabinetFragment extends BaseFragment {

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.ll_tip)
    LinearLayout mLlTip;
    private String goodsno;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_cabinet;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goodsno = getArguments().getString("goodsno");
        mTvContent.setText(String.format(ResUtil.getString(R.string.quwujihaomenyidakai_),goodsno));
        String resName = "take" + Integer.parseInt(goodsno);

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
//            ((TakeOutActivity) mActivity).playMedia(resName);
//            HomeService.playMedia(resName);
            ((TakeOutActivity) mActivity).setStep(6, false, false);
            MainActivity.playMedia(resName);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
//            ((HalfSizeActivty) mActivity).playMedia(resName);
//            HomeService.playMedia(resName);
            ((HalfSizeActivty) mActivity).setStep(6, false, false);
            MainActivity.playMedia(resName);
        }else{
//            ((TakeOutActivity) mActivity).playMedia(resName);
//            HomeService.playMedia(resName);
            ((TakeOutActivity) mActivity).setStep(6, false, false);
            MainActivity.playMedia(resName);
        }

        delayCloseActivity();
    }
    public void delayCloseActivity(){
        //设置3秒后关闭界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutSaveSucceedFragment(), null);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutSaveSucceedFragment(), null);
                }else{
                    ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutSaveSucceedFragment(), null);
                }
            }
        },5*1000);
    }

}
