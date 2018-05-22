package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 输入手机号，获取找回密码的途径
 *
 * @author syl
 * @time 2018/5/17 下午1:54
 */
public interface FindByInputMobileContract {

    interface View extends BaseContract.BaseView {


    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 获取找回密码的方式
         *
         * @param mobile
         */
        void getResetPsdMethod(String mobile);
    }


}
