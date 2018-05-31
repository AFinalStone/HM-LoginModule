package com.hm.iou.loginmodule.business.guide;


import com.hm.iou.base.mvp.BaseContract;

import java.util.List;

/**
 * 启动页
 *
 * @author syl
 * @time 2018/5/31 下午2:59
 */
public interface GuideContract {

    interface View extends BaseContract.BaseView {
        /**
         * 显示viewPager
         */
        void showViewPager(List<Integer> list);
    }

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 初始化
         */
        void init();

    }
}
