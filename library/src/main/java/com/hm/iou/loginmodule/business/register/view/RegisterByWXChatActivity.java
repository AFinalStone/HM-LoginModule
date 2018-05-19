package com.hm.iou.loginmodule.business.register.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
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

    @BindView(R2.id.et_code)
    EditText mEtCode;
    String mUserCode = "";

    @BindView(R2.id.et_password)
    EditText mEtPassword;
    String mUserPsd = "";

    @BindView(R2.id.iv_eye)
    ImageView mIvEye;
    boolean isEyeOpen = true;

    @BindView(R2.id.btn_bindMobile)
    TextView mBtnBindMobile;

    @BindView(R2.id.btn_getCode)
    TextView mBtnGettCode;

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
                    String strGmEtCode = mBtnGettCode.getText().toString();
                    if (getString(R.string.uikit_btn_get_check_code).equals(strGmEtCode)) {
                        mBtnGettCode.setEnabled(true);
                    }
                } else {
                    mBtnGettCode.setEnabled(false);
                }
                checkValue();
            }
        });

        RxTextView.textChanges(mEtPassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mUserPsd = String.valueOf(charSequence);
                checkValue();
            }
        });

        RxTextView.textChanges(mEtCode).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                mUserCode = String.valueOf(charSequence);
                checkValue();
            }
        });
        showSoftKeyboard();
        mWXChatSN = getIntent().getStringExtra(EXTRA_KEY_WXCHAT_SN);
    }


    @OnClick({R2.id.iv_eye, R2.id.btn_bindMobile, R2.id.btn_getCode})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.iv_eye == id) {
            changePassword();
        } else if (R.id.btn_bindMobile == id) {
            mPresenter.bindWX(mUserMobile, mUserCode, mUserPsd, mWXChatSN);
        } else if (R.id.btn_getCode == id) {
            mPresenter.isBindWX(mUserMobile);
        }
    }

    private void checkValue() {
        mBtnBindMobile.setEnabled(false);
        if (mUserMobile.length() > 0 && mUserCode.length() > 0 && mUserPsd.length() >= 6) {
            mBtnBindMobile.setEnabled(true);
        }
    }

    private void changePassword() {
        if (isEyeOpen) {
            isEyeOpen = false;
            mIvEye.setImageResource(R.mipmap.uikit_icon_password_eye_close);
            // 显示为密码
            mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = mEtPassword.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            isEyeOpen = true;
            mIvEye.setImageResource(R.mipmap.uikit_icon_password_eye_open);

            // 显示为普通文本
            mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = mEtPassword.getText();
            Selection.setSelection(etable, etable.length());
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
