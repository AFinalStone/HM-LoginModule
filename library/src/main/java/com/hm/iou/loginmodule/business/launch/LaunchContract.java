package com.hm.iou.loginmodule.business.launch;

/**
 * 启动页
 *
 * @author syl
 * @time 2018/5/31 下午2:59
 */
public interface LaunchContract {

    interface View {

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

        void closeCurrPage();
    }

    interface Presenter {

        /**
         * 初始化，获取广告
         */
        void init();

        /**
         * 暂停倒计时
         */
        void pauseCountDown();

        /**
         * 重新开始倒计时
         */
        void resumeCountDown();

        /**
         * 进入广告详情页面
         *
         * @param linkUrl
         */
        void toAdDetail(String linkUrl);

        /**
         * 跳转到首页
         */
        void toMain();

        void onDestroy();
    }
}
