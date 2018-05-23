package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByEmailContract;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByEmailPresenter extends BaseLoginModulePresenter<FindByEmailContract.View> implements FindByEmailContract.Present {


    //重置登录密码
    private final int PURPOSE_TYPE_RESET_LOGIN_PSD_BY_EMAIL = 6;

    public FindByEmailPresenter(@NonNull Context context, @NonNull FindByEmailContract.View view) {
        super(context, view);
    }

    @Override
    public void sendEmailCheckCode(String email) {
        mView.showLoadingView();
        LoginModuleApi.sendMessage(PURPOSE_TYPE_RESET_LOGIN_PSD_BY_EMAIL, email)
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
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void compareEmailCheckCode(final String mobile, final String email, final String checkCode) {
        mView.showLoadingView();
        LoginModuleApi.compareEmailCheckCode(email, checkCode)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String sn) {
                        mView.dismissLoadingView();
                        NavigationHelper.toResetLoginPsdByEmail(mContext, mobile, email, checkCode, sn);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
