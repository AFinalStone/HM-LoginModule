package com.hm.iou.loginmodule.business.register;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 微信注册或者绑定
 * @author syl
 * @time 2018/5/17 下午5:53
 */
public interface RegisterByWXChatContract {

    interface View extends BaseContract.BaseView {

        /**
         * 提醒账号已经存在且已经绑定微信
         *
         * @param desc 提示信息
         */
        void warnMobileHaveBindWX(String desc);


        /**
         * 提醒账号存在但是没有绑定微信
         *
         * @param desc 提示信息
         */
        void warnDialogMobileNotBindWX(String desc);

        /**
         * 显示当前倒计时时间
         *
         * @param currentDesc
         */
        void showTimeCountDown(String currentDesc);

        /**
         * 关闭倒计时
         */
        void closeTimeCountDown();
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 获取手机验证码，为后续的注册或者绑定做准备
         *
         * @param mobile
         */
        void getSmsCode(String mobile);

        /**
         * 查询手机号是否绑定微信
         *
         * @param mobile
         */
        void isBindWX(String mobile);

        /**
         * 通过微信进行绑定或者注册
         *
         * @param mobile    手机号
         * @param checkCode 短信验证码
         * @param loginPsd  登录密码
         * @param wxSn      判断微信是否存在的交易流水号
         */
        void bindWX(String mobile, String checkCode, String loginPsd, String wxSn);

    }
}
