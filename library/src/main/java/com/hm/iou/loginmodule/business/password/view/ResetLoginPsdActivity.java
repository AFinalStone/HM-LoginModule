package com.hm.iou.loginmodule.business.password.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.ResetLoginPsdContract;
import com.hm.iou.loginmodule.business.password.presenter.ResetLoginPsdPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 1.通过手机号获取验证码进入此界面，需要提供手机号，验证码，新的密码
 * 2.通过活体校验进入此界面，需要提供手机号，活体校验流水号，新的密码
 * 3.通过邮箱获取验证码进入此界面，需要提供邮箱号，邮箱验证码，新的密码
 *
 * @author AFinalStone
 * @time 2018/3/29 下午3:38
 */
public class ResetLoginPsdActivity extends BaseActivity<ResetLoginPsdPresenter> implements ResetLoginPsdContract.View {

    public static final String EXTRA_RESET_PSD_TYPE = "type";
    public static final String RESET_PSD_TYPE_BY_SMS = "sms";
    public static final String RESET_PSD_TYPE_BY_EMAIL = "email";
    public static final String RESET_PSD_TYPE_BY_FACE = "face";


    @BindView(R2.id.et_password)
    EditText etPassword;
    private String strPassword;

    @BindView(R2.id.iv_eye)
    ImageView ivEye;
    @BindView(R2.id.btn_queryOk)
    TextView btn_queryOk;

    boolean isEyeOpen = false;
    String currentCheckCode;
    String currentMobileNumber;
    String currentUserIDCard;
    String currentEmail;
    String ocrLivingSn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_login_psd;
    }

    @Override
    protected ResetLoginPsdPresenter initPresenter() {
        return new ResetLoginPsdPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        loginResetPsdTypeEnum = (LoginResetPsdTypeEnum) intent.getSerializableExtra(Constants.INTENT_RESET_LOGIN_PSD_TYPE);
        currentMobileNumber = intent.getStringExtra(Constants.INTENT_MOBILE_NUMBER);
        switch (loginResetPsdTypeEnum) {
            case LoginResetPsdBySMS:
                currentCheckCode = intent.getStringExtra(Constants.INTENT_CHECK_CODE);
                break;

            case LoginResetPsdByEmail:
                currentEmail = intent.getStringExtra(Constants.INTENT_EMAIL_NUMBER);
                currentCheckCode = intent.getStringExtra(Constants.INTENT_CHECK_CODE);
                break;

            case LoginResetPsdByRealFace:
                ocrLivingSn = intent.getStringExtra(Constants.INTENT_ORC_LIVING_SN);
                currentUserIDCard = intent.getStringExtra(Constants.INTENT_USER_ID_CARD_CODE);
                break;
        }

        RxTextView.textChanges(etPassword).subscribe(charSequence -> {
            strPassword = String.valueOf(charSequence);
            btn_queryOk.setEnabled(false);
            if (strPassword.length() >= 6) {
                btn_queryOk.setEnabled(true);
            }
        });
        etPassword.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etPassword, 0);
            }
        }, Constants.KEYBOARD_DIALOG_SHOW_DELAY_TIME);
    }


    private void changePassword() {
        if (isEyeOpen) {
            isEyeOpen = false;
            ivEye.setImageResource(R.mipmap.icon_password_eye_close);
            // 显示为密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = etPassword.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            isEyeOpen = true;
            ivEye.setImageResource(R.mipmap.icon_password_eye_open);

            // 显示为普通文本
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = etPassword.getText();
            Selection.setSelection(etable, etable.length());
        }
    }

    @OnClick({R.id.iv_eye, R.id.btn_queryOk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_eye:
                changePassword();
                break;
            case R.id.btn_queryOk:
                resetPsd();
                break;
        }
    }

    private void resetPsd() {
        switch (loginResetPsdTypeEnum) {
            case LoginResetPsdBySMS:
                mPresenter.resetQueryPsdBySMS(currentMobileNumber, currentCheckCode, strPassword);
                break;

            case LoginResetPsdByEmail:
                mPresenter.resetQueryPswdByMail(currentMobileNumber, currentEmail, currentCheckCode, strPassword);
                break;

            case LoginResetPsdByRealFace:
                mPresenter.resetQueryPswdWithLiveness(currentMobileNumber, currentUserIDCard, strPassword, ocrLivingSn);
                break;
        }
    }

    @Override
    public void resetQueryPsdSuccess() {
        mPresenter.mobileLogin(currentMobileNumber, strPassword);
    }

    @Override
    public void loginSuccess() {
        toLoginLoadingView();
    }

    private void toLoginLoadingView() {
        Intent intent = new Intent(mContext, LoginLoadingActivity.class);
        intent.putExtra(Constants.INTENT_LOGIN_LOADING_TYPE, LoginTypeEnum.loginByResetLoginPsd);
        startActivity(intent);
        finish();
    }

}
