package com.hm.iou.loginmodule.business.register;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by AFinalStone on 2017年12月4日 17:23:10
 */
public interface RegisterByMobileContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        void getCode(String phoneNumber);

        void registerAndLogin(String loginName, String queryPswd, String checkCode);
    }
}
