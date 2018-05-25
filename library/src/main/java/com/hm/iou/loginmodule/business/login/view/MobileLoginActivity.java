package com.hm.iou.loginmodule.business.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.login.MobileLoginContract;
import com.hm.iou.loginmodule.business.login.presenter.MobileLoginPresenter;
import com.hm.iou.uikit.ShowHidePasswordEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 通过手机号进行登录
 * Created by syl on 2017/11/10.
 */
public class MobileLoginActivity extends BaseActivity<MobileLoginPresenter> implements MobileLoginContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.et_mobile)
    TextView mTvMobile;

    @BindView(R2.id.et_password)
    ShowHidePasswordEditText etPassword;

    @BindView(R2.id.tv_login)
    TextView mTvLogin;

    private String mMobile = "";
    private String mStrPsd = "";

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_login_by_mobile;
    }

    @Override
    protected MobileLoginPresenter initPresenter() {
        return new MobileLoginPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(etPassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrPsd = String.valueOf(charSequence);
                mTvLogin.setEnabled(false);
                if (mStrPsd.length() >= 6) {
                    mTvLogin.setEnabled(true);
                }
            }
        });
        showSoftKeyboard();
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
        }
        mTvMobile.setText(mMobile);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.tv_login, R2.id.tv_forgetPassword})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.tv_login == id) {
            mPresenter.mobileLogin(mMobile, mStrPsd);
        } else if (R.id.tv_forgetPassword == id) {
            NavigationHelper.toFindByInputMobile(mContext, mMobile);
        }
    }


}
