package com.hm.iou.loginmodule.business.email;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 绑定邮箱
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class BindEmailPresenter extends MvpActivityPresenter<BindEmailContract.View> implements BindEmailContract.Present {


    //重置登录密码
    private final int PURPOSE_TYPE_BIND_EMAIL_BY_EMAIL = 4;

    public BindEmailPresenter(@NonNull Context context, @NonNull BindEmailContract.View view) {
        super(context, view);
    }

    @Override
    public void sendEmailCheckCode(String email) {
        mView.showLoadingView();
        LoginModuleApi.sendMessage(PURPOSE_TYPE_BIND_EMAIL_BY_EMAIL, email)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String str) {
                        mView.dismissLoadingView();
                        mView.toastMessage(R.string.uikit_get_check_code_success);
                        mView.startCountDown();
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void bindEmail(String email, String checkCode) {
        mView.showLoadingView();
        LoginModuleApi.bindEmail(email, checkCode)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String integer) {
                        mView.dismissLoadingView();
                        mView.toastMessage("邮箱绑定成功");
                        mView.closeCurrPage();
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
