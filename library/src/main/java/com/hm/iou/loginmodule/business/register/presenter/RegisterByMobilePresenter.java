package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.register.RegisterByMobileContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import static com.hm.iou.loginmodule.LoginModuleConstants.ERR_CODE_ACCOUNT_CLOSED;

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
        final String mobileLastNum = mobile.substring(mobile.length() - 4, mobile.length());
        LoginModuleApi.sendMessage(PURPOSE_TYPE_REGISTER_BY_SMS, mobile)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String str) {
                        mView.dismissLoadingView();
                        mView.toastMessage("验证码已成功发送至尾号" + mobileLastNum + "手机号上");
                        mView.startCountDown();
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (!TextUtils.isEmpty(code)) {
                            if (ERR_CODE_ACCOUNT_CLOSED.equals(code)) {
                                NavigationHelper.toWarnCanNotRegister(mContext);
                            } else {
                                mView.toastErrorMessage(msg);
                            }
                        }
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });
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
                        NavigationHelper.toTagStatusJudgePage(mContext, "hmiou://m.54jietiao.com/login/mobilelogin?mobile=" + mobile);
                        TraceUtil.onEvent(mContext, "mob_reg_succ");
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (!TextUtils.isEmpty(code)) {
                            if (ERR_CODE_ACCOUNT_CLOSED.equals(code)) {
                                NavigationHelper.toWarnCanNotRegister(mContext);
                            } else {
                                mView.toastErrorMessage(msg);
                            }
                        }
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });
    }
}
