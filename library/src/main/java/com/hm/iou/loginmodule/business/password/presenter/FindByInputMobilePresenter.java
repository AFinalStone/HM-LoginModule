package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.ResetPsdMethodBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByInputMobileContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 通过手机号进行登录
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByInputMobilePresenter extends BaseLoginModulePresenter<FindByInputMobileContract.View> implements FindByInputMobileContract.Presenter {

    private final int RESET_PSD_BY_METHOD_MAIL = 1;
    private final int RESET_PSD_BY_METHOD_REAL = 2;
    private final int RESET_PSD_BY_METHOD_SMS = 3;

    public FindByInputMobilePresenter(@NonNull Context context, @NonNull FindByInputMobileContract.View view) {
        super(context, view);
    }

    @Override
    public void getResetPsdMethod(final String mobile) {
        mView.showLoadingView();
        LoginModuleApi.getResetPswdMethod(mobile)
                .compose(getProvider().<BaseResponse<ResetPsdMethodBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<ResetPsdMethodBean>handleResponse())
                .subscribeWith(new CommSubscriber<ResetPsdMethodBean>(mView) {
                    @Override
                    public void handleResult(ResetPsdMethodBean resetPsdMethodBean) {
                        mView.dismissLoadingView();
                        int method = resetPsdMethodBean.getMethod();
                        if (RESET_PSD_BY_METHOD_MAIL == method) {
                            //通过邮箱验证码实现重置登录密码
                            String email = resetPsdMethodBean.getField();
                            NavigationHelper.toFindByEmail(mContext, mobile, email);
                        } else if (RESET_PSD_BY_METHOD_REAL == method) {
                            //通过活体校验重置登录密码
                            String idCard = resetPsdMethodBean.getField();
                            NavigationHelper.toFindByFace(mContext, idCard);
                        } else if (RESET_PSD_BY_METHOD_SMS == method) {
                            //通过手机号验证码重置登录密码
                            String idCard = resetPsdMethodBean.getField();
                            NavigationHelper.toFindByFace(mContext, idCard);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }
}
