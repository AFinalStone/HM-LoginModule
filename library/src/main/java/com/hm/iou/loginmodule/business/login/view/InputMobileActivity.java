package com.hm.iou.loginmodule.business.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.login.InputMobileContract;
import com.hm.iou.loginmodule.business.login.presenter.InputMobilePresenter;
import com.hm.iou.uikit.ClearEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 用户输入手机号,判断该手机号是否已经注册
 * 1.已经注册则进入登录页面
 * 2.没有注册则进入注册页面进行注册操作
 *
 * @author syl
 * @time 2018/4/23 上午11:10
 */
public class InputMobileActivity extends BaseActivity<InputMobilePresenter> implements InputMobileContract.View {

    @BindView(R2.id.et_phone)
    ClearEditText etPhone;
    String userMobile;

    @BindView(R2.id.btn_next)
    TextView btnNext;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_phone;
    }

    @Override
    protected InputMobilePresenter initPresenter() {
        return new InputMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(etPhone).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                userMobile = String.valueOf(charSequence);
                btnNext.setEnabled(false);
                if (userMobile.length() == 11) {
                    btnNext.setEnabled(true);
                }
            }
        });
        showSoftKeyboard();
    }

    private void checkPhone() {
//        mPresenter.checkAccountIsExist(userMobile);
    }

    public void jumpToLoginByPhoneView() {
//        Intent intent = new Intent(this, LoginByPhoneActivity.class);
//        intent.putExtra(Constants.INTENT_INPUT_PHONE, userMobile);
//        startActivity(intent);
    }

    public void jumpToRegisterView() {
//        Intent intent = new Intent(this, RegisterByMobileActivity.class);
//        intent.putExtra(Constants.INTENT_INPUT_PHONE, userMobile);
//        startActivity(intent);
    }

    @OnClick({R2.id.btn_next})
    public void onViewClicked(View view) {
        if (R.id.btn_next == view.getId()) {
            mPresenter.checkAccountIsExist(userMobile);
        }
    }


}
