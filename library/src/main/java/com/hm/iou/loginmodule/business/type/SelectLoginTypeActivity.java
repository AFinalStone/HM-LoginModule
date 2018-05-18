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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 1.判断当前手机是否有微信，有则显示微信登录，没有则不显示微信登录
 * 2.这个页面要把用户相关的数据清空，因为所有的请求，Retrofit里面对所有的接口进行了统一的header封装，
 * 如果这里不清除用户数据，则服务端的接口会自动取header里面的内容，部分接口会报错
 *
 * @author AFinalStone
 * @time 2018/3/30 下午4:36
 */
public class SelectLoginTypeActivity extends BaseActivity<SelectLoginTypePresenter> implements SelectLoginTypeContract.View {

    @BindView(R2.id.ll_background)
    LinearLayout mLlBackground;

    @BindView(R2.id.ll_loginByChat)
    LinearLayout mLlLoginByChat;

    @BindView(R2.id.iv_loginByPhone)
    ImageView ivLoginByPhone;

    @BindView(R2.id.tv_loginByPhone)
    TextView tvLoginByPhone;

    @BindView(R2.id.ll_loginByPhone)
    LinearLayout mLlLoginByPhone;

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
        mPresenter.isExistsWx();
    }

    @OnClick({R2.id.ll_loginByChat, R2.id.ll_loginByPhone, R2.id.tv_onlyLook})
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.ll_loginByChat == id) {
            mPresenter.getWxCode();
        } else if (R.id.ll_loginByPhone == id) {
            NavigationHelper.toMobileLoginInputPhone(mContext);
        } else if (R.id.tv_onlyLook == id) {
            NavigationHelper.toMain(mContext);
        }
    }



    @Override
    public void hideButtonForLoginByWx() {
        mLlBackground.setBackground(getResources().getDrawable(R.mipmap.background_login_type_select_01));
        mLlLoginByChat.setVisibility(View.INVISIBLE);
        mLlLoginByPhone.setBackground(getResources().getDrawable(R.drawable.shape_common_btn_inblack));
        ivLoginByPhone.setImageResource(R.mipmap.icon_mobile_white);
        tvLoginByPhone.setTextColor(Color.WHITE);
    }

    @Override
    public void startNewActivityAnimator() {
        overridePendingTransition(R.anim.uikit_activity_open_from_right, R.anim.uikit_activity_open_from_left);
    }

}
