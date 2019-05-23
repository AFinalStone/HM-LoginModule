package com.hm.iou.loginmodule.business.tags;

import com.hm.iou.base.mvp.BaseContract;

import java.io.File;
import java.util.List;

/**
 * Created by hjy on 2018/12/19.
 */

public interface AddTagContract {

    interface View extends BaseContract.BaseView {

        /**
         * 更新昵称
         *
         * @param nickname
         */
        void updateNickname(String nickname);

        /**
         * 更新头像
         *
         * @param url
         */
        void updateAvatar(String url);

        /**
         * 更新button背景图片
         *
         * @param resId
         */
        void updateBtnBackground(int resId);

        void updateBtnTextColor(int colorStateListId);

        void toDataLoadingPage();
    }

    interface Presenter extends BaseContract.BasePresenter {

        void init();

        void searchClipBoard();

        void submitData(List<Integer> tagList, File avatarUrl, String nickname);

    }

}