package com.hm.iou.loginmodule.business.launch.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.launch.LaunchContract;
import com.hm.iou.loginmodule.business.launch.LaunchPresenter;
import com.hm.iou.tools.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 启动页
 *
 * @author AFinalStone
 * @time 2018/3/9 下午2:55
 */
public class LaunchActivity extends Activity implements LaunchContract.View {

    private Unbinder mUnbinder;

    @BindView(R2.id.iv_advertisement)
    ImageView ivAdvertisement;
    @BindView(R2.id.btn_jump)
    Button btnJump;

    private LaunchPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManager.getInstance().addActivity(this);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = initPresenter();
        initEventAndData(savedInstanceState);

        TraceUtil.onEvent(this, "launch_enter");
    }

    protected int getLayoutId() {
        return R.layout.loginmodule_activity_launch;
    }

    protected LaunchPresenter initPresenter() {
        return new LaunchPresenter(this, this);
    }

    protected void initEventAndData(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        mPresenter.init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
        this.mUnbinder.unbind();
        ActivityManager.getInstance().removeActivity(this);
    }

    @OnClick(R2.id.btn_jump)
    public void onClick() {
        TraceUtil.onEvent(this, "launch_skip");
        mPresenter.toMain();
    }


    @Override
    public void showAdvertisement(String imageUrl, final String linkUrl) {
        ImageLoader.getInstance(this).displayImage(imageUrl, ivAdvertisement
                , R.drawable.uikit_bg_pic_loading_place, R.drawable.uikit_bg_pic_loading_error);
        ivAdvertisement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mPresenter.pauseCountDown();
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    TraceUtil.onEvent(LaunchActivity.this, "launch_ad_click");
                    mPresenter.toAdDetail(linkUrl);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setJumpBtnText(String desc) {
        btnJump.setVisibility(View.VISIBLE);
        btnJump.setText(desc);
    }

    @Override
    public void closeCurrPage() {
        finish();
    }
}
