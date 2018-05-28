package com.hm.iou.loginmodule.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.sharedata.event.CommBizEvent;
import com.hm.iou.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BIND_EMAIL_SUCCESS = "bindEmailSuccess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onClickOpenLoginTypeSelect(View view) {
        NavigationHelper.toSelectLoginType(this);
    }

    public void onClickTokenLogin(View view) {
        NavigationHelper.toLoginLoading(this, true);
    }

    public void onClickResetPsdBySMS(View view) {
        NavigationHelper.toFindBySMS(this, "15267163669");
    }

    public void onClickBindEmail(View view) {
        NavigationHelper.toBindEmail(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusBindEmail(CommBizEvent commBizEvent) {
        if (KEY_BIND_EMAIL_SUCCESS.equals(commBizEvent.key)) {
            ToastUtil.showMessage(this, "用户成功修改签约密码");
        }
    }
}
