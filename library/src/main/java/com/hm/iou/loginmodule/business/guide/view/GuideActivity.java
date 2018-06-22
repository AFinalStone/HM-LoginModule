package com.hm.iou.loginmodule.business.guide.view;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.guide.GuideContract;
import com.hm.iou.loginmodule.business.guide.GuidePresenter;
import com.hm.iou.loginmodule.widget.CircleIndicator;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 引导页
 *
 * @author AFinalStone
 * @time 2018/3/9 下午2:52
 */
public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View {

    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

    @BindView(R2.id.indicator)
    CircleIndicator mIndicator;

    private GuidePagerAdapter mAdapter;
    private boolean mIsFirstEnter = false;
    private boolean mHaveFinishView = false;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_guide;
    }

    @Override
    protected GuidePresenter initPresenter() {
        return new GuidePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        mPresenter.init();

        //请求日历权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                    }
                });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsFirstEnter = false;
        mHaveFinishView = false;
    }

    @Override
    public void showViewPager(final List<Integer> list) {
        mAdapter = new GuidePagerAdapter(mContext, list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == list.size() - 1) {
                    if (positionOffset == 0 && positionOffsetPixels == 0) {
                        if (mIsFirstEnter) {
                            if (!mHaveFinishView) {
                                mHaveFinishView = true;
                                NavigationHelper.toSelectLoginType(mContext);
                                overridePendingTransition(R.anim.uikit_activity_open_from_right, R.anim.uikit_activity_to_left);
                            }
                        } else {
                            mIsFirstEnter = true;
                        }
                    }
                } else {
                    mIsFirstEnter = false;
                    mHaveFinishView = false;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mIndicator.setViewPager(mViewPager);
    }

}
