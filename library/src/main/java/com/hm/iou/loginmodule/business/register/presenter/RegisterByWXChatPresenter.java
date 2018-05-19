package com.hm.iou.loginmodule.business.register.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.register.RegisterByWXChatContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 1.通过微信号进行注册并绑定手机
 * 2.如果选择的手机号已经存在则进行微信绑定操作
 *
 * @author syl
 * @time 2018/5/17 下午5:26
 */
public class RegisterByWXChatPresenter extends MvpActivityPresenter<RegisterByWXChatContract.View> implements RegisterByWXChatContract.Presenter {

    //手机号不存在
    private static final int MOBILE_NOT_EXIST = 7004;
    //手机号存在但是没有绑定微信
    private static final int MOBILE_NOT_BIND_WX = 7011;
    //手机号存在且已经绑定微信
    private static final int MOBILE_HAVE_BIND_WX = 7010;


    public RegisterByWXChatPresenter(@NonNull Context context, @NonNull RegisterByWXChatContract.View view) {
        super(context, view);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getSmsCode(String mobile) {

    }

    @Override
    public void isBindWX(String mobile) {
        LoginModuleApi.isBindWX(mobile)
                .compose(getProvider().<BaseResponse<Integer>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Integer>handleResponse())
                .subscribeWith(new CommSubscriber<Integer>(mView) {
                    @Override
                    public void handleResult(Integer type) {
                        if (MOBILE_HAVE_BIND_WX == type) {

                        } else if (MOBILE_NOT_EXIST == type) {

                        } else if (MOBILE_NOT_BIND_WX == type) {

                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }
                });
    }

    @Override
    public void bindWX(String mobile, String checkCode, String loginPsd, String wxSn) {

    }


}
