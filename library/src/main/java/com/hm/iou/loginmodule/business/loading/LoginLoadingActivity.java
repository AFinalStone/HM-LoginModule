package com.hm.iou.loginmodule.business.loading;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.ImageLoader;

import butterknife.BindView;


/**
 * Created by syl on 2017/11/10.
 * 1.用户从手机登录页面进入这个页面，根据手机号和密码进行登录
 * 2.用户通过token进行登录
 * 3.用户通过微信进行登录
 * 4.用户通过微信进行注册并绑定手机号并登录
 * 5.用户通过微信登录，输入手机号之后判断手机号已经存在，这里只是进行手机号和微信的绑定，然后登陆
 */
public class LoginLoadingActivity extends BaseActivity<LoginLoadingPresenter> implements LoginLoadingContract.View {

    public static final String EXTRA_LOADING_TYPE = "loading_type";
    public static final String LOADING_TYPE_TOKEN_LOGIN = "token_login";

    @BindView(R2.id.iv_header)
    ImageView ivHeader;

    @BindView(R2.id.tv_nickName)
    TextView tvNickName;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_login_loading;
    }

    @Override
    protected LoginLoadingPresenter initPresenter() {
        return new LoginLoadingPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initHeaderAndNickName();
        String loadingType = getIntent().getStringExtra(EXTRA_LOADING_TYPE);
        if (LOADING_TYPE_TOKEN_LOGIN.equals(loadingType)) {
            mPresenter.tokenLogin();
            return;
        }
        mPresenter.getHomeData();
    }


    private void initHeaderAndNickName() {
        UserInfo userDataBean = UserManager.getInstance(this).getUserInfo();
        if (userDataBean != null) {
            //头像
            String sexEnum = userDataBean.getSex();
            int imageResId = R.mipmap.uikit_icon_header_unknow;
            if ("MALE".equals(sexEnum)) {
                imageResId = R.mipmap.uikit_icon_header_man;
            } else if ("FEMALE".equals(sexEnum)) {
                imageResId = R.mipmap.uikit_icon_header_wuman;
            }
            ivHeader.setImageResource(imageResId);
            String urlHeader = userDataBean.getAvatarUrl();
            ImageLoader.getInstance(mContext).displayImage(urlHeader, ivHeader, imageResId, imageResId);
            String nickName = userDataBean.getNickName();
            tvNickName.setText(nickName);
        }
    }


    private void toLoginSelectView() {
//        startActivity(new Intent(this, LoginSelectActivity.class));
//        finish();
    }

    private void toLoginByPhoneView() {
//        Intent intent = new Intent(this, LoginByPhoneActivity.class);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, getIntent().getStringExtra(Constants.INTENT_MOBILE_NUMBER));
//        startActivity(intent);
//        finish();
    }

    public void toMainView() {
//        startNewActivity(MainActivity.class);
//        finish();
    }


}
