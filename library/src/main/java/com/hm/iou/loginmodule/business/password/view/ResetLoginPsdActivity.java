package com.hm.iou.loginmodule.business.password.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.ResetLoginPsdContract;
import com.hm.iou.loginmodule.business.password.presenter.ResetLoginPsdPresenter;
import com.hm.iou.uikit.ShowHidePasswordEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 1.通过手机号获取验证码进入此界面，需要提供手机号，验证码，新的密码
 * 2.通过活体校验进入此界面，需要提供手机号，活体校验流水号，新的密码
 * 3.通过邮箱获取验证码进入此界面，需要提供邮箱号，邮箱验证码，新的密码
 *
 * @author syl
 * @time 2018/3/29 下午3:38
 */
public class ResetLoginPsdActivity extends BaseActivity<ResetLoginPsdPresenter> implements ResetLoginPsdContract.View {

    public static final String EXTRA_RESET_PSD_TYPE = "reset_psd_type";
    public static final String EXTRA_MOBILE = "mobile";
    public static final String EXTRA_SMS_CHECK_CODE = "sms_check_code";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_EMAIL_CHECK_CODE = "email_check_code";
    public static final String EXTRA_EMAIL_CHECK_CODE_SN = "email_check_code_sn";
    public static final String EXTRA_FACE_CHECK_SN = "face_check_sn";
    public static final String EXTRA_USER_ID_CARD = "user_id_card";


    public static final String RESET_PSD_TYPE_BY_SMS = "sms";
    public static final String RESET_PSD_TYPE_BY_EMAIL = "email";
    public static final String RESET_PSD_TYPE_BY_FACE = "face";


    @BindView(R2.id.et_password)
    ShowHidePasswordEditText mEtPsd;
    @BindView(R2.id.tv_ok)
    TextView mTvOk;

    //短信验证码
    private String mSMSCheckCode;
    //手机号
    private String mMobile;
    //身份证号码的前六位
    private String mUserIDCard;
    //邮箱
    private String mEmail;
    //邮箱验证码
    private String mEmailCheckCode;
    //校验邮箱验证码是否合法的交易流水号
    private String mEmailCheckCodeSN;
    //活体校验的流水号
    private String mOCRLivingSN;
    //重置密码的方式
    private String mResetPsdType;
    //新密码
    private String mStrPsd;


    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_reset_login_psd;
    }

    @Override
    protected ResetLoginPsdPresenter initPresenter() {
        return new ResetLoginPsdPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mResetPsdType = intent.getStringExtra(EXTRA_RESET_PSD_TYPE);
        mMobile = intent.getStringExtra(EXTRA_MOBILE);
        if (RESET_PSD_TYPE_BY_SMS.equals(mResetPsdType)) {
            mSMSCheckCode = intent.getStringExtra(EXTRA_SMS_CHECK_CODE);
        } else if (RESET_PSD_TYPE_BY_EMAIL.equals(mResetPsdType)) {
            mEmail = intent.getStringExtra(EXTRA_EMAIL);
            mEmailCheckCode = intent.getStringExtra(EXTRA_EMAIL_CHECK_CODE);
            mEmailCheckCodeSN = intent.getStringExtra(EXTRA_EMAIL_CHECK_CODE_SN);
        } else if (RESET_PSD_TYPE_BY_FACE.equals(mResetPsdType)) {
            mOCRLivingSN = intent.getStringExtra(EXTRA_FACE_CHECK_SN);
            mUserIDCard = intent.getStringExtra(EXTRA_USER_ID_CARD);
        }
        RxTextView.textChanges(mEtPsd).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrPsd = String.valueOf(charSequence);
                mTvOk.setEnabled(false);
                if (mStrPsd.length() >= 6) {
                    mTvOk.setEnabled(true);
                }
            }
        });
        showSoftKeyboard();
    }

    @OnClick({R2.id.tv_ok})
    public void onClick(View view) {
        resetPsd();
    }

    private void resetPsd() {
        if (RESET_PSD_TYPE_BY_SMS.equals(mResetPsdType)) {
            mPresenter.resetLoginPsdBySMS(mMobile, mSMSCheckCode, mStrPsd);
        } else if (RESET_PSD_TYPE_BY_EMAIL.equals(mResetPsdType)) {
            mPresenter.resetLoginPsdByEMail(mMobile, mEmail, mEmailCheckCode, mEmailCheckCodeSN, mStrPsd);
        } else if (RESET_PSD_TYPE_BY_FACE.equals(mResetPsdType)) {
            mPresenter.resetLoginPsdByFace(mMobile, mUserIDCard, mStrPsd, mOCRLivingSN);
        }
    }

}
