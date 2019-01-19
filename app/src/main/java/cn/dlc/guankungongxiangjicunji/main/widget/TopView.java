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

public class TopView extends LinearLayout {

    public final ImageView mIvLeftIcon;
    public final TextView mTvTitle;
    public final TextView mTvCountDown;

    private static final int LEFT_ICON = R.id.iv_left_icon;
    private static final int TITLE = R.id.tv_title;
    private static final int COUNTDOWN = R.id.tv_count_down;

    public TopView(Context context) {
        this(context, null);
    }

    public TopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);

        View view = LayoutInflater.from(context).inflate(R.layout.view_top, this, true);

        mIvLeftIcon = view.findViewById(LEFT_ICON);
        mTvTitle = view.findViewById(TITLE);
        mTvCountDown = view.findViewById(COUNTDOWN);

        initView(mTypedArray);

        mTypedArray.recycle();
    }

    private void initView(TypedArray mTypedArray) {
        int leftIcon = mTypedArray.getResourceId(R.styleable.TopView_left_icon, R.mipmap.icon_1);
        mIvLeftIcon.setBackgroundResource(leftIcon);

        String title = mTypedArray.getString(R.styleable.TopView_title);
        mTvTitle.setText(title);

        int countdown = mTypedArray.getInteger(R.styleable.TopView_countdown, 60);
        mTvCountDown.setText(String.valueOf(countdown));
    }

    public void setLeftIcon( int leftIconId) {
        mIvLeftIcon.setBackgroundResource(leftIconId);
    }

    public void setTitle(@StringRes int title) {
        mTvTitle.setText(title);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setCountdown(int countdown) {
        mTvCountDown.setText(String.valueOf(countdown));
    }

    public void setEmpty(String text) {
        mTvCountDown.setText(text);
    }
}
