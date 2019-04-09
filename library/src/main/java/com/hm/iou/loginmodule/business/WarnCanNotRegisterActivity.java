package com.hm.iou.loginmodule.business;

import android.os.Bundle;
import android.view.View;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.event.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * Created by hjy on 2019/4/9.
 */

public class WarnCanNotRegisterActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_warn_can_not_register;
    }

    @Override
    protected MvpActivityPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @OnClick(value = {R2.id.btn_register_again})
    void onClick(View view) {
        if (view.getId() == R.id.btn_register_again) {
            exitApp();
        }
    }

    private void exitApp() {
        EventBus.getDefault().post(new LogoutEvent());
        HttpReqManager.getInstance().setUserId("");
        HttpReqManager.getInstance().setToken("");
        UserManager.getInstance(mContext).logout();
        ActivityManager.getInstance().exitAllActivities();
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/selecttype")
                .navigation(mContext);
    }
}
