package cn.dlc.guankungongxiangjicunji.main.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dlc.guankungongxiangjicunji.R;

/**
 * Created by liuwenzhuo on 2018/6/22.
 */

public class MyTitleBar extends LinearLayout {

    public final ImageView mIvLogo;
    public final TextView mTvName;
    public final TextView mTvAction;
    public final StepView mStepView;

    private static final int LOGO = R.id.iv_logo;
    private static final int NAME = R.id.tv_name;
    private static final int ACTION = R.id.tv_action;
    private static final int STEPVIEW = R.id.view_step;

    public MyTitleBar(Context context) {
        this(context, null);
    }

    public MyTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.view_my_title_bar, this, true);

        mIvLogo = view.findViewById(LOGO);
        mTvName = view.findViewById(NAME);
        mTvAction = view.findViewById(ACTION);
        mStepView = view.findViewById(STEPVIEW);

        initView(mTypedArray);

        mTypedArray.recycle();
    }

    private void initView(TypedArray mTypedArray) {
        int logo = mTypedArray.getResourceId(R.styleable.MyTitleBar_logo, R.mipmap.icon_logo);
        mIvLogo.setBackgroundResource(logo);

        boolean logoVisib = mTypedArray.getBoolean(R.styleable.MyTitleBar_logo_visible, false);
        mIvLogo.setVisibility(logoVisib ? GONE : VISIBLE);

        boolean nameVisib = mTypedArray.getBoolean(R.styleable.MyTitleBar_name_visible, false);
        mTvName.setVisibility(nameVisib ? GONE : VISIBLE);

        String name = mTypedArray.getString(R.styleable.MyTitleBar_name);
        mTvName.setText(name);

        boolean isActionVisiable =
                mTypedArray.getBoolean(R.styleable.MyTitleBar_action_visiable, true);//默认显示
        if (isActionVisiable) {
            mTvAction.setVisibility(VISIBLE);
            String action = mTypedArray.getString(R.styleable.MyTitleBar_action);
            mTvAction.setText(action);
        } else {
            mTvAction.setVisibility(GONE);
        }

        boolean isStepViewVisiable =
                mTypedArray.getBoolean(R.styleable.MyTitleBar_step_view_visiable, true);//默认显示
        if (isStepViewVisiable) {
            mStepView.setVisibility(VISIBLE);
            int step = mTypedArray.getInteger(R.styleable.MyTitleBar_step, 0);
            mStepView.setCurrentStep(step);
        } else {
            mStepView.setVisibility(GONE);
        }
    }

    public void setLogo(int logoId) {
        mIvLogo.setBackgroundResource(logoId);
    }

    public void setName(@StringRes int name) {
        mTvName.setText(name);
    }

    public void setName(String name) {
        mTvName.setText(name);
    }

    public void setAction(@StringRes int action) {
        mTvAction.setText(action);
    }

    public void setAction(String action) {
        mTvAction.setText(action);
    }

    public void setActionVisiable(boolean isActionVisiable) {
        if (isActionVisiable) {
            mTvAction.setVisibility(VISIBLE);
        } else {
            mTvAction.setVisibility(GONE);
        }
    }

    public void setStep(int step) {
        mStepView.setCurrentStep(step);
    }

    public void setStepViewVisiable(boolean isStepViewVisiable) {
        if (isStepViewVisiable) {
            mStepView.setVisibility(VISIBLE);
        } else {
            mStepView.setVisibility(GONE);
        }
    }
}
