package com.hm.iou.loginmodule.business.register.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.constants.HMConstants;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByWXChatPresenter;
import com.hm.iou.router.Router;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
import com.hm.iou.uikit.HMCountDownTextView;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.dialog.IOSAlertDialog;
import com.jakewharton.rxbinding2.widget.RxTextView;

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
    @BindView(R2.id.et_password)
    EditText mEtPassword;
    @BindView(R2.id.btn_next)
    Button mBtnNext;

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
                Router.getInstance()
                        .buildWithUrl("hmiou://m.54jietiao.com/login/customer_service")
                        .navigation(mContext);
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
                if (StringUtil.matchRegex(mStrMobile, HMConstants.REG_MOBILE)) {
                    mTvGetSMSCheckCode.setEnabled(true);
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_WX_CHAT_SN, mWXChatSN);
    }

    @OnClick({R2.id.btn_next, R2.id.tv_getSMSCheckCode, R2.id.tv_agreement01, R2.id.tv_agreement02})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.tv_getSMSCheckCode == id) {
            mPresenter.isMobileHaveBindWX(mStrMobile);
        } else if (R.id.btn_next == id) {
            TraceUtil.onEvent(this, "wx_bind_click");
            mPresenter.bindWX(mStrMobile, mStrSMSCheckCode, mStrPsd, mWXChatSN);
        } else if (R.id.tv_agreement01 == id) {
            TraceUtil.onEvent(this, "web_useragreement");
            NavigationHelper.ToRegisterAndUseAgreement(mContext);
        } else if (R.id.tv_agreement02 == id) {
            TraceUtil.onEvent(this, "web_privacy");
            NavigationHelper.toPrivateAgreement(mContext);
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
        new IOSAlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NavigationHelper.toMobileLogin(mContext, mStrMobile);
                    }
                }).show();
    }

    @Override
    public void warnMobileNotBindWX(String desc) {
        String title = getString(R.string.loginmodule_register_by_wx_chat_dialog02_title);
        String msg = getString(R.string.loginmodule_register_by_wx_chat_dialog02_msg);
        String cancel = getString(R.string.base_cancel);
        String ok = getString(R.string.loginmodule_register_by_wx_chat_dialog02_ok);
        new IOSAlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPresenter.getSmsCode(mStrMobile);
                    }
                }).show();
    }

    @Override
    public void startCountDown() {
        mTvGetSMSCheckCode.startCountDown();
    }

}
