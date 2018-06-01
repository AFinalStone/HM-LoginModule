package com.hm.iou.loginmodule.business.launch;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 启动页
 *
 * @author syl
 * @time 2018/5/31 下午2:59
 */
public interface LaunchContract {

    interface View extends BaseContract.BaseView {

        /**
         * 显示广告
         *
         * @param imageUrl 广告图片地址
         * @param linkUrl  广告详情地址
         */
        void showAdvertisement(String imageUrl, String linkUrl);

        /**
         * 设置倒计时按钮的文字描述
         *
         * @param desc
         */
        void setJumpBtnText(String desc);

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 初始化，获取广告
         */
        void init();

        /**
         * 校验用户是否成功登陆过
         */
        void checkUserHaveLogin();

    }
}
