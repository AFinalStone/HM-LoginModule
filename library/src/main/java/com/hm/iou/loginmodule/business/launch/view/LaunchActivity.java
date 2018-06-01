package com.hm.iou.loginmodule.business.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
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

    @BindView(R2.id.tv_jump)
    TextView tvJump;

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

    @OnClick(R2.id.tv_jump)
    public void onClick() {
        mPresenter.checkUserHaveLogin();
    }


    @Override
    public void showAdvertisement(String imageUrl, final String linkUrl) {
        ImageLoader.getInstance(mContext).displayImage(imageUrl, ivAdvertisement
                , R.drawable.uikit_bg_pic_loading_place, R.drawable.uikit_bg_pic_loading_error);
        ivAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.toLaunchAdvertisement(mContext, linkUrl);
                finish();
            }
        });
    }

    @Override
    public void setJumpBtnText(String desc) {
        tvJump.setVisibility(View.VISIBLE);
        tvJump.setText(desc);
    }
}
