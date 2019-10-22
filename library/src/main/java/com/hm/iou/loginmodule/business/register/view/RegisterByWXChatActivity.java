package com.hm.iou.loginmodule.business.register.view;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.constants.HMConstants;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByWXChatPresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.HMCountDownTextView;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.dialog.HMActionSheetDialog;
import com.hm.iou.uikit.dialog.HMAlertDialog;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * 微信注册或者绑定
 *
 * @author syl
 * @time 2018/5/17 下午5:56
 */
public class RegisterByWXChatActivity extends BaseActivity<RegisterByWXChatPresenter> implements RegisterByWXChatContract.View {

    public static final String EXTRA_KEY_WX_CHAT_SN = "wx_chat_sn";

    @BindView(R2.id.topBar)
    HMTopBarView mTopBar;
    @BindView(R2.id.et_mobile)
    ClearEditText mEtMobile;
    @BindView(R2.id.et_smsCheckCode)
    EditText mEtSMSCheckCode;
    @BindView(R2.id.tv_getSMSCheckCode)
    HMCountDownTextView mTvGetSMSCheckCode;
    @BindView(R2.id.tv_not_get_code)
    TextView mTvNotGetCode;
    @BindView(R2.id.et_password)
    EditText mEtPassword;
    @BindView(R2.id.btn_next)
    Button mBtnNext;
    @BindView(R2.id.tv_register_agreement)
    TextView mTvAgreement;

    private HMActionSheetDialog mBottomDialog;
    private HMAlertDialog mVoiceDialog;

    String mStrSMSCheckCode = "";
    String mStrMobile = "";
    String mStrPsd = "";

    //判断当前微信是否存绑定过手机的交易流水号
    String mWXChatSN;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_register_by_wx_chat;
    }

    @Override
    protected RegisterByWXChatPresenter initPresenter() {
        return new RegisterByWXChatPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                TraceUtil.onEvent(RegisterByWXChatActivity.this, "wx_help_click");
                RouterUtil.toSubmitFeedback(RegisterByWXChatActivity.this, "WX_Register", "Register_Not_Recv_Mobile_Code");
            }

            @Override
            public void onClickImageMenu() {

            }
        });
        RxTextView.textChanges(mEtMobile).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrMobile = String.valueOf(charSequence);
                mTvGetSMSCheckCode.setEnabled(false);
                mTvNotGetCode.setEnabled(false);
                if (StringUtil.matchRegex(mStrMobile, HMConstants.REG_MOBILE)) {
                    mTvGetSMSCheckCode.setEnabled(true);
                    mTvNotGetCode.setEnabled(true);
                }
                checkValue();
            }
        });
        RxTextView.textChanges(mEtSMSCheckCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrSMSCheckCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        RxTextView.textChanges(mEtPassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrPsd = String.valueOf(charSequence);
                checkValue();
            }
        });
        showSoftKeyboard();
        mWXChatSN = getIntent().getStringExtra(EXTRA_KEY_WX_CHAT_SN);
        if (savedInstanceState != null) {
            mWXChatSN = savedInstanceState.getString(EXTRA_KEY_WX_CHAT_SN);
        }

        String str = mTvAgreement.getText().toString();
        SpannableString spanStr = new SpannableString(str);
        spanStr.setSpan(new UnderlineSpan(), 8, 12, 0);
        mTvAgreement.setText(spanStr);

        mEtSMSCheckCode.requestFocus();
        showUserAgreementDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_WX_CHAT_SN, mWXChatSN);
    }

    @OnClick({R2.id.btn_next, R2.id.tv_getSMSCheckCode, R2.id.tv_register_agreement, R2.id.tv_not_get_code})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.tv_getSMSCheckCode == id) {
            mPresenter.isMobileHaveBindWX(mStrMobile, true);
        } else if (R.id.btn_next == id) {
            TraceUtil.onEvent(this, "wx_bind_click");
            mPresenter.bindWX(mStrMobile, mStrSMSCheckCode, mStrPsd, mWXChatSN);
        } else if (R.id.tv_register_agreement == id) {
            TraceUtil.onEvent(this, "web_useragreement");
            showUserAgreementDialog();
        } else if (R.id.tv_not_get_code == id) {
            showBottomDialog();
        }
    }

    private void checkValue() {
        mBtnNext.setEnabled(false);
        if (StringUtil.matchRegex(mStrMobile, HMConstants.REG_MOBILE) && mStrSMSCheckCode.length() >= 6 && mStrPsd.length() >= 6) {
            mBtnNext.setEnabled(true);
        }
    }

    @Override
    public void warnMobileHaveBindWX(String desc) {
        String title = getString(R.string.loginmodule_register_by_wx_chat_dialog01_title);
        String msg = getString(R.string.loginmodule_register_by_wx_chat_dialog01_msg);
        String cancel = getString(R.string.base_cancel);
        String ok = getString(R.string.loginmodule_register_by_wx_chat_dialog01_ok);
        new HMAlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(cancel)
                .setPositiveButton(ok)
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        NavigationHelper.toMobileLogin(mContext, mStrMobile);
                    }

                    @Override
                    public void onNegClick() {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void warnMobileNotBindWX(String desc, final boolean isGetSmsCode) {
        String title = getString(R.string.loginmodule_register_by_wx_chat_dialog02_title);
        String msg = getString(R.string.loginmodule_register_by_wx_chat_dialog02_msg);
        String cancel = getString(R.string.base_cancel);
        String ok = getString(R.string.loginmodule_register_by_wx_chat_dialog02_ok);
        new HMAlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(cancel)
                .setPositiveButton(ok)
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        if (isGetSmsCode) {
                            mPresenter.getSmsCode(mStrMobile);
                        } else {
                            mPresenter.getVoiceCode(mStrMobile);
                        }
                    }

                    @Override
                    public void onNegClick() {

                    }
                })
                .create()
                .show();
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

    @Override
    public void startCountDown() {
        mTvGetSMSCheckCode.startCountDown();
    }

    private void showUserAgreementDialog() {
        UserAgreementDialog dialog = new UserAgreementDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnUserAgreementListener(new UserAgreementDialog.OnUserAgreementListener() {
            @Override
            public void onAgree() {
                mEtSMSCheckCode.requestFocus();
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
                                mPresenter.getVoiceCode(mStrMobile);
                            } else if (1 == i) {
                                RouterUtil.toSubmitFeedback(RegisterByWXChatActivity.this, "Mobile_Register_Not_Recv_Code", "Register_Not_Recv_Mobile_Code");
                            }
                        }
                    })
                    .create();
        }
        mBottomDialog.show();
    }

}
