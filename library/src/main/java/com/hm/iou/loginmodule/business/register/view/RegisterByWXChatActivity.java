package com.hm.iou.loginmodule.business.register.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.loginmodule.business.register.presenter.RegisterByWXChatPresenter;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.ClearEditText;
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

    public static final String EXTRA_KEY_WXCHAT_SN = "wxchat_sn";

    private static final String REGEXP_PHOTO_NUMBER = "^[1][0-9]{10}$";

    @BindView(R2.id.topbar)
    HMTopBarView mTopbar;

    @BindView(R2.id.et_mobile)
    ClearEditText mEtMobile;
    String mUserMobile = "";

    @BindView(R2.id.et_smsCheckCode)
    EditText mEtSMSCheckCode;
    String mStrSMSCheckCode = "";

    @BindView(R2.id.et_password)
    EditText mEtPassword;
    String mStrPsd = "";

    @BindView(R2.id.tv_bindMobile)
    TextView mTvBindMobile;

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
        mTopbar.setOnBackClickListener(new HMTopBarView.OnTopBarBackClickListener() {
            @Override
            public void onClickBack() {
                finish();
            }
        });
        RxTextView.textChanges(mEtMobile).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mUserMobile = String.valueOf(charSequence);
                if (StringUtil.matchRegex(mUserMobile, REGEXP_PHOTO_NUMBER)) {
                    String strGmEtCode = mTvBindMobile.getText().toString();
                } else {
                    mTvBindMobile.setEnabled(false);
                }
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

        RxTextView.textChanges(mEtSMSCheckCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mStrSMSCheckCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        showSoftKeyboard();
        mWXChatSN = getIntent().getStringExtra(EXTRA_KEY_WXCHAT_SN);
    }


    @OnClick({R2.id.tv_bindMobile, R2.id.btn_getSMSCheckCode})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.tv_bindMobile == id) {
            mPresenter.bindWX(mUserMobile, mStrSMSCheckCode, mStrPsd, mWXChatSN);
        } else if (R.id.btn_getSMSCheckCode == id) {
            mPresenter.isBindWX(mUserMobile);
        }
    }

    private void checkValue() {
        mTvBindMobile.setEnabled(false);
        if (mUserMobile.length() > 0 && mStrSMSCheckCode.length() > 0 && mStrPsd.length() >= 6) {
            mTvBindMobile.setEnabled(true);
        }
    }

    private void showDialogMobileHaveBindWX() {
        String title = getString(R.string.bind_mobile_dialog01_title);
        String msg = getString(R.string.bind_mobile_dialog01_msg);
        String cancel = getString(R.string.base_cancel);
        String ok = getString(R.string.bind_mobile_dialog01_ok);
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
                        NavigationHelper.toMobileLogin(mContext, mUserMobile);
                        finish();
                    }
                }).show();
    }

    private void showDialogMobileHaveExist() {
        String title = getString(R.string.bind_mobile_dialog02_title);
        String msg = getString(R.string.bind_mobile_dialog02_msg);
        String cancel = getString(R.string.base_cancel);
        String ok = getString(R.string.bind_mobile_dialog02_ok);
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
                        mPresenter.getSmsCode(mUserMobile);
                    }
                }).show();
    }


    @Override
    public void warnMobileHaveBindWX(String desc) {

    }

    @Override
    public void warnDialogMobileNotBindWX(String desc) {

    }

    @Override
    public void showTimeCountDown(String currentDesc) {

    }

    @Override
    public void closeTimeCountDown() {

    }
}
