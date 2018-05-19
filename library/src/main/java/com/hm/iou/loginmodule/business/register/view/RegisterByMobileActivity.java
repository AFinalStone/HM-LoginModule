package com.hm.iou.loginmodule.business.register.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByMobilePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 用户注册账号
 *
 * @author AFinalStone
 * @time 2018/4/23 上午11:12
 */
public class RegisterByMobileActivity extends BaseActivity<RegisterByMobilePresenter> implements RegisterByMobileContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.et_phone)
    TextView etPhone;
    String userPhone = "";

    @BindView(R2.id.et_code)
    EditText etCode;
    String userCode = "";

    @BindView(R2.id.et_password)
    EditText etPassword;
    String userPsd = "";

    @BindView(R2.id.iv_eye)
    ImageView ivEye;
    boolean isEyeOpen = true;

    @BindView(R2.id.btn_register)
    TextView btnRegister;

    @BindView(R2.id.btn_getCode)
    TextView btnGetCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterByMobilePresenter initPresenter() {
        return new RegisterByMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(etPassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                userPsd = String.valueOf(charSequence);
                checkValue();
            }
        });
        RxTextView.textChanges(etCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                userCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        userPhone = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        etPhone.setText(userPhone);
    }

    private void checkValue() {
        btnRegister.setEnabled(false);
        if (userPsd.length() >= 6 && userCode.length() > 0) {
            btnRegister.setEnabled(true);
        }
    }

    private void toLoginLoadingView() {
//        Intent intent = new Intent(mContext, LoginLoadingActivity.class);
//        intent.putExtra(Constants.INTENT_LOGIN_LOADING_TYPE, LoginTypeEnum.loginByRegister);
//        startActivity(intent);
//        finish();
    }

//    @Override
//    public void registerAndLoginSuccess() {
//        toLoginLoadingView();
//    }
//
//    @Override
//    public void getCodeSuccess() {
//        String desc = getString(R.string.getCode_success);
//        showSuccessMsg(desc);
//        timeCountDown();
//    }
//
//
//    @Override
//    public void showRegisterResult() {
//        String desc = getString(R.string.register_registerSuccess);
//        showSuccessMsg(desc);
//    }


    @OnClick({R2.id.iv_eye, R2.id.btn_register, R2.id.btn_getCode, R2.id.tv_agreement01, R2.id.tv_agreement02})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.iv_eye == id) {
            changePassword();
        } else if (R.id.btn_register == id) {
            mPresenter.registerAndLogin(userPhone, userPsd, userCode);
        } else if (R.id.btn_getCode == id) {
            mPresenter.getCode(userPhone);
        } else if (R.id.tv_agreement01 == id) {
            jumpToRegisterAndUseAgreement();
        } else if (R.id.tv_agreement02 == id) {
            jumpToPrivateAgreement();
        }
    }

    private void changePassword() {
        if (isEyeOpen) {
            isEyeOpen = false;
            ivEye.setImageResource(R.mipmap.uikit_icon_password_eye_close);
            // 显示为密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = etPassword.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            isEyeOpen = true;
            ivEye.setImageResource(R.mipmap.uikit_icon_password_eye_open);

            // 显示为普通文本
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = etPassword.getText();
            Selection.setSelection(etable, etable.length());
        }
    }


    private void jumpToRegisterAndUseAgreement() {
//        Intent intent = new Intent(this, WebViewH5Activity.class);
//        String title = getString(R.string.webViewHtml5_titleRegisterAndUseAgreement);
//        intent.putExtra(Constants.INTENT_TITLE, title);
//        intent.putExtra(Constants.INTENT_WEB_URL, Constants.WEB_H5_URL_IOU_AGREEMENT);
//        startActivity(intent);
    }

    private void jumpToPrivateAgreement() {
//        Intent intent = new Intent(this, WebViewH5Activity.class);
//        String title = getString(R.string.webViewHtml5_titlePrivateAgreement);
//        intent.putExtra(Constants.INTENT_TITLE, title);
//        intent.putExtra(Constants.INTENT_WEB_URL, Constants.WEB_H5_URL_PRIVACY_AGREEMENT);
//        startActivity(intent);
    }

}
