package com.hm.iou.loginmodule.business.guide.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.guide.GuideContract;
import com.hm.iou.loginmodule.business.guide.GuidePresenter;

import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

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

    GuidePagerAdapter mAdapter;
    boolean mIsFirstEnter = false;
    boolean mHaveFinishView = false;

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
                            }
                        } else {
                            mIsFirstEnter = true;
                        }
                    }
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
