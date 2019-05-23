package com.hm.iou.loginmodule.business.tags;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.comm.ClipBoardBean;
import com.hm.iou.base.comm.CommApi;
import com.hm.iou.base.file.FileApi;
import com.hm.iou.base.file.FileBizType;
import com.hm.iou.base.file.FileUploadResult;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.logger.Logger;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.ClipUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.List;

/**
 * Created by hjy on 2018/12/19.
 */

public class AddTagPresenter extends MvpActivityPresenter<AddTagContract.View> implements AddTagContract.Presenter {

    public AddTagPresenter(@NonNull Context context, @NonNull AddTagContract.View view) {
        super(context, view);
    }

    @Override
    public void init() {
        UserInfo userInfo = UserManager.getInstance(mContext).getUserInfo();
        String nickname = userInfo.getNickName();
        String avatar = userInfo.getAvatarUrl();
        if (!TextUtils.isEmpty(avatar)) {
            mView.updateAvatar(avatar);
        }
        if (TextUtils.isEmpty(nickname) || "小管家".equals(nickname)) {
            mView.updateNickname("点此设置昵称");
        } else {
            mView.updateNickname(nickname);
        }
    }

    @Override
    public void searchClipBoard() {
        CharSequence content = ClipUtil.getTextFromClipboard(mContext);
        Logger.d("剪切板内容为：" + content);
        if (TextUtils.isEmpty(content) || !content.toString().startsWith("【条管家】")) {
            Logger.d("不搜索剪切板...");
            return;
        }
        Logger.d("开始搜索剪切板....");
        CommApi.searchClipBoardOnLabel(content.toString())
                .compose(getProvider().<BaseResponse<ClipBoardBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<ClipBoardBean>handleResponse())
                .subscribeWith(new CommSubscriber<ClipBoardBean>(mView) {
                    @Override
                    public void handleResult(ClipBoardBean data) {
                        ClipUtil.putTextIntoClipboard(mContext, null, null);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });
    }

    @Override
    public void submitData(final List<Integer> tagList, File newAvatarFile, final String newNickname) {
        if (tagList == null || tagList.size() < 2) {
            Logger.d("tag的数量 < 2");
            mView.toastMessage("请至少选择两个标签哟～");
            return;
        }

        final UserInfo userInfo = UserManager.getInstance(mContext).getUserInfo();
        if (newAvatarFile != null) {
            //需要上传头像文件
            mView.showLoadingView("图片上传中...");
            FileApi.uploadImage(newAvatarFile, FileBizType.Avatar)
                    .compose(getProvider().<BaseResponse<FileUploadResult>>bindUntilEvent(ActivityEvent.DESTROY))
                    .map(RxUtil.<FileUploadResult>handleResponse())
                    .subscribeWith(new CommSubscriber<FileUploadResult>(mView) {
                        @Override
                        public void handleResult(FileUploadResult result) {
                            mView.dismissLoadingView();
                            setUserTags(tagList, result.getFileId(), result.getFileUrl(), newNickname);
                        }

                        @Override
                        public void handleException(Throwable throwable, String s, String s1) {
                            mView.dismissLoadingView();
                        }
                    });
        } else {
            //直接设置
            setUserTags(tagList, null, null, newNickname);
        }
    }

    private void setUserTags(List<Integer> tagList, final String avatarFileId, final String avatarFileUrl, final String nickname) {
        mView.showLoadingView();
        LoginModuleApi.setTags(avatarFileId, nickname, tagList)
                .compose(getProvider().<BaseResponse<Object>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.handleResponse())
                .subscribeWith(new CommSubscriber<Object>(mView) {
                    @Override
                    public void handleResult(Object o) {
                        mView.dismissLoadingView();
                        //设置成功，保存更新用户头像和昵称

                        if (!TextUtils.isEmpty(avatarFileId) && !TextUtils.isEmpty(avatarFileUrl)) {
                            UserManager.getInstance(mContext).updateAvatar(avatarFileUrl);
                        }
                        if (!TextUtils.isEmpty(nickname)) {
                            UserManager.getInstance(mContext).updateNickname(nickname);
                        }

                        mView.toDataLoadingPage();
                        mView.closeCurrPage();
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

}