package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 通过手机号进行注册
 *
 * @author syl
 * @time 2018/5/19 下午5:21
 */
public class RegisterByMobilePresenter extends BaseLoginModulePresenter<RegisterByMobileContract.View> implements RegisterByMobileContract.Presenter {

    //注册
    private final int PURPOSE_TYPE_REGISTER_BY_SMS = 1;

    public RegisterByMobilePresenter(@NonNull Context context, @NonNull RegisterByMobileContract.View view) {
        super(context, view);
    }

    @Override
    public void getSMSCode(String mobile) {
        mView.showLoadingView();
        LoginModuleApi.sendMessage(PURPOSE_TYPE_REGISTER_BY_SMS, mobile)
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

//        LoginModuleApi.sendSmsCheckCode(mobile)
//                .compose(getProvider().<BaseResponse<Boolean>>bindUntilEvent(ActivityEvent.DESTROY))
//                .map(RxUtil.<Boolean>handleResponse())
//                .subscribeWith(new CommSubscriber<Boolean>(mView) {
//                    @Override
//                    public void handleResult(Boolean flag) {
//                        mView.dismissLoadingView();
//                        if (flag) {
//                            mView.toastMessage(R.string.uikit_get_check_code_success);
//                        } else {
//                            mView.toastMessage(R.string.uikit_get_check_code_failed);
//                        }
//                    }
//
//                    @Override
//                    public void handleException(Throwable throwable, String s, String s1) {
//                        mView.dismissLoadingView();
//                    }
//                });
    }

    @Override
    public void registerByMobileAndLogin(final String mobile, String loginPsd, String smsCheckCode) {
        mView.showLoadingView();
        LoginModuleApi.mobileRegLogin(mobile, loginPsd, smsCheckCode)
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        mView.dismissLoadingView();
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        NavigationHelper.toLoginLoading(mContext,"hmiou://m.54jietiao.com/login/mobilelogin?mobile="+mobile);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
