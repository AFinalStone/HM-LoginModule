package com.hm.iou.loginmodule.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.bean.UserTagBean;
import com.hm.iou.loginmodule.business.guide.view.GuideActivity;
import com.hm.iou.loginmodule.business.launch.view.LaunchActivity;
import com.hm.iou.sharedata.event.CommBizEvent;
import com.hm.iou.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BIND_EMAIL_SUCCESS = "bindEmailSuccess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        findViewById(R.id.btn_open_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LaunchActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onClickOpenLoginTypeSelect(View view) {
        NavigationHelper.toSelectLoginType(this);
    }

    public void onClickResetPsdBySMS(View view) {
        NavigationHelper.toFindBySMS(this, "15267163669");
    }

    public void onClickBindEmail(View view) {
        NavigationHelper.toBindEmail(this);
    }

    public void toClickAddTag(View view) {
        ArrayList<UserTagBean> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            UserTagBean data = new UserTagBean();
            data.setLabelId(i);
            data.setName("我是个人");
            list.add(data);
        }
        NavigationHelper.toAddTagPage(this, list);
    }

    public void toClickGuide(View view) {
        startActivity(new Intent(this, GuideActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusBindEmail(CommBizEvent commBizEvent) {
        if (KEY_BIND_EMAIL_SUCCESS.equals(commBizEvent.key)) {
            ToastUtil.showMessage(this, "用户成功修改签约密码");
        }
    }
}
