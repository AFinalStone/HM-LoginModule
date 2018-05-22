package com.hm.iou.loginmodule.business.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.login.InputMobileContract;
import com.hm.iou.loginmodule.business.login.presenter.InputMobilePresenter;
import com.hm.iou.tools.StringUtil;
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

    private static final String REGEXP_MOBILE_NUMBER = "^[1][0-9]{10}$";

    @BindView(R2.id.et_mobile)
    ClearEditText mEtMobile;
    @BindView(R2.id.tv_next)
    TextView mTvNext;

    String mStrMobile;


    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_input_mobile;
    }

    @Override
    protected InputMobilePresenter initPresenter() {
        return new InputMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(mEtMobile).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrMobile = String.valueOf(charSequence);
                mTvNext.setEnabled(false);
                if (StringUtil.matchRegex(mStrMobile, REGEXP_MOBILE_NUMBER)) {
                    mTvNext.setEnabled(true);
                }
            }
        });
        showSoftKeyboard();
    }

    @OnClick({R2.id.tv_next})
    public void onViewClicked(View view) {
        mPresenter.checkAccountIsExist(mStrMobile);
    }


}
