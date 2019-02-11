package cn.dlc.guankungongxiangjicunji.main.fragment.take_out_fragment;

import android.os.Build;
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
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.guankungongxiangjicunji.R;
import cn.dlc.guankungongxiangjicunji.base.BaseFragment;
import cn.dlc.guankungongxiangjicunji.hs.HalfSizeActivty;
import cn.dlc.guankungongxiangjicunji.main.MainHttp;
import cn.dlc.guankungongxiangjicunji.main.activity.TakeOutActivity;
import cn.dlc.guankungongxiangjicunji.main.bean.GetGoodsBean;
import cn.dlc.guankungongxiangjicunji.main.bean.UserInfo;
import cn.dlc.guankungongxiangjicunji.main.widget.CustomKeyboardView;

import com.dlc.vendingcabinets.Constant;

import java.lang.reflect.Method;

/**
 * Created by wuyufeng on 2018/3/28.
 */

public class TakeOutIdCardWayFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    Button mBtnBack;

    @BindView(R.id.customKeyboardView)
    CustomKeyboardView mCustomKeyboardView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private String idCard;

    private String name;

    private boolean passwordFocus = false;
    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    protected int getLayoutId() {
        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_id_card_way_take_out;
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            return R.layout.fragment_id_card_way_take_out_hs;
        }
        return R.layout.fragment_id_card_way_take_out;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setListener();
        initKeyboardView();
    }


    public void setView() {
        idCard = getArguments().getString("idCard");
        name = getArguments().getString("name");
        mTvName.setText(String.format(ResUtil.getString(R.string.zunjingdexiansheng), name));
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

        //如果没有超过起步寄存时间
        //mActivity.setStep(6,false,false);
        //mActivity.switchFragment(mActivity.getTakeOutOpenCabinetFragment());
        //mActivity.getTakeOutOpenCabinetFragment().setView("19");

        //如果超过起步寄存时间
       // mActivity.setStep(5, false, false);

        if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((TakeOutActivity) mActivity).setStep(5, false, false);
        } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
            ((HalfSizeActivty) mActivity).setTakeoutStep(5, false, false);
        }else{
            ((TakeOutActivity) mActivity).setStep(5, false, false);
        }

        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() != 5) {
            showOneToast("请输入5位数字密码");
            return;
        }

        showWaitingDialog("获取数据中", true);

        MainHttp.get().getGoodsCheckCode("", idCard, name, password, "1", new Bean01Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                String token = userInfo.data.userinfo.token;
                MainHttp.get().getGoods(Constant.MACNO, userInfo.data.userinfo.token, new Bean01Callback<GetGoodsBean>() {
                    @Override
                    public void onSuccess(GetGoodsBean getGoodsBean) {
                        dismissWaitingDialog();

                        switch (getGoodsBean.data.status) {
                            case "pay":
                                //超时支付
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
                               // mActivity.setStep(5, false, false);
                               // mActivity.switchFragment(mActivity.getTakeOutSelectPayWayFragment(), mBundle);
                                break;
                            case "finish":
                                Bundle mBundle1 = new Bundle();
                                mBundle1.putString("goodsno", getGoodsBean.data.goodsno);

                                if ("FUll".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                    ((TakeOutActivity) mActivity).setStep(6, false, false);
                                    ((TakeOutActivity) mActivity).switchFragment(  ((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                                } else if ("HALF".equals(PrefUtil.getDefault().getString("Type", "FULL"))) {
                                    ((HalfSizeActivty) mActivity).setTakeoutStep(6, false, false);
                                    ((HalfSizeActivty) mActivity).switchFragment(((HalfSizeActivty) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                                }else{
                                    ((TakeOutActivity) mActivity).setStep(6, false, false);
                                    ((TakeOutActivity) mActivity).switchFragment(  ((TakeOutActivity) mActivity).getTakeOutOpenCabinetFragment(), mBundle1);
                                }
//                                mActivity.setStep(6, false, false);
//                                mActivity.switchFragment(mActivity.getTakeOutOpenCabinetFragment(), mBundle1);
                                break;
                            case "fail":

                                break;
                        }

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
                });
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
        });

    }

    private void deletePhoneOrPassword() {
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
        if (passwordFocus) {
            if (mEtPassword.getText().length() < 5) {
                mStringBuilder.append(num);
                String content = mStringBuilder.toString();
                mEtPassword.setText(content);
                mEtPassword.setSelection(content.length());
            }
        }

    }

    private void setListener() {
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


    }

    @OnClick({R.id.btn_back, R.id.btn_confirm})
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
               // mActivity.closeActivity();
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
        if (Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
