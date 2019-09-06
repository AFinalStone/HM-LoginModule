package com.hm.iou.loginmodule.business.password.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.constants.HMConstants;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByEmailPresenter;
import com.hm.iou.router.Router;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.HMCountDownTextView;
import com.hm.iou.uikit.HMTopBarView;
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

    @BindView(R2.id.topBar)
    HMTopBarView mTopBar;

    @BindView(R2.id.et_email)
    ClearEditText mEtEmail;

    @BindView(R2.id.et_emailCode)
    EditText mEtEmailCode;

    @BindView(R2.id.tv_getEmailCode)
    HMCountDownTextView mTvGetEmailCode;

    @BindView(R2.id.btn_find)
    Button mBtnFind;

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
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                RouterUtil.toSubmitFeedback(FindByEmailActivity.this, "Find_Pwd_By_Email", "Not_Recv_Email_Code");
            }

            @Override
            public void onClickImageMenu() {

            }
        });

        RxTextView.textChanges(mEtEmail).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrEmail = String.valueOf(charSequence);
                mTvGetEmailCode.setEnabled(false);
                if (StringUtil.matchRegex(mStrEmail, HMConstants.REG_EMAIL_NUMBER)) {
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
        String strEmail = getString(R.string.loginmodule_bind_email_or_reset_login_psd_tip) + mTipEmail + getString(R.string.loginmodule_bind_email_or_reset_login_psd_email);
        mEtEmail.setHint(strEmail);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
        outState.putString(EXTRA_KEY_TIP_EMAIL, mTipEmail);
    }

    @OnClick({R2.id.tv_getEmailCode, R2.id.btn_find})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.tv_getEmailCode == id) {
            mPresenter.sendEmailCheckCode(mMobile, mStrEmail);
        } else if (R.id.btn_find == id) {
            mPresenter.compareEmailCheckCode(mMobile, mStrEmail, mStrEmailCode);
        }
    }

    private void checkValue() {
        mBtnFind.setEnabled(false);
        if (StringUtil.matchRegex(mStrEmail, HMConstants.REG_EMAIL_NUMBER) && mStrEmailCode.length() >= 6) {
            mBtnFind.setEnabled(true);
        }
    }

    @Override
    public void startCountDown() {
        mTvGetEmailCode.startCountDown();
    }
}
