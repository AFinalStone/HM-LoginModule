package com.hm.iou.loginmodule.business.type;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 1.判断当前手机是否有微信，有则显示微信登录，没有则不显示微信登录
 * 2.这个页面要把用户相关的数据清空，因为所有的请求，Retrofit里面对所有的接口进行了统一的header封装，
 * 如果这里不清除用户数据，则服务端的接口会自动取header里面的内容，部分接口会报错
 *
 * @author syl
 * @time 2018/3/30 下午4:36
 */
public class SelectLoginTypeActivity extends BaseActivity<SelectLoginTypePresenter> implements SelectLoginTypeContract.View {

    @BindView(R2.id.ll_background)
    LinearLayout mLlBackground;

    @BindView(R2.id.ll_loginByChat)
    LinearLayout mLlLoginByChat;

    @BindView(R2.id.iv_loginByMobile)
    ImageView mIvLoginByMobile;

    @BindView(R2.id.tv_loginByMobile)
    TextView mTvLoginByMobile;

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
    }

    @OnClick({R2.id.ll_loginByChat, R2.id.ll_loginByMobile, R2.id.tv_onlyLook})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.ll_loginByChat == id) {
            mPresenter.getWxCode();
        } else if (R.id.ll_loginByMobile == id) {
            NavigationHelper.toInputMobile(mContext);
        } else if (R.id.tv_onlyLook == id) {
            NavigationHelper.toMain(mContext);
        }
    }


    @Override
    public void hideButtonForLoginByWx() {
        mLlBackground.setBackground(getResources().getDrawable(R.mipmap.loginmodule_background_login_type_select_01));
        mLlLoginByChat.setVisibility(View.INVISIBLE);
        mLlLoginByMobile.setBackground(getResources().getDrawable(R.drawable.loginmodule_shape_common_btn_inblack));
        mIvLoginByMobile.setImageResource(R.mipmap.loginmodule_icon_mobile_white);
        mTvLoginByMobile.setTextColor(Color.WHITE);
    }

}
