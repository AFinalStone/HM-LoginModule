package com.hm.iou.loginmodule.business.password.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.InputMobileContract;
import com.hm.iou.loginmodule.business.password.presenter.InputMobilePresenter;
import com.hm.iou.uikit.ClearEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 输入手机号获取找回密码使用什么途径
 *
 * @author AFinalStone
 * @time 2018/4/23 上午11:10
 */
public class InputMobileActivity extends BaseActivity<InputMobilePresenter> implements InputMobileContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.et_phone)
    ClearEditText etPhone;
    String userMobile;

    @BindView(R2.id.btn_find)
    TextView btnFind;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_login_psd_input_phone;
    }

    @Override
    protected InputMobilePresenter initPresenter() {
        return new InputMobilePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        RxTextView.textChanges(etPhone).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                userMobile = String.valueOf(charSequence);
                btnFind.setEnabled(false);
                if (userMobile.length() == 11) {
                    btnFind.setEnabled(true);
                }
            }
        });
        userMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        etPhone.setText(userMobile);
    }

    @OnClick({R2.id.btn_find})
    public void onViewClicked(View view) {
        if (R.id.btn_find == view.getId()) {
            mPresenter.getResetPwsMethod(userMobile);
        }
    }


    //通过手机号找回密码
    private void toFindLoginPsdBySMS() {
//        Intent intent = new Intent(mContext, FindLoginPsdBySendCheckCodeActivity.class);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, userPhone);
//        startActivity(intent);
    }


    //通过邮箱实现重置登录密码
    public void toFindLoginPsdByEmailView(String email) {
//        Intent intent = new Intent(this, BindEmailAndResetPsdActivity.class);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, userPhone);
//        intent.putExtra(Constants.INTENT_EMAIL_NUMBER, email);
//        intent.putExtra(Constants.INTENT_BIND_EMAIL_AND_RESET_PSD, BindEmailAndResetPsdEnum.ResetPsdByEmail);
//        startActivity(intent);
    }

    //进行活体校验实现重置登录密码
    public void toFindPswByFaceView() {
//        Intent intent = new Intent(this, LivingCheckActivity.class);
//        intent.putExtra(Constants.INTENT_LIVINIG_CHECK_RESET_PSD_TYPE, LivingCheckResetPsdTypeEnum.LivingCheckResetLoginPsd);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, userPhone);
//        startActivityForResult(intent, Constants.REQUEST_TO_LIVING_CHECK);
    }

//    @Override
//    public void getResetPwsMethodSuccess(ResetPsdMethodBean resetPsdMethodBean) {
//        Logger.e(resetPsdMethodBean.toString());
//        ResetPswdMethodEnum resetPswdMethodEnum = resetPsdMethodBean.getMethod();
//        String field = resetPsdMethodBean.getField();
//        switch (resetPswdMethodEnum) {
//            case Real:
//                toFindPswByFaceView();
//                break;
//            case Mail:
//                toFindLoginPsdByEmailView(field);
//                break;
//            case Sms:
//                toFindLoginPsdBySMS();
//                break;
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //如果用户进行活体校验成功找密码，关闭当前页面
//        if (Constants.REQUEST_TO_LIVING_CHECK == requestCode) {
//            if (RESULT_OK == resultCode) {
//                finish();
//            }
//        }
//    }
}
