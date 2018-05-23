package com.hm.iou.loginmodule.business.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.login.MobileLoginContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.Md5Util;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class MobileLoginPresenter extends BaseLoginModulePresenter<MobileLoginContract.View> implements MobileLoginContract.Presenter {


    public MobileLoginPresenter(@NonNull Context context, @NonNull MobileLoginContract.View view) {
        super(context, view);
    }


    @Override
    public void mobileLogin(String mobile, String loginPsd) {
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
                        NavigationHelper.toLoginLoading(mContext,false);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
