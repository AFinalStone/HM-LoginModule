package com.hm.iou.loginmodule.business.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.login.InputMobileContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

import static com.hm.iou.loginmodule.LoginModuleConstants.ERR_CODE_ACCOUNT_CLOSED;

/**
 * 1.通过微信号进行注册并绑定手机
 * 2.如果选择的手机号已经存在则进行微信绑定操作
 *
 * @author syl
 * @time 2018/5/17 下午5:26
 */
public class InputMobilePresenter extends BaseLoginModulePresenter<InputMobileContract.View> implements InputMobileContract.Presenter {

    public InputMobilePresenter(@NonNull Context context, @NonNull InputMobileContract.View view) {
        super(context, view);
    }


    @Override
    public void checkAccountIsExist(final String mobile) {
        mView.showLoadingView();
        LoginModuleApi.isAccountExist(mobile)
                .compose(getProvider().<BaseResponse<Boolean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Boolean>handleResponse())
                .subscribeWith(new CommSubscriber<Boolean>(mView) {
                    @Override
                    public void handleResult(Boolean flag) {
                        mView.dismissLoadingView();
                        if (flag) {
                            NavigationHelper.toMobileLogin(mContext, mobile);
                        } else {
                            NavigationHelper.toRegisterByMobile(mContext, mobile);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        TraceUtil.onEvent(mContext, "mob_next_err");

                        if (!TextUtils.isEmpty(code)) {
                            if (ERR_CODE_ACCOUNT_CLOSED.equals(code)) {
                                NavigationHelper.toWarnCanNotRegister(mContext);
                            } else {
                                mView.toastErrorMessage(msg);
                            }
                        }
                    }

                    @Override
                    public boolean isShowCommError() {
                        return true;
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });
    }
}
