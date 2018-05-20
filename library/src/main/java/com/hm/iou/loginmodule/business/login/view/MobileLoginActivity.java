package com.hm.iou.loginmodule.business.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.login.MobileLoginContract;
import com.hm.iou.loginmodule.business.login.presenter.MobileLoginPresenter;
import com.hm.iou.uikit.HMPasswordEditText;
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

    @BindView(R2.id.et_phone)
    TextView etUserPhone;
    String userPhone = "";

    @BindView(R2.id.et_password)
    HMPasswordEditText etPassword;
    String userPsd = "";

    @BindView(R2.id.btn_login)
    TextView btnLogin;

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
                userPsd = String.valueOf(charSequence);
                btnLogin.setEnabled(false);
                if (userPsd.length() >= 6) {
                    btnLogin.setEnabled(true);
                }
            }
        });
        showSoftKeyboard();
        userPhone = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        etUserPhone.setText(userPhone);
    }

    private void toFindPswView() {
//        Intent intent = new Intent(mContext, FindLoginPsdInputPhoneActivity.class);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, userPhone);
//        startActivity(intent);
    }


    @OnClick({R2.id.btn_login, R2.id.tv_forgetPassword})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.btn_login == id) {
            mPresenter.login(userPhone, userPsd);
        } else if (R.id.tv_forgetPassword == id) {
            toFindPswView();
        }
    }


    private void toLoginLoadingView() {
//        Intent intent = new Intent(mContext, LoginLoadingActivity.class);
//        intent.putExtra(Constants.INTENT_LOGIN_LOADING_TYPE, LoginTypeEnum.loginByPhone);
//        startActivity(intent);
//        finish();
    }


}
