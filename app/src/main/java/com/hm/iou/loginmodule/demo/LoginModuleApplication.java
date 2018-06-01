package com.hm.iou.loginmodule.demo;

import android.app.Application;

import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.router.Router;


/**
 * @author syl
 * @time 2018/5/14 下午3:23
 */
public class LoginModuleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
        Logger.init(this, true);
        initNetwork();
    }


    private void initNetwork() {
        System.out.println("init-----------");
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setAppChannel("yyb")
                .setAppVersion("1.0.2")
                .setDeviceId("123abc123")
                .setBaseUrl("http://192.168.1.254")
                .build();
        HttpReqManager.init(config);
    }

}
