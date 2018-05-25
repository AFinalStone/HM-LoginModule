package com.hm.iou.loginmodule.business.password.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByEmailPresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.HMCountDownTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 获取邮箱验证码
 *
 * @author syl
 */
public class FindByEmailActivity extends BaseActivity<FindByEmailPresenter> implements FindByEmailContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";
    public static final String EXTRA_KEY_TIP_EMAIL = "tip_email";

    private static String REGEXP_EMAIL_NUMBER = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @BindView(R2.id.et_email)
    ClearEditText mEtEmail;
    @BindView(R2.id.et_emailCode)
    EditText mEtEmailCode;
    @BindView(R2.id.tv_getEmailCode)
    HMCountDownTextView mTvGetEmailCode;
    @BindView(R2.id.tv_find)
    TextView mTvFind;

    private String mStrEmail = "";
    private String mStrEmailCode = "";
    private String mTipEmail = "";
    private String mMobile = "";

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_find_by_email;
    }

    @Override
    protected FindByEmailPresenter initPresenter() {
        return new FindByEmailPresenter(this, this);
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
                mStrEmailCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        showSoftKeyboard();
        Intent intent = getIntent();
        mMobile = intent.getStringExtra(EXTRA_KEY_MOBILE);
        mTipEmail = intent.getStringExtra(EXTRA_KEY_TIP_EMAIL);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
            mTipEmail = savedInstanceState.getString(EXTRA_KEY_TIP_EMAIL);
        }
        String strEmail = getString(R.string.bindEmailAndResetPsd_etEmailLeft) + mTipEmail + getString(R.string.bindEmailAndResetPsd_etEmailRight);
        mEtEmail.setHint(strEmail);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
        outState.putString(EXTRA_KEY_TIP_EMAIL, mTipEmail);
    }

    @OnClick({R2.id.tv_getEmailCode, R2.id.tv_find})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.tv_getEmailCode == id) {
            mPresenter.sendEmailCheckCode(mStrEmail);
        } else if (R.id.tv_find == id) {
            mPresenter.compareEmailCheckCode(mMobile, mStrEmail, mStrEmailCode);
        }
    }

    private void checkValue() {
        mTvFind.setEnabled(false);
        if (StringUtil.matchRegex(mStrEmail, REGEXP_EMAIL_NUMBER) && mStrEmailCode.length() > 0) {
            mTvFind.setEnabled(true);
        }
    }

    @Override
    public void startCountDown() {
        mTvGetEmailCode.startCountDown();
    }
}
