package com.hm.iou.loginmodule.business.service;


import android.os.Bundle;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.tools.ClipUtil;

import butterknife.OnClick;

/**
 * @author syl
 * @time 2018/3/30 下午4:36
 */
public class CustomerServiceActivity<T extends MvpActivityPresenter> extends BaseActivity<T> {

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_customer_service;
    }

    @Override
    protected T initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {

    }

    @OnClick(R2.id.btn_copyWeiXin)
    public void onClick() {
        ClipUtil.getInstance(mContext).putTextIntoClip("jietiaoguanjia2018");
        toastMessage("客服微信已复制到剪切板");
    }
}
