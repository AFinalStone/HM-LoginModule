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

        void showChangeAvatarTip(int visibility);

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

        void showErrorMsg(int visibility);

        void toDataLoadingPage();
    }

    interface Presenter extends BaseContract.BasePresenter {

        void init();

        void submitData(List<String> tagList, File avatarUrl, String nickname);

    }

}