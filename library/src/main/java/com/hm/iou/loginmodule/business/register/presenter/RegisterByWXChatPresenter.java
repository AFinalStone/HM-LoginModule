package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.IsBindWXRespBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import static com.hm.iou.loginmodule.LoginModuleConstants.ERR_CODE_ACCOUNT_CLOSED;

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
    private static final int PURPOSE_TYPE_BIND_WX_BY_SMS = 2;
    //上次发送语音验证码的时间
    private long mLastSendVoiceCodeTime = -1;

    public RegisterByWXChatPresenter(@NonNull Context context, @NonNull RegisterByWXChatContract.View view) {
        super(context, view);
    }

    @Override
    public void getSmsCode(String mobile) {
        mView.showLoadingView();
        final String mobileLastNum = mobile.substring(mobile.length() - 4, mobile.length());
        LoginModuleApi.sendMessage(PURPOSE_TYPE_BIND_WX_BY_SMS, mobile)
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
    public void getVoiceCode(String mobile) {
        if (mLastSendVoiceCodeTime != -1 && System.currentTimeMillis() - mLastSendVoiceCodeTime < 30000) {
            mView.showVoiceTipDialog();
            return;
        }
        LoginModuleApi.sendVoiceCode(PURPOSE_TYPE_BIND_WX_BY_SMS, mobile)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String str) {
                        mLastSendVoiceCodeTime = System.currentTimeMillis();
                        mView.dismissLoadingView();
                        mView.toastMessage("语音验证码获取成功，请注意接听");
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
    public void isMobileHaveBindWX(final String mobile, final boolean isGetSmsCode) {
        mView.showLoadingView();
        LoginModuleApi.isBindWX(mobile)
                .compose(getProvider().<BaseResponse<IsBindWXRespBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IsBindWXRespBean>handleResponse())
                .subscribeWith(new CommSubscriber<IsBindWXRespBean>(mView) {
                    @Override
                    public void handleResult(IsBindWXRespBean respBean) {
                        int type = respBean.getCount();
                        if (MOBILE_HAVE_BIND_WX == type) {
                            TraceUtil.onEvent(mContext, "wx_mob_bound_refuse");
                            mView.dismissLoadingView();
                            mView.warnMobileHaveBindWX(null);
                        } else if (MOBILE_NOT_BIND_WX == type) {
                            TraceUtil.onEvent(mContext, "wx_mob_bound_continue");
                            mView.dismissLoadingView();
                            mView.warnMobileNotBindWX(null, isGetSmsCode);
                        } else if (MOBILE_NOT_EXIST == type) {
                            TraceUtil.onEvent(mContext, "wx_mob_bound_succ");
                            getSmsCode(mobile);
                        }
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
                        NavigationHelper.toTagStatusJudgePage(mContext, "hmiou://m.54jietiao.com/login/selecttype");
                        TraceUtil.onEvent(mContext, "wx_bind_click_succ_count");
                        TraceUtil.onEvent(mContext, "wx_bind_succ_count");
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        TraceUtil.onEvent(mContext, "wx_bind_fail_count");

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
