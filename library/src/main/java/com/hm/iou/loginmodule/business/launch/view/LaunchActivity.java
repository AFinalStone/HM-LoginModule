package com.hm.iou.loginmodule.business.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.logger.Logger;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.launch.LaunchContract;
import com.hm.iou.loginmodule.business.launch.LaunchPresenter;
import com.hm.iou.tools.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 启动页
 *
 * @author AFinalStone
 * @time 2018/3/9 下午2:55
 */
public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @BindView(R2.id.iv_advertisement)
    ImageView ivAdvertisement;

    @BindView(R2.id.btn_jump)
    Button btnJump;

    private long mTimeDown;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_launch;
    }

    @Override
    protected LaunchPresenter initPresenter() {
        return new LaunchPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        mPresenter.init();

    }

    @OnClick(R2.id.btn_jump)
    public void onClick() {
        mPresenter.toMain();
    }


    @Override
    public void showAdvertisement(String imageUrl, final String linkUrl) {
        ImageLoader.getInstance(mContext).displayImage(imageUrl, ivAdvertisement
                , R.drawable.uikit_bg_pic_loading_place, R.drawable.uikit_bg_pic_loading_error);
        ivAdvertisement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mTimeDown = System.currentTimeMillis();
                    Logger.d("ACTION_DOWN TIME = " + mTimeDown);
                    mPresenter.pauseCountDown();
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mPresenter.toAdDetail(linkUrl);
//                    long time = System.currentTimeMillis() - mTimeDown;
//                    Logger.d("ACTION_UP TIME = " + time);
//                    if (time > 800) {
//                        Logger.d("ACTION_UP TIME = " + time);
//                    }
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
}
