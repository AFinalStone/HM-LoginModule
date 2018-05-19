package com.hm.iou.loginmodule.business.login;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 手机登录的第一步，输入手机号
 *
 * @author syl
 * @time 2018/5/17 下午1:57
 */
public interface InputMobileContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {
        void checkAccountIsExist(String phone);
    }
}
