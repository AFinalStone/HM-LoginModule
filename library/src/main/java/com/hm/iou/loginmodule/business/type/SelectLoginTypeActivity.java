package com.hm.iou.loginmodule.business.type;


import android.Manifest;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.PermissionUtil;
import com.hm.iou.cityselect.location.LocationManager;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 1.判断当前手机是否有微信，有则显示微信登录，没有则不显示微信登录
 *
 * @author syl
 * @time 2018/3/30 下午4:36
 */
public class SelectLoginTypeActivity extends BaseActivity<SelectLoginTypePresenter> implements SelectLoginTypeContract.View {

    private float mXDown = 0;
    private float mXUp = 0;

    @BindView(R2.id.ll_loginByChat)
    LinearLayout mLlLoginByChat;

    @BindView(R2.id.ll_loginByMobile)
    LinearLayout mLlLoginByMobile;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_select_login_type;
    }

    @Override
    protected SelectLoginTypePresenter initPresenter() {
        return new SelectLoginTypePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mPresenter.isInstalledWxChatAPP();
        mPresenter.checkVersion();

        //如果有定位权限，则请求定位
        if (PermissionUtil.isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                PermissionUtil.isPermissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            LocationManager.getInstance(this).requestLocation();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            mXDown = event.getX();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            mXUp = event.getX();
            if (mXUp - mXDown > 50) {
                finish();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(0, R.anim.uikit_activity_to_right);
        super.onDestroy();
    }

    @OnClick({R2.id.ll_loginByChat, R2.id.ll_loginByMobile})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.ll_loginByChat == id) {
            mPresenter.getWxCode();
        } else if (R.id.ll_loginByMobile == id) {
            NavigationHelper.toInputMobile(mContext);
        }
    }

    @Override
    public void hideButtonForLoginByWx() {
        mLlLoginByChat.setVisibility(View.GONE);
    }

}
