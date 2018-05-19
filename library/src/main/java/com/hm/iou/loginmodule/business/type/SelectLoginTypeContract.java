package com.hm.iou.loginmodule.business.type;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 选择登录方式
 *
 * @author syl
 * @time 2018/5/17 下午4:35
 */
public interface SelectLoginTypeContract {

    interface View extends BaseContract.BaseView {

        /**
         * 隐藏微信登录的方式
         */
        void hideButtonForLoginByWx();
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 是否存在微信
         */
        void isExistsWx();

        /**
         * 根据微信code判断当前微信是否绑定过手机号
         *
         * @param code 微信code
         */
        void isWXExist(String code);


        /**
         * 根据之前获得的流水号进行微信登录
         *
         * @param wxSN 判断微信是否绑定过手机号的交易流水号
         */
        void wxLogin(String wxSN);

        /**
         * 获取当前手机的微信code
         */
        void getWxCode();

    }
}