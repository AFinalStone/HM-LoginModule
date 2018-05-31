package com.hm.iou.loginmodule.business.lancher;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.router.Router;
import com.hm.iou.tools.ImageLoader;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 闪屏页
 * 1.判断本地是否有广告，有则开启倒计时进行加载
 * 2.获取最新的版本信息，并保存最新的版本信息
 * 3.倒计时结束，判断用户是否已经登录过
 * 4.判断应用是否是第一次打开
 * 5.用户直接点击跳转按钮
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
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION
//                , Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION)
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                    }
//                })
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (!aBoolean) {
//                            toastMessage("请求权限被拒绝，软件部分功能受限");
//                        }
//                    }
//                });
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
                Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                        .withString("url", linkUrl)
                        .navigation(mContext);
            }
        });
    }

    @Override
    public void setJumpBtnText(String desc) {
        tvJump.setVisibility(View.VISIBLE);
        tvJump.setText(desc);
    }
}
