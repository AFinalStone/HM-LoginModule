package com.hm.iou.loginmodule.business.loading;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.SexEnum;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.ImageLoader;

import butterknife.BindView;


/**
 * Created by syl on 2017/11/10.
 * 2.用户通过token进行登录
 */
public class LoginLoadingActivity extends BaseActivity<LoginLoadingPresenter> implements LoginLoadingContract.View {

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
        mPresenter.getHomeData();
    }

    private void initHeaderAndNickName() {
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        if (userInfo != null) {
            //头像
            int sexEnum = userInfo.getSex();
            int resIdHeader = R.mipmap.uikit_icon_header_unknow;
            if (sexEnum == SexEnum.MALE.getValue()) {
                resIdHeader = R.mipmap.uikit_icon_header_man;
            } else if (sexEnum == SexEnum.FEMALE.getValue()) {
                resIdHeader = R.mipmap.uikit_icon_header_wuman;
            }
            ivHeader.setImageResource(resIdHeader);
            String urlHeader = userInfo.getAvatarUrl();
            ImageLoader.getInstance(mContext).displayImage(urlHeader, ivHeader, resIdHeader, resIdHeader);
            String nickName = userInfo.getNickName();
            tvNickName.setText(nickName);
        }
    }

}
