package com.hm.iou.loginmodule.business.tags;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.logger.Logger;
import com.hm.iou.loginmodule.R;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.UserInfo;

import java.util.ArrayList;
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
            mView.showChangeAvatarTip(View.INVISIBLE);
            mView.updateAvatar(avatar);
        } else {
            mView.showChangeAvatarTip(View.VISIBLE);
        }
        if (TextUtils.isEmpty(nickname) || "小管家".equals(nickname)) {
            mView.updateNickname("点此设置昵称");
        } else {
            mView.updateNickname(nickname);
        }
    }

    int c = 0;

    @Override
    public void getTagList() {
        mView.showLoadingTags();
        if (c++ < 5) {
            mView.showTagsLoadFail();
            return;
        }

        List<ITagItem> list = new ArrayList<>();
        for (int i=0; i<9; i++) {
            final int index = i;
            list.add(new ITagItem() {
                boolean selected;

                @Override
                public String getTagName() {
                    return "我是律师" + index;
                }

                @Override
                public String getTagId() {
                    return null;
                }

                @Override
                public int getTextColor() {
                    return selected ? 0xffffffff : 0xff000a00;
                }

                @Override
                public int getBgRedId() {
                    return selected ? R.drawable.loginmodule_bg_tag_selected : R.drawable.loginmodule_bg_tag_normal;
                }

                @Override
                public boolean isSelected() {
                    return selected;
                }

                @Override
                public void toggle() {
                    selected = !selected;
                }
            });
        }
        mView.showTags(list);
    }

    @Override
    public void submitData(List<String> tagList) {
        if (tagList == null || tagList.size() < 2) {
            Logger.d("tag的数量 < 2");
            mView.showErrorMsg(View.VISIBLE);
            return;
        }
        mView.showErrorMsg(View.INVISIBLE);
    }

}
