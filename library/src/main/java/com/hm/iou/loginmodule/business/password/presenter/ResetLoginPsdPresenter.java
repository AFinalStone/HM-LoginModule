package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.ResetLoginPsdContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class ResetLoginPsdPresenter extends BaseLoginModulePresenter<ResetLoginPsdContract.View> implements ResetLoginPsdContract.Presenter {


    public ResetLoginPsdPresenter(@NonNull Context context, @NonNull ResetLoginPsdContract.View view) {
        super(context, view);
    }

    @Override
    public void resetLoginPsdBySMS(final String mobile, String checkCode, final String newPswd) {
        mView.showLoadingView();
        LoginModuleApi.resetLoginPsdBySMS(mobile, checkCode, newPswd)
                .compose(getProvider().<BaseResponse<Integer>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Integer>handleResponse())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer integer) {
                        mView.dismissLoadingView();
                        mobileLogin(mobile, newPswd);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void resetLoginPsdByFace(final String mobile, String idCardNum, String faceCheckSn, final String newPsd) {
        mView.showLoadingView();
        LoginModuleApi.resetLoginPsdByFace(mobile, idCardNum, faceCheckSn, newPsd)
                .compose(getProvider().<BaseResponse<Integer>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Integer>handleResponse())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer integer) {
                        mView.dismissLoadingView();
                        mobileLogin(mobile, newPsd);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }


    @Override
    public void resetLoginPsdByEMail(final String mobile, String email, String checkCode, String sn, final String newPsd) {
        mView.showLoadingView();
        LoginModuleApi.resetLoginPsdByEmail(email, checkCode, sn, newPsd)
                .compose(getProvider().<BaseResponse<Integer>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Integer>handleResponse())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer integer) {
                        mView.dismissLoadingView();
                        mobileLogin(mobile, newPsd);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }


    @Override
    public void mobileLogin(final String mobile, String loginPsd) {
        mView.showLoadingView();
        LoginModuleApi.mobileLogin(mobile, loginPsd)
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        mView.dismissLoadingView();
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        NavigationHelper.toLoginLoading(mContext);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                        NavigationHelper.toMobileLogin(mContext, mobile);
                    }
                });
    }
}
