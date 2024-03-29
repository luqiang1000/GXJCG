package cn.dlc.guankungongxiangjicunji.main.widget;

import android.os.CountDownTimer;

/**
 * 倒计时工具类
 * Created by wuyufeng    on  2018/6/21 0021.
 * interface by
 */

public class CountDownTimerUtils {

    /**
     * 倒计时结束的回调接口
     */
    public interface FinishDelegate {
        void onFinish();
    }

    /**
     * 定期回调的接口
     */
    public interface TickDelegate {
        void onTick(long pMillisUntilFinished);
    }

    /**
     * 倒计时和定期会调接口
     */
    public interface TickAndFinishDelegate {
        void onFinish();
        void onTick(long pMillisUntilFinished);
    }

    private  long ONE_SECOND = 1000;
    /**
     * 总倒计时时间
     */
    private long mMillisInFuture = 0;
    /**
     * 定期回调的时间 必须大于0 否则会出现ANR
     */
    private long mCountDownInterval;
    /**
     * 倒计时结束的回调
     */
    private FinishDelegate mFinishDelegate;
    /**
     * 定期回调
     */
    private TickDelegate mTickDelegate;

    /**
     * 定期回调和倒计时结束
     */
    private TickAndFinishDelegate mTickAndFinishDelegate;
    
    private MyCountDownTimer mCountDownTimer;

    /**
     * 获取 CountDownTimerUtils
     *
     * @return CountDownTimerUtils
     */
    public static CountDownTimerUtils getCountDownTimer() {
        return new CountDownTimerUtils();
    }

    /**
     * 设置定期回调的时间 调用{@link #setTickDelegate(TickDelegate)}
     *
     * @param pCountDownInterval 定期回调的时间 必须大于0
     * @return CountDownTimerUtils
     */
    public CountDownTimerUtils setCountDownInterval(long pCountDownInterval) {
        this.mCountDownInterval = pCountDownInterval;
        return this;
    }

    /**
     * 设置倒计时结束的回调
     *
     * @param pFinishDelegate 倒计时结束的回调接口
     * @return CountDownTimerUtils
     */
    public CountDownTimerUtils setFinishDelegate(FinishDelegate pFinishDelegate) {
        this.mFinishDelegate = pFinishDelegate;
        return this;
    }

    /**
     * 设置周期和结束的回调
     *
     * @return CountDownTimerUtils
     */
    public CountDownTimerUtils setTickAndFinishDelegate(TickAndFinishDelegate tickAndFinishDelegate) {
        this.mTickAndFinishDelegate = tickAndFinishDelegate;
        return this;
    }

    /**
     * 设置总倒计时时间
     *
     * @param pMillisInFuture 总倒计时时间
     * @return CountDownTimerUtils
     */
    public CountDownTimerUtils setMillisInFuture(long pMillisInFuture) {
        this.mMillisInFuture = pMillisInFuture;
        return this;
    }

    /**
     * 设置定期回调
     *
     * @param pTickDelegate 定期回调接口
     * @return CountDownTimerUtils
     */
    public CountDownTimerUtils setTickDelegate(TickDelegate pTickDelegate) {
        this.mTickDelegate = pTickDelegate;
        return this;
    }

    public void create() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        if (mCountDownInterval <= 0) {
            mCountDownInterval = mMillisInFuture + ONE_SECOND;
        }
        mCountDownTimer = new MyCountDownTimer(mMillisInFuture, mCountDownInterval);
        mCountDownTimer.setTickDelegate(mTickDelegate);
        mCountDownTimer.setFinishDelegate(mFinishDelegate);
        mCountDownTimer.setTickAndFinishDelegate(mTickAndFinishDelegate);
    }

    /**
     * 开始倒计时
     */
    public void start() {
        if (mCountDownTimer == null) {
            create();
        }
        mCountDownTimer.cancel();
        mCountDownTimer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private  class MyCountDownTimer extends CountDownTimer {
        private FinishDelegate mFinishDelegate;
        private TickDelegate mTickDelegate;
        private TickAndFinishDelegate mTickAndFinishDelegate;

        /**
         * @param millisInFuture The number of millis in the future from the call
         * to {@link #start()} until the countdown is done and {@link #onFinish()}
         * is called.
         * @param countDownInterval The interval along the way to receive
         * {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (mTickDelegate != null) {
                mTickDelegate.onTick(millisUntilFinished);
            }
            if (mTickAndFinishDelegate != null) {
                mTickAndFinishDelegate.onTick(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if (mFinishDelegate != null) {
                mFinishDelegate.onFinish();
            }
            if (mTickAndFinishDelegate != null) {
                mTickAndFinishDelegate.onFinish();
            }
        }

        void setFinishDelegate(FinishDelegate pFinishDelegate) {
            this.mFinishDelegate = pFinishDelegate;
        }

        void setTickDelegate(TickDelegate pTickDelegate) {
            this.mTickDelegate = pTickDelegate;
        }

        void setTickAndFinishDelegate(TickAndFinishDelegate mTickAndFinishDelegate) {
            this.mTickAndFinishDelegate = mTickAndFinishDelegate;
        }
    }

 
}

