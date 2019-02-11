package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dlc.vendingcabinets.Constant;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.SaveActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.BaseBean;
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MakeCupboardOrderBean;
import cn.dlc.guankungongxiangjicunji.main.bean.UserInfo;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomKeyboardView;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomKeyboardViewHs;
import cn.dlc.guankungongxiangjicunji.main.widget.MyCountDownTimer;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class PhoneWayFragment extends BaseFragment {

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
    private int mType;

    private CabinetListBean.DataBean mDataBean;
    private MyCountDownTimer myCountDownTimer;


    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_phone_way;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_phone_way_hs;
        }else{
            return R.layout.fragment_phone_way;
        }
        //return R.layout.fragment_phone_way;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");
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
        MainHttp.get().checkCode(phone, "", "", password, "0", new Bean01Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                makeOrder(password, String.valueOf(mDataBean.cupboard_charging), userInfo.data.userinfo.token);

            }

            @Override
            public void onFailure(String message, Throwable tr) {
                dismissWaitingDialog();
                showOneToast(message);
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    mActivity.finish();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    mActivity.finish();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();

            }
        });
    }

    private void makeOrder(String code, String cupboardCharging, String token) {
        MainHttp.get().makeCupboardOrder(Constant.MACNO, code, "0", cupboardCharging, token, new Bean01Callback<MakeCupboardOrderBean>() {
            @Override
            public void onSuccess(MakeCupboardOrderBean makeCupboardOrderBean) {
                Bundle mBundle = new Bundle();
                mBundle.putString("paylog", makeCupboardOrderBean.data.paylog);
                mBundle.putString("money", makeCupboardOrderBean.data.money);
                mBundle.putString("goodsno", makeCupboardOrderBean.data.goodsno);
                mBundle.putSerializable("mDataBean", mDataBean);
                mBundle.putString("token", token);

                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectPayWayFragment(), mBundle);
                    ((SaveActivity) mActivity).setStep(5, false, false);
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getSelectPayWayFragment(), mBundle);
                    ((HalfSizeActivty) mActivity).setStep(5, false, false);
                }else{
                    ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectPayWayFragment(), mBundle);
                    ((SaveActivity) mActivity).setStep(5, false, false);
                }
//                mActivity.switchFragment(mActivity.getSelectPayWayFragment(),mBundle);
//                mActivity.setStep(5, false, false);
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


    @OnClick({R.id.btn_back, R.id.tv_get_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).closeActivity();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    ((SaveActivity) mActivity).closeActivity();
                }
                //   mActivity.closeActivity();
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
                myCountDownTimer = new MyCountDownTimer(mTvGetPassword, 60000, 1000);
                myCountDownTimer.start();
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((SaveActivity) mActivity).startCountDownTime();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).startCountDownTime();
                }else{
                    ((SaveActivity) mActivity).startCountDownTime();
                }
                //   mActivity.startCountDownTime();
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showOneToast(message);
                mActivity.finish();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissWaitingDialog();
            }
        });


    }


    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
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

    @Override
    public void onDestroy() {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
