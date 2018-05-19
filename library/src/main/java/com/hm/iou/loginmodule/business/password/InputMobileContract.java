package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 输入手机号，获取找回密码的途径
 *
 * @author AFinalStone
 * @time 2018/5/17 下午1:54
 */
public interface InputMobileContract {

    /**
     * 输入手机号，判断找回密码的方式
     **/
    interface View extends BaseContract.BaseView {


    }

    interface Presenter extends BaseContract.BasePresenter {

        //获取找回密码的方式
        void getResetPwsMethod(String userPhone);
    }


}
