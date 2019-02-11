package cn.dlc.guankungongxiangjicunji.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
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
import cn.dlc.guankungongxiangjicunji.main.bean.CabinetListBean;
import cn.dlc.guankungongxiangjicunji.main.bean.MakeCupboardOrderBean;
import cn.dlc.guankungongxiangjicunji.main.bean.UserInfo;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomKeyboardView;

import com.dlc.vendingcabinets.Constant;

import java.lang.reflect.Method;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class IdCardWayFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    Button mBtnBack;

    @BindView(R.id.customKeyboardView)
    CustomKeyboardView mCustomKeyboardView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private String idCard;
    private String name;
    private CabinetListBean.DataBean mDataBean;


    private boolean passwordFocus = false;
    private boolean confirmPasswordFocus = false;
    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_id_card_way;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_id_card_way_hs;
        }
        return R.layout.fragment_id_card_way;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idCard = getArguments().getString("idCard");
        name = getArguments().getString("name");
        mDataBean = (CabinetListBean.DataBean) getArguments().getSerializable("mDataBean");
        setListener();
        initKeyboardView();
    }


    private void initKeyboardView() {

        mCustomKeyboardView.setOnClickKeyboardItemListener(
                new CustomKeyboardView.OnClickKeyboardItemListener() {
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

                        confirmHandle();

                    }
                });
    }


    private void confirmHandle() {
        //确认成功后，跳转到支付方式
//        switchFragment();
        String password = mEtPassword.getText().toString();
        String confirmPassword = mEtConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() != 5) {
            showOneToast("请输入5位数字密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword) || confirmPassword.length() != 5) {
            showOneToast("请输入5位数字密码");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showOneToast("两次密码不相同");
            return;
        }

        showWaitingDialog("上传中", true);
        MainHttp.get().checkCode("", idCard, name, password, "1", new Bean01Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                final String token = userInfo.data.userinfo.token;
                MainHttp.get().makeCupboardOrder(Constant.MACNO, password, "1", String.valueOf(mDataBean.cupboard_charging), userInfo.data.userinfo.token, new Bean01Callback<MakeCupboardOrderBean>() {
                    @Override
                    public void onSuccess(MakeCupboardOrderBean makeCupboardOrderBean) {
                        dismissWaitingDialog();
                        Bundle mBundle = new Bundle();
                        mBundle.putString("paylog", makeCupboardOrderBean.data.paylog);
                        mBundle.putString("money", makeCupboardOrderBean.data.money);
                        mBundle.putString("goodsno", makeCupboardOrderBean.data.goodsno);
                        mBundle.putSerializable("mDataBean", mDataBean);
                        mBundle.putString("token", token);

                        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                            ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectPayWayFragment(), mBundle);
                        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                            ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getSelectPayWayFragment(), mBundle);
                        }else{
                            ((SaveActivity) mActivity).switchFragment(((SaveActivity) mActivity).getSelectPayWayFragment(), mBundle);
                        }
                        // mActivity.switchFragment(mActivity.getSelectPayWayFragment(), mBundle);
                    }

                    @Override
                    public void onFailure(String message, Throwable tr) {
                        dismissWaitingDialog();
                        showOneToast(message);
                    }
                });
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                dismissWaitingDialog();
                showOneToast(message);
            }
        });

    }

    private void deletePhoneOrPassword() {
        if (passwordFocus) {
            delete(mEtPassword);
        }

        if (confirmPasswordFocus) {
            delete(mEtConfirmPassword);
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
        if (passwordFocus) {
            if (mEtPassword.getText().length() < 5) {
                mStringBuilder.append(num);
                String content = mStringBuilder.toString();
                mEtPassword.setText(content);
                mEtPassword.setSelection(content.length());
            }
        }

        if (confirmPasswordFocus) {
            if (mEtConfirmPassword.getText().length() < 5) {
                mStringBuilder.append(num);
                String content = mStringBuilder.toString();
                mEtConfirmPassword.setText(content);
                mEtConfirmPassword.setSelection(content.length());
            }

        }
    }

    private void setListener() {

        mEtPassword.setInputType(InputType.TYPE_NULL);



        //密码输入框焦点
        disableShowSoftInput(mEtPassword);
        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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
            }
        });

        //确认密码输入框焦点
        disableShowSoftInput(mEtConfirmPassword);
        mEtConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    confirmPasswordFocus = true;
                    mStringBuilder = new StringBuilder();
                    String s = mEtConfirmPassword.getText().toString();
                    if (!TextUtils.isEmpty(s)) {
                        mStringBuilder.append(s);
                    }
                } else {
                    confirmPasswordFocus = false;
                }
            }
        });

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((SaveActivity) mActivity).startCountDownTime();
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).startCountDownTime();
        }else{
            ((SaveActivity) mActivity).startCountDownTime();
        }
        //mActivity.startCountDownTime();

    }

    @OnClick({R.id.btn_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    mActivity.finish();
                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                    ((HalfSizeActivty) mActivity).closeActivity();
                }else{
                    mActivity.finish();
                }

                break;
            case R.id.btn_confirm://确认
                confirmHandle();
                break;
        }
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
