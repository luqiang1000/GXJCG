package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.BaseBean;
import cn.dlc.guankungongxiangjicunji.main.bean.GetGoodsBean;
import cn.dlc.guankungongxiangjicunji.main.bean.UserInfo;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomKeyboardView;
import cn.dlc.guankungongxiangjicunji.main.widget.MyCountDownTimer;

import com.dlc.vendingcabinets.Constant;

import java.lang.reflect.Method;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutPhoneWayFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_get_password)
    TextView mTvGetPassword;
    @BindView(R.id.customKeyboardView)
    CustomKeyboardView mCustomKeyboardView;


    private boolean phoneFocus = false;
    private boolean passwordFocus = false;
    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_take_out_phone_way;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_take_out_phone_way_hs;
        }
        return R.layout.fragment_take_out_phone_way;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        initKeyboardView();
    }

    private void initKeyboardView() {

        mCustomKeyboardView.setOnClickKeyboardItemListener(new CustomKeyboardView.OnClickKeyboardItemListener() {
            @Override
            public void onClickNumber(String num) {
                setPhoneOrPassword(num);
            }

            @Override
            public void onClickDelete() {
                deletePhoneOrPassword();
            }

            @Override
            public void onClickConfirm() {//点击确定按钮

                //如果没有超过起步寄存时间
                //mActivity.setStep(6,false,false);
                //mActivity.getTakeOutOpenCabinetFragment().setView("19");
                //mActivity.switchFragment(mActivity.getTakeOutOpenCabinetFragment());

                //如果超过起步寄存时间


//                mActivity.setStep(4, false, false);
//                mActivity.getTakeOutSelectPayWayFragment().setView();
//                mActivity.getTakeOutSelectPayWayFragment().setShowType(1);
//                mActivity.switchFragment(mActivity.getTakeOutSelectPayWayFragment());

                checkCode();

            }
        });
    }


    private void checkCode() {
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showOneToast("请输入正确手机号码");
            return;
        }
        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showOneToast("请输入密码");
            return;
        }
        showWaitingDialog("请稍后...", false);
        MainHttp.get().getGoodsCheckCode(phone, "", "", password, "0", new Bean01Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                String token = userInfo.data.userinfo.token;
                MainHttp.get().getGoods(Constant.MACNO, token, new Bean01Callback<GetGoodsBean>() {
                    @Override
                    public void onSuccess(GetGoodsBean getGoodsBean) {
                        if ("finish".equals(getGoodsBean.data.status)) {//开门成功
                            Bundle mBundle1 = new Bundle();
                            mBundle1.putString("goodsno", getGoodsBean.data.goodsno);

                            if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((TakeOutActivity) mActivity).setStep(6, false, false);
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                            } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((HalfSizeActivty) mActivity).setStep(6, false, false);
                                ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                            }else{
                                ((TakeOutActivity) mActivity).setStep(6, false, false);
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                            }

                        } else if ("fail".equals(getGoodsBean.data.status)) {//开门失败
                            showToast(getGoodsBean.msg);
                        } else if ("pay".equals(getGoodsBean.data.status)) {//超时支付
                            Bundle mBundle = new Bundle();
                            mBundle.putString("paylog", getGoodsBean.data.paylog);
                            mBundle.putString("money", getGoodsBean.data.money);
                            mBundle.putInt("usedtime", getGoodsBean.data.usedtime);
                            mBundle.putInt("overtime", getGoodsBean.data.overtime);
                            mBundle.putString("token", token);
                            mBundle.putString("unit",getGoodsBean.data.unit);
                            mBundle.putString("goodsno", getGoodsBean.data.goodsno);
                            mBundle.putString("unit",getGoodsBean.data.unit);
                            if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((TakeOutActivity) mActivity).setStep(5, false, false);
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutSelectPayWayFragment(), mBundle);
                            } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                ((HalfSizeActivty) mActivity).setTakeoutStep(5, false, false);
                                ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutSelectPayWayFragment(), mBundle);
                            }else{
                                ((TakeOutActivity) mActivity).setStep(5, false, false);
                                ((TakeOutActivity) mActivity).switchFragment(((TakeOutActivity) mActivity).getTakeOutSelectPayWayFragment(), mBundle);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {
                        showOneToast(message);
                    }
                });

            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showOneToast(message);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissWaitingDialog();
            }
        });
    }


    private void deletePhoneOrPassword() {
        if (phoneFocus) {
            delete(mEtPhone);
        }

        if (passwordFocus) {
            delete(mEtPassword);
        }
    }

    private void delete(EditText editText) {
        String content = editText.getText().toString();
        if (content.length() > 0) {
            mStringBuilder.delete(content.length() - 1, content.length());
            editText.setText(mStringBuilder.toString());
            editText.setSelection(mStringBuilder.toString().length());
        } else {
            mStringBuilder = new StringBuilder();
        }
    }

    private void setPhoneOrPassword(String num) {
        if (phoneFocus) {
            if (mEtPhone.getText().length() < 11) {
                mStringBuilder.append(num);
                String content = mStringBuilder.toString();
                mEtPhone.setText(content);
                mEtPhone.setSelection(content.length());
            }
        }

        if (passwordFocus) {
            mStringBuilder.append(num);
            String content = mStringBuilder.toString();
            mEtPassword.setText(content);
            mEtPassword.setSelection(content.length());
        }
    }

    private void setListener() {
        //手机号输入框焦点
        disableShowSoftInput(mEtPhone);
        mEtPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                phoneFocus = true;
                mStringBuilder = new StringBuilder();
                String s = mEtPhone.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    mStringBuilder.append(s);
                }
            } else {
                phoneFocus = false;
            }
        });

        //密码输入框焦点
        disableShowSoftInput(mEtPassword);
        mEtPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordFocus = true;
                mStringBuilder = new StringBuilder();
                String s = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    mStringBuilder.append(s);
                }
            } else {
                passwordFocus = false;
            }
        });

    }


    @OnClick({R.id.btn_back,R.id.tv_get_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    ((TakeOutActivity) mActivity).closeActivity();
                }



                break;
            case R.id.tv_get_password://获取随机密码
                getPassword();
                break;
        }
    }

    private void getPassword() {
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showOneToast("请输入正确手机号码");
            return;
        }
        showWaitingDialog("请稍后...", false);
        MainHttp.get().sendCode(phone, new Bean01Callback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                MyCountDownTimer myCountDownTimer = new MyCountDownTimer(mTvGetPassword, 60000, 1000);
                myCountDownTimer.start();
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((TakeOutActivity) mActivity).startCountDownTime();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).startCountDownTime();
                }else{
                    ((TakeOutActivity) mActivity).startCountDownTime();
                }
                // mActivity.startCountDownTime();
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showOneToast(message);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissWaitingDialog();
            }
        });

    }


    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public void disableShowSoftInput(EditText editText) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {
        }

        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {
        }
    }
}
