package com.hm.iou.loginmodule.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.router.Router;
import com.hm.iou.tools.ClipUtil;


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

        BaseBizAppLike baseBizAppLike = new BaseBizAppLike();
        baseBizAppLike.onCreate(this);
        baseBizAppLike.setDebug(true);
        baseBizAppLike.initServer("http://192.168.1.179:8071", "http://dev.54jietiao.com",
                "http://dev.54jietiao.com");
//        baseBizAppLike.initServer("https://api.54jietiao.com", "http://upload.54jietiao.com",
//                "http://h5.54jietiao.com");
//        initNetwork();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClipUtil.putTextIntoClipboard(this, null, null);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    private void initNetwork() {
        System.out.println("init-----------");
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setAppChannel("yyb")
                .setAppVersion("1.3.3")
                .setDeviceId("123abc123")
                .setBaseUrl(BaseBizAppLike.getInstance().getApiServer())
                .build();
        HttpReqManager.init(config);
    }

}
