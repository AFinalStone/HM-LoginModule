package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.login.MobileLoginContract;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindBySMSPresenter extends BaseLoginModulePresenter<FindBySMSContract.View> implements FindBySMSContract.Present {


    public FindBySMSPresenter(@NonNull Context context, @NonNull FindBySMSContract.View view) {
        super(context, view);
    }

    @Override
    public void sendResetPsdCheckCodeSMS(String userMobile) {

    }
}
