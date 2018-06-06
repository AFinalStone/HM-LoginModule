package com.hm.iou.loginmodule.business.guide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author syl
 * @time 2018/5/31 下午3:01
 */
public class GuidePresenter extends BaseLoginModulePresenter<GuideContract.View> implements GuideContract.Presenter {

    private List<Integer> mPicListData;

    public GuidePresenter(@NonNull Context context, @NonNull GuideContract.View view) {
        super(context, view);
    }

    @Override
    public void init() {
        mPicListData = new ArrayList<>();
        mPicListData.add(R.mipmap.loginmodule_background_guide_01);
        mPicListData.add(R.mipmap.loginmodule_background_guide_02);
        mPicListData.add(R.mipmap.loginmodule_background_guide_03);
        mPicListData.add(R.mipmap.loginmodule_background_guide_04);
        mView.showViewPager(mPicListData);
    }
}
