package cn.dlc.guankungongxiangjicunji.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.dlc.commonlibrary.ui.base.BaseCommonFragment;


public abstract class BaseFragment extends BaseCommonFragment{

    //处理每个Fragment都需要执行的逻辑
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
}

