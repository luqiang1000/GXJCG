package cn.dlc.guankungongxiangjicunji.base;

import android.os.Bundle;

import com.umeng.message.PushAgent;

import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

public abstract class BaseActivity extends BaseCommonActivity {

    //需要处理全屏逻辑之类的操作,在子类重写beforeSetContentView方法做处理
    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        setTranslucentStatus(); // 沉浸状态栏
        
    }

    //需要在每个Activity处理逻辑,直接在onCreate方法处理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

}
