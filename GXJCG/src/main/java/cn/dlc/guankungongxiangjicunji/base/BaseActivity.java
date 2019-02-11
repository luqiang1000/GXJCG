package cn.dlc.guankungongxiangjicunji.base;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.umeng.analytics.MobclickAgent;
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
    }
    
    @Override
    public void onResume() {
        super.onResume();
        String name = this.getClass().getName();
        LogUtils.e("onResume class name " + name);
        //统计时长
        MobclickAgent.onResume(this);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("onPause class name " + getClass().getName());
        MobclickAgent.onPause(this);
    }
}
