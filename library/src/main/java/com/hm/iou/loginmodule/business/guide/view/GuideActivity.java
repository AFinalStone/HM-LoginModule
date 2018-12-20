package com.hm.iou.loginmodule.business.guide.view;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.cityselect.location.LocationManager;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.guide.GuideContract;
import com.hm.iou.loginmodule.business.guide.GuidePresenter;
import com.hm.iou.tools.SystemUtil;
import com.hm.iou.uikit.CircleIndicator;
import com.hm.iou.uikit.dialog.IOSAlertDialog;
import com.hm.iou.wxapi.WXEntryActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static com.hm.iou.socialshare.SocialShareUtil.PACKAGE_NAME_OF_WX_CHAT;

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

    @BindView(R2.id.iv_loginByWX)
    ImageView mIvLoginByWx;

    @BindView(R2.id.ll_loginByChat)
    LinearLayout mLlLoginByChat;

    @BindView(R2.id.ll_loginByMobile)
    LinearLayout mLlLoginByMobile;

    @BindView(R2.id.ll_guide_wx_only)
    View mLayoutWxOnly;

    private GuidePagerAdapter mAdapter;

    private boolean mAccessFineLocation;//定位
    private boolean mAccessCoarseLocation;//定位

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
        //请求权限：定位权限、日历读写权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //如果有定位权限，则请求定位
                        if (mAccessFineLocation && mAccessCoarseLocation) {
                            LocationManager.getInstance(mContext).requestLocation();
                        }
                    }
                })
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.name.equals(Manifest.permission.WRITE_CALENDAR)) {
                            if (permission.granted) {
                                TraceUtil.onEvent(GuideActivity.this, "perm_calendar_allow");
                            } else {
                                TraceUtil.onEvent(GuideActivity.this, "perm_calendar_disallow");
                            }
                        } else if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            if (permission.granted) {
                                mAccessFineLocation = true;
                            } else {
                                mAccessFineLocation = false;
                            }
                        } else if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            if (permission.granted) {
                                mAccessCoarseLocation = true;
                            } else {
                                mAccessCoarseLocation = false;
                            }
                        }
                    }
                });
        mIvLoginByWx.setColorFilter(Color.BLACK);

        mPresenter.init();
        mPresenter.checkVersion();
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onDestroy() {
        overridePendingTransition(0, R.anim.uikit_activity_to_right);
        super.onDestroy();
        WXEntryActivity.cleanWXLeak();
    }

    @OnClick({R2.id.ll_loginByChat, R2.id.ll_loginByMobile, R2.id.btn_guide_wx_only})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.ll_loginByChat == id) {
            TraceUtil.onEvent(this, "guide_wx_click");
            mPresenter.getWxCode();
        } else if (R.id.ll_loginByMobile == id) {
            TraceUtil.onEvent(this, "guide_mob_click");
            NavigationHelper.toInputMobile(mContext);
        } else if (R.id.btn_guide_wx_only == id) {
            boolean flag = SystemUtil.isAppInstalled(mContext, PACKAGE_NAME_OF_WX_CHAT);
            if (flag) {
                TraceUtil.onEvent(this, "guide_wx_click");
                mPresenter.getWxCode();
            } else {
                new IOSAlertDialog.Builder(this)
                        .setTitle(getString(R.string.loginmodule_no_wx_title))
                        .setMessage(getString(R.string.loginmodule_no_wx_msg))
                        .setPositiveButton(getString(R.string.loginmodule_change_login_type), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TraceUtil.onEvent(GuideActivity.this, "guide_mob_click");
                                NavigationHelper.toInputMobile(mContext);
                            }
                        })
                        .setCanceledOnTouchOutside(false)
                        .setPositiveButtonTextColor(getResources().getColor(R.color.uikit_blue))
                        .show();
            }
        }
    }

    @Override
    public void showViewPager(List<IGuidePageItem> list) {
        mAdapter = new GuidePagerAdapter(mContext, list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    TraceUtil.onEvent(mContext, "guide_see_two");
                } else if (position == 4) {
                    TraceUtil.onEvent(mContext, "guide_see_five");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mIndicator.setViewPager(mViewPager);

    }

    @Override
    public void hideButtonForLoginByWx() {
        mLlLoginByChat.setVisibility(View.GONE);
    }

    @Override
    public void showWXLoginOnly() {
        mLayoutWxOnly.setVisibility(View.VISIBLE);
    }

}