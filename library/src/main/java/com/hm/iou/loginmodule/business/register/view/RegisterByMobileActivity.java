package com.hm.iou.loginmodule.business.register.view;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByMobilePresenter;
import com.hm.iou.router.Router;
import com.hm.iou.uikit.HMCountDownTextView;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.ShowHidePasswordEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 用户注册账号
 *
 * @author syl
 * @time 2018/4/23 上午11:12
 */
public class RegisterByMobileActivity extends BaseActivity<RegisterByMobilePresenter> implements RegisterByMobileContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.topBar)
    HMTopBarView mTopBar;
    @BindView(R2.id.et_mobile)
    TextView mEtMobile;
    @BindView(R2.id.et_smsCheckCode)
    EditText mEtSMSCheckCode;
    @BindView(R2.id.tv_getSmsCheckCode)
    HMCountDownTextView mTvGetSmsCheckCode;
    @BindView(R2.id.et_password)
    ShowHidePasswordEditText mEtPsd;
    @BindView(R2.id.btn_register)
    Button mBtnRegister;
    @BindView(R2.id.tv_register_agreement)
    TextView mTvAgreement;

    private String mStrSmsCheckCode = "";
    private String mMobile = "";
    private String mStrPsd = "";

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_register_by_mobile;
    }

    @Override
    protected RegisterByMobilePresenter initPresenter() {
        return new RegisterByMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        TraceUtil.onEvent(this, "mob_page_register");
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                TraceUtil.onEvent(RegisterByMobileActivity.this, "mob_help_click");
                Router.getInstance()
                        .buildWithUrl("hmiou://m.54jietiao.com/login/customer_service")
                        .navigation(mContext);
            }

            @Override
            public void onClickImageMenu() {

            }
        });
        RxTextView.textChanges(mEtPsd).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrPsd = String.valueOf(charSequence);
                checkValue();
            }
        });
        RxTextView.textChanges(mEtSMSCheckCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrSmsCheckCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
        }
        mEtMobile.setText(mMobile);

        String str = mTvAgreement.getText().toString();
        SpannableString spanStr = new SpannableString(str);
        spanStr.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.uikit_function_remind)), 7, 13, 0);
        mTvAgreement.setText(spanStr);

        showUserAgreementDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.btn_register, R2.id.tv_getSmsCheckCode, R2.id.tv_register_agreement, R2.id.et_mobile})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.btn_register == id) {
            TraceUtil.onEvent(this, "mob_reg_click");
            mPresenter.registerByMobileAndLogin(mMobile, mStrPsd, mStrSmsCheckCode);
        } else if (R.id.tv_getSmsCheckCode == id) {
            TraceUtil.onEvent(this, "mob_code_click");
            mPresenter.getSMSCode(mMobile);
        } else if (R.id.tv_register_agreement == id) {
            TraceUtil.onEvent(this, "web_useragreement");
            showUserAgreementDialog();
        } else if (R.id.et_mobile == id) {
            finish();
        }
    }

    private void checkValue() {
        mBtnRegister.setEnabled(false);
        if (mStrPsd.length() >= 6 && mStrSmsCheckCode.length() >= 6) {
            mBtnRegister.setEnabled(true);
        }
    }

    @Override
    public void startCountDown() {
        mTvGetSmsCheckCode.startCountDown();
    }

    private void showUserAgreementDialog() {
        UserAgreementDialog dialog = new UserAgreementDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnUserAgreementListener(new UserAgreementDialog.OnUserAgreementListener() {
            @Override
            public void onAgree() {

            }

            @Override
            public void onDisagree() {
                finish();
            }
        });
        dialog.show();
    }

}
