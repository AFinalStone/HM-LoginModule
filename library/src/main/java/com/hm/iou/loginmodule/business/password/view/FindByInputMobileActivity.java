package com.hm.iou.loginmodule.business.password.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.constants.HMConstants;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByInputMobileContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByInputMobilePresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.MobileInputEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 输入手机号获取找回密码使用什么途径
 *
 * @author syl
 * @time 2018/4/23 上午11:10
 */
public class FindByInputMobileActivity extends BaseActivity<FindByInputMobilePresenter> implements FindByInputMobileContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.et_mobile)
    MobileInputEditText mEtMobile;
    @BindView(R2.id.btn_find)
    Button mBtnFind;

    private String mMobile;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_find_input_mobile;
    }

    @Override
    protected FindByInputMobilePresenter initPresenter() {
        return new FindByInputMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        RxTextView.textChanges(mEtMobile).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mMobile = mEtMobile.getTextWithoutSpace();
                mBtnFind.setEnabled(false);
                if (StringUtil.matchRegex(mMobile, HMConstants.REG_MOBILE)) {
                    mBtnFind.setEnabled(true);
                    mPresenter.getResetPsdMethod(mMobile);
                }
            }
        });
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            savedInstanceState.putString(EXTRA_KEY_MOBILE, mMobile);
        }
        if (!TextUtils.isEmpty(mMobile)) {
            mEtMobile.setText(mMobile);
            mEtMobile.setSelection(mEtMobile.length());
        }
        showSoftKeyboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.btn_find})
    public void onViewClicked(View view) {
        mPresenter.getResetPsdMethod(mMobile);
    }

}
