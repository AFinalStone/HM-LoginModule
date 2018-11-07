package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByFaceContract;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.event.FaceCheckAgainEvent;
import com.hm.iou.sharedata.event.LoginSuccEvent;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 这个页面是在登录模块LoginModule发起使用的，
 * 获取活体校验流水号，然后到下一步进行登录密码找回操作
 *
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByFacePresenter extends BaseLoginModulePresenter<FindByFaceContract.View> implements FindByFaceContract.Present {

    //活体校验失败，今日次数已达上限
    private static final String ERROR_CODE_FACE_CHECK_TODAY_NOTIME = "203005";
    //活体校验失败，retMsg中携带剩余次数
    private static final String ERROR_CODE_FACE_CHECK_FAILED = "203009";

    public FindByFacePresenter(@NonNull Context context, @NonNull FindByFaceContract.View view) {
        super(context, view);
    }

    @Override
    public void checkIdCardWithoutLogin(String mobile, String idCardNum) {
        mView.showLoadingView();
        LoginModuleApi.checkIdCardWithoutLogin(mobile, idCardNum)
                .compose(getProvider().<BaseResponse<Boolean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Boolean>handleResponse())
                .subscribeWith(new CommSubscriber<Boolean>(mView) {
                    @Override
                    public void handleResult(Boolean aBoolean) {
                        mView.dismissLoadingView();
                        if (aBoolean) {
                            mView.toScanFace();
                        } else {
                            mView.warnCheckFailed();
                            mView.toastMessage(R.string.loginmodule_check_id_card_failed);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                        mView.warnCheckFailed();
                    }
                });
    }

    @Override
    public void faceCheckWithoutLogin(final String mobile, final String idCardNum, String imagePath) {
        mView.showLoadingView();
        LoginModuleApi.faceCheckWithoutLogin(mContext, mobile, idCardNum, imagePath)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String faceCheckSN) {
                        mView.dismissLoadingView();
                        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/reset_login_psd")
                                .withString("reset_psd_type", "face")
                                .withString("mobile", mobile)
                                .withString("user_id_card", idCardNum)
                                .withString("face_check_sn", faceCheckSN)
                                .navigation(mContext);
                        mView.closeCurrPage();
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (ERROR_CODE_FACE_CHECK_FAILED.equals(code)) {
                            Router.getInstance()
                                    .buildWithUrl("hmiou://m.54jietiao.com/facecheck/facecheckfailed")
                                    .withString("face_check_remainder_number", msg)
                                    .navigation(mContext);
                        } else if (ERROR_CODE_FACE_CHECK_TODAY_NOTIME.equals(code)) {
                            Router.getInstance()
                                    .buildWithUrl("hmiou://m.54jietiao.com/facecheck/facecheckfailed")
                                    .withString("face_check_remainder_number", "0")
                                    .navigation(mContext);
                        } else {
                            mView.toastMessage(msg);
                            mView.warnCheckFailed();
                        }

                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });
    }

    /**
     * 用户活体校验失败，点击重试之后，重新进行活体校验
     *
     * @param faceCheckAgainEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusFaceCheckAgain(FaceCheckAgainEvent faceCheckAgainEvent) {
        mView.toScanFace();
    }

    /**
     * 用户成功登陆，关闭当前页面
     *
     * @param loginSucceedEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenLoginSuccess(LoginSuccEvent loginSucceedEvent) {
        mView.closeCurrPage();
    }
}
