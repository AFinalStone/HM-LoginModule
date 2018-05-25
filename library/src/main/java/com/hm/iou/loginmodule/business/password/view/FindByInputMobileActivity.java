package com.hm.iou.loginmodule.business.password.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByInputMobileContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByInputMobilePresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
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
    private static final String REGEXP_MOBILE_NUMBER = "^[1][0-9]{10}$";

    @BindView(R2.id.et_mobile)
    ClearEditText mEtMobile;
    @BindView(R2.id.tv_find)
    TextView mTvFind;

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
                mMobile = String.valueOf(charSequence);
                mTvFind.setEnabled(false);
                if (StringUtil.matchRegex(mMobile, REGEXP_MOBILE_NUMBER)) {
                    mTvFind.setEnabled(true);
                }
            }
        });
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            savedInstanceState.putString(EXTRA_KEY_MOBILE, mMobile);
        }
        if (!StringUtil.isEmpty(mMobile)) {
            mEtMobile.setText(mMobile);
            mEtMobile.setSelection(mMobile.length());
        }
        showSoftKeyboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.tv_find})
    public void onViewClicked(View view) {
        mPresenter.getResetPsdMethod(mMobile);
    }

}
