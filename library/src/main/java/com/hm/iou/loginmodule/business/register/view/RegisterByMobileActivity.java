package com.hm.iou.loginmodule.business.register.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByMobilePresenter;
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

    @BindView(R2.id.et_mobile)
    TextView mEtMobile;
    @BindView(R2.id.et_smsCheckCode)
    EditText mEtSMSCheckCode;
    @BindView(R2.id.et_password)
    ShowHidePasswordEditText mEtPsd;
    @BindView(R2.id.tv_register)
    TextView mTvRegister;

    private String mStrSmsCheckCode = "";
    private String mStrMobile = "";
    private String mStrPsd = "";

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_register_mobile;
    }

    @Override
    protected RegisterByMobilePresenter initPresenter() {
        return new RegisterByMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
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
        mStrMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        mEtMobile.setText(mStrMobile);
    }

    private void checkValue() {
        mTvRegister.setEnabled(false);
        if (mStrPsd.length() >= 6 && mStrSmsCheckCode.length() > 0) {
            mTvRegister.setEnabled(true);
        }
    }

    @OnClick({R2.id.tv_register, R2.id.tv_getSmsCheckCode, R2.id.tv_agreement01, R2.id.tv_agreement02})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.tv_register == id) {
            mPresenter.registerByMobileAndLogin(mStrMobile, mStrPsd, mStrSmsCheckCode);
        } else if (R.id.tv_getSmsCheckCode == id) {
            mPresenter.getSMSCode(mStrMobile);
        } else if (R.id.tv_agreement01 == id) {
            NavigationHelper.ToRegisterAndUseAgreement(mContext);
        } else if (R.id.tv_agreement02 == id) {
            NavigationHelper.toPrivateAgreement(mContext);
        }
    }

}
