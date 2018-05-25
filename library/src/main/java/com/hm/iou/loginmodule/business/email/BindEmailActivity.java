package com.hm.iou.loginmodule.business.email;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.HMCountDownTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 绑定邮箱
 *
 * @author syl
 */
public class BindEmailActivity extends BaseActivity<BindEmailPresenter> implements BindEmailContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    private static String REGEXP_EMAIL_NUMBER = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @BindView(R2.id.et_email)
    ClearEditText mEtEmail;
    @BindView(R2.id.et_emailCode)
    EditText mEtEmailCode;
    @BindView(R2.id.tv_getEmailCode)
    HMCountDownTextView mTvGetEmailCode;
    @BindView(R2.id.tv_bindEmail)
    TextView mTvBindEmail;

    private String mStrEmail = "";
    private String mStrEmailCheckCode = "";
    private String mMobile = "";

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_bind_email;
    }

    @Override
    protected BindEmailPresenter initPresenter() {
        return new BindEmailPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(mEtEmail).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrEmail = String.valueOf(charSequence);
                mTvGetEmailCode.setEnabled(false);
                if (StringUtil.matchRegex(mStrEmail, REGEXP_EMAIL_NUMBER)) {
                    mTvGetEmailCode.setEnabled(true);
                }
                checkValue();
            }
        });

        RxTextView.textChanges(mEtEmailCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrEmailCheckCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        showSoftKeyboard();
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.tv_getEmailCode, R2.id.tv_bindEmail})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.tv_getEmailCode == id) {
            mPresenter.sendEmailCheckCode(mStrEmail);
        } else if (R.id.tv_bindEmail == id) {
            mPresenter.bindEmail(mStrEmail, mStrEmailCheckCode);
        }
    }

    private void checkValue() {
        mTvBindEmail.setEnabled(false);
        if (StringUtil.matchRegex(mStrEmail, REGEXP_EMAIL_NUMBER) && mStrEmailCheckCode.length() > 0) {
            mTvBindEmail.setEnabled(true);
        }
    }


    private void jumpToLoginResetPsdView() {
//        Intent intent = new Intent(mContext, ResetLoginPsdActivity.class);
//        intent.putExtra(Constants.INTENT_RESET_LOGIN_PSD_TYPE, LoginResetPsdTypeEnum.LoginResetPsdByEmail);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, mMobile);
//        intent.putExtra(Constants.INTENT_EMAIL_NUMBER, mStrEmail);
//        intent.putExtra(Constants.INTENT_CHECK_CODE, mEtEmailCode);
//        startActivity(intent);
    }

    @Override
    public void startCountDown() {
        mTvGetEmailCode.startCountDown();
    }
}
