package com.hm.iou.loginmodule.business.password.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByEmailPresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
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
    public static final String EXTRA_KEY_EMAIL = "email";

    private static String REGEXP_EMAIL_NUMBER = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @BindView(R2.id.et_email)
    ClearEditText mEtEmail;
    String mStrEmail;

    @BindView(R2.id.et_emailCode)
    EditText mEtEmailCode;
    String mStrEtEmailCode = "";

    @BindView(R2.id.btn_getEmailCode)
    TextView mBtnGetEmailCode;

    @BindView(R2.id.tv_find)
    TextView mTvFind;

    String mEmail;
    String mMobile;

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
        RxTextView.textChanges(mEtEmail).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrEmail = String.valueOf(charSequence);
                if (StringUtil.matchRegex(mStrEmail, REGEXP_EMAIL_NUMBER)) {
                    if (mEtEmailCode.length() > 0) {
                        mBtnGetEmailCode.setEnabled(true);
                    }
                } else {
                    mBtnGetEmailCode.setEnabled(false);
                }
            }
        });

        RxTextView.textChanges(mEtEmailCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrEtEmailCode = String.valueOf(charSequence);
                if (mStrEtEmailCode.length() > 0 && mStrEtEmailCode.length() > 0) {
                    mTvFind.setEnabled(true);
                } else {
                    mTvFind.setEnabled(false);
                }
            }
        });
        showSoftKeyboard();
        Intent intent = getIntent();
        mMobile = intent.getStringExtra(EXTRA_KEY_MOBILE);
        mEmail = intent.getStringExtra(EXTRA_KEY_EMAIL);
        String strEmail = getString(R.string.bindEmailAndResetPsd_etEmailLeft) + mEmail + getString(R.string.bindEmailAndResetPsd_etEmailRight);
        mEtEmail.setHint(strEmail);
    }

    @OnClick({R2.id.btn_getEmailCode, R2.id.tv_find})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.btn_getEmailCode == id) {
            mPresenter.sendEmailCheckCode(mStrEmail);
        } else if (R.id.tv_find == id) {
            jumpToLoginResetPsdView();
        }
    }

    private void timeCountDown() {
//        CommonSubscriber<Long> commonSubscriber = new CommonSubscriber<Long>(this) {
//            @Override
//            public void onNext(Long time) {
//                if (btnGmEtEmailCode != null) {
//                    String countDownDesc = getString(R.string.btn_gmEtEmailCode_timeCountDown);
//                    btnGmEtEmailCode.setEnabled(false);
//                    btnGmEtEmailCode.setText(time + countDownDesc);
//                }
//
//            }
//        }.setShowLoading(false);
//        Flowable.interval(0, 1, TimeUnit.SECONDS)
//                .map(s -> 59 - s)
//                .take(60)
//                .compose(RxUtil.rxSchedulerHelper())
//                .compose(bindUntilEvent(ActivityEvent.DESTROY))
//                .doOnComplete(() -> {
//                    if (btnGmEtEmailCode != null) {
//                        btnGmEtEmailCode.setEnabled(true);
//                        btnGmEtEmailCode.setText(R.string.btn_gmEtEmailCode);
//                    }
//                })
//                .subscribeWith(commonSubscriber);
    }


    private void jumpToLoginResetPsdView() {
//        Intent intent = new Intent(mContext, ResetLoginPsdActivity.class);
//        intent.putExtra(Constants.INTENT_RESET_LOGIN_PSD_TYPE, LoginResetPsdTypeEnum.LoginResetPsdByEmail);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, mMobile);
//        intent.putExtra(Constants.INTENT_EMAIL_NUMBER, mStrEmail);
//        intent.putExtra(Constants.INTENT_CHECK_CODE, mEtEmailCode);
//        startActivity(intent);
    }
}
