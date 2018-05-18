package com.hm.iou.loginmodule.business.login;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by AFinalStone on 2016/8/2.
 */
public interface MobileLoginContract {

    interface View extends BaseContract.BaseView {

        void showResult(String info);

        void jumpFindPswView();

        void jumpMainView();
    }

    interface Presenter extends BaseContract.BasePresenter {

        void login(String userPhone, String userPassword);

    }
}
