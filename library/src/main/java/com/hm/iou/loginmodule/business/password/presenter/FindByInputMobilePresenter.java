package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.GetResetPsdMethodRespBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByInputMobileContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByInputMobilePresenter extends BaseLoginModulePresenter<FindByInputMobileContract.View> implements FindByInputMobileContract.Presenter {

    //Mail(1:邮件方式), Real(2:人脸识别方式), Sms(3:短信方式)
    private final int RESET_PSD_METHOD_BY_MAIL = 1;
    private final int RESET_PSD_METHOD_BY_FACE = 2;
    private final int RESET_PSD_METHOD_BY_SMS = 3;

    public FindByInputMobilePresenter(@NonNull Context context, @NonNull FindByInputMobileContract.View view) {
        super(context, view);
    }

    @Override
    public void getResetPsdMethod(final String mobile) {
        mView.showLoadingView();
        LoginModuleApi.getResetPswdMethod(mobile)
                .compose(getProvider().<BaseResponse<GetResetPsdMethodRespBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<GetResetPsdMethodRespBean>handleResponse())
                .subscribeWith(new CommSubscriber<GetResetPsdMethodRespBean>(mView) {
                    @Override
                    public void handleResult(GetResetPsdMethodRespBean getResetPsdMethodRespBean) {
                        mView.dismissLoadingView();
                        int method = getResetPsdMethodRespBean.getMethod();
                        if (RESET_PSD_METHOD_BY_MAIL == method) {
                            //通过邮箱验证码实现重置登录密码
                            String email = getResetPsdMethodRespBean.getField();
                            NavigationHelper.toFindByEmail(mContext, mobile, email);
                        } else if (RESET_PSD_METHOD_BY_FACE == method) {
                            //通过活体校验重置登录密码
                            String userName = getResetPsdMethodRespBean.getField();
                            NavigationHelper.toFindByFace(mContext, mobile, userName);
                            mView.closeCurrPage();
                        } else if (RESET_PSD_METHOD_BY_SMS == method) {
                            //通过手机号验证码重置登录密码
                            NavigationHelper.toFindBySMS(mContext, mobile);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
