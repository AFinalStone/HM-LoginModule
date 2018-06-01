package com.hm.iou.loginmodule.business.launch.view;

import com.hm.iou.base.webview.BaseWebviewActivity;
import com.hm.iou.loginmodule.NavigationHelper;

public class LaunchAdvertisementDetailActivity extends BaseWebviewActivity {

    @Override
    public void finish() {
        super.finish();
        NavigationHelper.toMain(mContext);
    }

}
