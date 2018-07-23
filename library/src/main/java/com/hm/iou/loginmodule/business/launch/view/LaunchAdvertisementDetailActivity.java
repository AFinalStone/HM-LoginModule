package com.hm.iou.loginmodule.business.launch.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.hm.iou.base.webview.BaseWebviewActivity;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.socialshare.bean.PlatFormBean;
import com.hm.iou.socialshare.business.view.SharePlatformDialog;
import com.hm.iou.socialshare.dict.PlatformEnum;
import com.hm.iou.tools.NetStateUtil;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.tools.ToastUtil;
import com.hm.iou.uikit.HMTopBarView;

import java.util.ArrayList;
import java.util.List;

public class LaunchAdvertisementDetailActivity extends BaseWebviewActivity {

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        super.initEventAndData(savedInstanceState);
        mTopBar.setRightText("分享");
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                shareAd();
            }

            @Override
            public void onClickImageMenu() {

            }
        });
    }

    @Override
    public void finish() {
        NavigationHelper.toMain(mContext);
        super.finish();
    }

    private void shareAd() {
        if (!NetStateUtil.isNetworkConnected(this)) {
            ToastUtil.showMessage(this, "当前网络不可用，请检查你的网络设置");
            return;
        }

        String title = StringUtil.getUnnullString(mWebView.getTitle());
        if (TextUtils.isEmpty(title)) {
            title = "条管家的活动";
        }
        String desc = "来自 条管家 的分享";
        List<PlatFormBean> list = new ArrayList<>();
        list.add(new PlatFormBean(PlatformEnum.WEIXIN));
        list.add(new PlatFormBean(PlatformEnum.WEIXIN_CIRCLE));
        list.add(new PlatFormBean(PlatformEnum.QQ));
        list.add(new PlatFormBean(PlatformEnum.WEIBO));
        new SharePlatformDialog.Builder(this)
                .setWebUrlDesc(desc)
                .setWebUrlTitle(title)
                .setWebUrl(mUrl)
                .setPlatforms(list)
                .show();
    }

}
