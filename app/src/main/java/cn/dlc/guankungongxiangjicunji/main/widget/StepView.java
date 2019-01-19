package cn.dlc.guankungongxiangjicunji.main.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dlc.guankungongxiangjicunji.R;

/**
 * Created by liuwenzhuo on 2018/6/22.
 */

public class StepView extends LinearLayout {

    public final TextView mTvStepOne;
    public final TextView mTvStepTwo;
    public final TextView mTvStepThree;
    public final TextView mTvStepFour;
    public final TextView mTvStepFive;
    public final TextView mTvStepSix;

    private static final int STEP_ONE = R.id.tv_step_one;
    private static final int STEP_TWO = R.id.tv_step_two;
    private static final int STEP_THREE = R.id.tv_step_three;
    private static final int STEP_FOUR = R.id.tv_step_four;
    private static final int STEP_FIVE = R.id.tv_step_five;
    private static final int STEP_SIX = R.id.tv_step_six;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_step, this, true);

        mTvStepOne = view.findViewById(STEP_ONE);
        mTvStepTwo = view.findViewById(STEP_TWO);
        mTvStepThree = view.findViewById(STEP_THREE);
        mTvStepFour = view.findViewById(STEP_FOUR);
        mTvStepFive = view.findViewById(STEP_FIVE);
        mTvStepSix = view.findViewById(STEP_SIX);
    }

    public void setCurrentStep(int currentStep) {
        switch (currentStep) {
            case 1:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(false);
                mTvStepThree.setSelected(false);
                mTvStepFour.setSelected(false);
                mTvStepFive.setSelected(false);
                mTvStepSix.setSelected(false);
                break;
            case 2:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(true);
                mTvStepThree.setSelected(false);
                mTvStepFour.setSelected(false);
                mTvStepFive.setSelected(false);
                mTvStepSix.setSelected(false);
                break;
            case 3:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(true);
                mTvStepThree.setSelected(true);
                mTvStepFour.setSelected(false);
                mTvStepFive.setSelected(false);
                mTvStepSix.setSelected(false);
                break;
            case 4:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(true);
                mTvStepThree.setSelected(true);
                mTvStepFour.setSelected(true);
                mTvStepFive.setSelected(false);
                mTvStepSix.setSelected(false);
                break;
            case 5:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(true);
                mTvStepThree.setSelected(true);
                mTvStepFour.setSelected(true);
                mTvStepFive.setSelected(true);
                mTvStepSix.setSelected(false);
                break;
            case 6:
                mTvStepOne.setSelected(true);
                mTvStepTwo.setSelected(true);
                mTvStepThree.setSelected(true);
                mTvStepFour.setSelected(true);
                mTvStepFive.setSelected(true);
                mTvStepSix.setSelected(true);
                break;
            default:
                mTvStepOne.setSelected(false);
                mTvStepTwo.setSelected(false);
                mTvStepThree.setSelected(false);
                mTvStepFour.setSelected(false);
                mTvStepFive.setSelected(false);
                mTvStepSix.setSelected(false);
                break;
        }
    }
}
