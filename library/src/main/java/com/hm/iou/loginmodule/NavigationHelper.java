package com.hm.iou.loginmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hm.iou.loginmodule.business.register.wx.RegisterByWXChatActivity;

/**
 * Created by hjy on 18/5/4.<br>
 */

public class NavigationHelper {

    /**
     * 跳转到首页
     *
     * @param context
     */
    public static void toMain(Context context) {
//        startNewActivity(MainActivity.class);
//        finish();
    }

    /**
     * 通过微信进行注册或者绑定操作
     *
     * @param activity
     * @param wxSn     判断微信是否绑定过手机的交易流水号
     */
    public static void toRegisterByWXChat(Context context, String wxSn) {
        Intent intent = new Intent(context, RegisterByWXChatActivity.class);
        intent.putExtra(RegisterByWXChatActivity.EXTRA_KEY_WXCHAT_SN, wxSn);
        context.startActivity(intent);
    }

    /**
     * 跳转到输入手机号的页面
     *
     * @param context
     */
    public static void toMobileLoginInputPhone(Context context) {
//        startNewActivity(LoginInputPhoneActivity.class);
    }

    /**
     * 跳转到预加载首页数据的页面
     *
     * @param context
     */
    public static void toLoginLoading(Context context) {
//        Intent intent = new Intent(mContext, LoginLoadingActivity.class);
//        intent.putExtra(Constants.INTENT_LOGIN_LOADING_TYPE, LoginTypeEnum.loginByWx);
//        startActivity(intent);
//        finish();
//        overridePendingTransition(R.anim.activity_open_from_right, R.anim.activity_to_left);
    }


    /**
     * 跳转到手机号登录页面
     *
     * @param context
     * @param mobile
     */
    public static void toMobileLogin(Context context, String mobile) {
//        Intent intent = new Intent(this, LoginByPhoneActivity.class);
//        intent.putExtra(Constants.INTENT_INPUT_PHONE, userPhone);
//        startActivity(intent);
    }

}
