package com.hm.iou.loginmodule.business.register.view;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByMobilePresenter;
import com.hm.iou.router.Router;
import com.hm.iou.uikit.HMCountDownTextView;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.ShowHidePasswordEditText;
import com.hm.iou.uikit.dialog.HMActionSheetDialog;
import com.hm.iou.uikit.dialog.HMAlertDialog;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

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

    private HMActionSheetDialog mBottomDialog;
    private HMAlertDialog mVoiceDialog;

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
                RouterUtil.toSubmitFeedback(RegisterByMobileActivity.this, "Mobile_Register", "Register_Not_Recv_Mobile_Code");
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
        spanStr.setSpan(new UnderlineSpan(), 8, 12, 0);
        mTvAgreement.setText(spanStr);

        showUserAgreementDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.btn_register, R2.id.tv_getSmsCheckCode, R2.id.tv_register_agreement, R2.id.et_mobile, R2.id.tv_not_get_code})
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
        } else if (R.id.tv_not_get_code == id) {
            showBottomDialog();
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

    @Override
    public void showVoiceTipDialog() {
        if (mVoiceDialog == null) {
            mVoiceDialog = new HMAlertDialog
                    .Builder(mContext)
                    .setTitle("温馨提示")
                    .setMessage("语音可能有延迟，请再等一会儿")
                    .setPositiveButton("再等一会儿")
                    .create();
        }
        mVoiceDialog.show();
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

    private void showBottomDialog() {
        if (mBottomDialog == null) {
            List<String> list = new ArrayList<>();
            list.add("获取语音验证码");
            list.add("一键反馈问题");
            mBottomDialog = new HMActionSheetDialog
                    .Builder(mContext)
                    .setCanSelected(false)
                    .setActionSheetList(list)
                    .setOnItemClickListener(new HMActionSheetDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(int i, String s) {
                            if (0 == i) {
                                mPresenter.getVoiceCode(mMobile);
                            } else if (1 == i) {
                                RouterUtil.toSubmitFeedback(RegisterByMobileActivity.this, "Mobile_Register_Not_Recv_Code", "Register_Not_Recv_Mobile_Code");
                            }
                        }
                    })
                    .create();
        }
        mBottomDialog.show();
    }

}
