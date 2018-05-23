package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.IsBindWXRespBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 1.通过微信号进行注册并绑定手机
 * 2.如果选择的手机号已经存在则进行微信绑定操作
 *
 * @author syl
 * @time 2018/5/17 下午5:26
 */
public class RegisterByWXChatPresenter extends BaseLoginModulePresenter<RegisterByWXChatContract.View> implements RegisterByWXChatContract.Presenter {

    //手机号不存在
    private static final int MOBILE_NOT_EXIST = -1;
    //手机号存在但是没有绑定微信
    private static final int MOBILE_NOT_BIND_WX = 0;
    //手机号存在且已经绑定微信
    private static final int MOBILE_HAVE_BIND_WX = 1;
    //绑定微信
    private final int PURPOSE_TYPE_BIND_WX_BY_SMS = 2;


    public RegisterByWXChatPresenter(@NonNull Context context, @NonNull RegisterByWXChatContract.View view) {
        super(context, view);
    }

    @Override
    public void getSmsCode(String mobile) {
        mView.showLoadingView();
        LoginModuleApi.sendMessage(PURPOSE_TYPE_BIND_WX_BY_SMS, mobile)
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
    public void isMobileHaveBindWX(final String mobile) {
        mView.showLoadingView();
        LoginModuleApi.isBindWX(mobile)
                .compose(getProvider().<BaseResponse<IsBindWXRespBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IsBindWXRespBean>handleResponse())
                .subscribeWith(new CommSubscriber<IsBindWXRespBean>(mView) {
                    @Override
                    public void handleResult(IsBindWXRespBean respBean) {
                        int type = respBean.getCount();
                        if (MOBILE_HAVE_BIND_WX == type) {
                            mView.dismissLoadingView();
                            mView.warnMobileHaveBindWX(null);
                        } else if (MOBILE_NOT_BIND_WX == type) {
                            mView.dismissLoadingView();
                            mView.warnMobileNotBindWX(null);
                        } else if (MOBILE_NOT_EXIST == type) {
                            getSmsCode(mobile);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void bindWX(String mobile, String checkCode, String loginPsd, String wxSn) {
        mView.showLoadingView();
        LoginModuleApi.bindWX(mobile, checkCode, loginPsd, wxSn)
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        mView.dismissLoadingView();
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        NavigationHelper.toLoginLoading(mContext, false);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }


}
