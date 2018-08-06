package com.hm.iou.loginmodule.business.password.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.loginmodule.business.password.presenter.FindBySMSPresenter;
import com.hm.iou.router.Router;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.keyboard.HMInputNumberView;
import com.hm.iou.uikit.keyboard.HMKeyBoardView;
import com.hm.iou.uikit.keyboard.OnInputCodeListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通过短信验证码找回登录密码
 *
 * @author syl
 */
public class FindBySMSActivity extends BaseActivity<FindBySMSPresenter> implements FindBySMSContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.topBar)
    HMTopBarView mTopBar;

    @BindView(R2.id.tv_mobile)
    TextView mTvMobile;

    @BindView(R2.id.tv_retryCode)
    TextView mTvRetryCode;

    @BindView(R2.id.inputNumView)
    HMInputNumberView mInputNumView;

    @BindView(R2.id.keyBoardView)
    HMKeyBoardView mKeyBoardView;

    private String mMobile;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_find_by_sms;
    }

    @Override
    protected FindBySMSPresenter initPresenter() {
        return new FindBySMSPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                Router.getInstance()
                        .buildWithUrl("hmiou://m.54jietiao.com/login/customer_service")
                        .navigation(mContext);
            }

            @Override
            public void onClickImageMenu() {

            }
        });

        mInputNumView.bindKeyBoardView(mKeyBoardView);

        mInputNumView.setOnInputCodeListener(new OnInputCodeListener() {
            @Override
            public void onInputCodeFinish(String smsCheckCode) {
                mPresenter.compareSMSCheckCode(mMobile, smsCheckCode);
            }
        });

        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
        }
        if (!TextUtils.isEmpty(mMobile) && mMobile.length() == 11) {
            StringBuffer sbMobile = new StringBuffer();
            sbMobile.append(mMobile.substring(0, 3));
            sbMobile.append(" ");
            sbMobile.append(mMobile.substring(3, 7));
            sbMobile.append(" ");
            sbMobile.append(mMobile.substring(7, 11));
            mTvMobile.setText(sbMobile);
            mPresenter.sendSMSCheckCode(mMobile);
        } else {
            finish();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.tv_retryCode})
    public void onClick(View view) {
        if (R.id.tv_retryCode == view.getId()) {
            mPresenter.sendSMSCheckCode(mMobile);
        }
    }

    @Override
    public void setGetSMSBtnText(boolean enable, String text) {
        mTvRetryCode.setEnabled(enable);
        mTvRetryCode.setText(text);
    }

    @Override
    public void warnCheckFailed() {
        mInputNumView.clearInputCode();
        mInputNumView.setError();
    }


}
