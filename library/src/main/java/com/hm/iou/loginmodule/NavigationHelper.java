package com.hm.iou.loginmodule;

import android.content.Context;
import android.content.Intent;

import com.hm.iou.loginmodule.business.login.view.InputMobileActivity;
import com.hm.iou.loginmodule.business.login.view.MobileLoginActivity;
import com.hm.iou.loginmodule.business.register.view.RegisterByWXChatActivity;
import com.hm.iou.loginmodule.business.type.SelectLoginTypeActivity;

/**
 * @author syl
 * @time 2018/5/19 下午2:59
 */
public class NavigationHelper {

    /**
     * 跳转到登录方式选择页面
     *
     * @param context
     */
    public static void toSelectLoginType(Context context) {
        context.startActivity(new Intent(context, SelectLoginTypeActivity.class));
    }


    /**
     * 跳转到输入手机号的页面
     *
     * @param context
     */
    public static void toInputMobile(Context context) {
        context.startActivity(new Intent(context, InputMobileActivity.class));
    }

    /**
     * 通过手机号进行注册
     *
     * @param context
     * @param mobil
     */
    public static void toRegisterByMobile(Context context, String mobil) {
//        Intent intent = new Intent(context, RegisterByWXChatActivity.class);
//        intent.putExtra(RegisterByWXChatActivity.EXTRA_KEY_WXCHAT_SN, mobile);
//        context.startActivity(intent);
    }


    /**
     * 跳转到手机号登录页面
     *
     * @param context
     * @param mobile
     */
    public static void toMobileLogin(Context context, String mobile) {
        Intent intent = new Intent(context, MobileLoginActivity.class);
        intent.putExtra(MobileLoginActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

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
     * @param context
     * @param wxSn    判断微信是否绑定过手机的交易流水号
     */
    public static void toRegisterByWXChat(Context context, String wxSn) {
        Intent intent = new Intent(context, RegisterByWXChatActivity.class);
        intent.putExtra(RegisterByWXChatActivity.EXTRA_KEY_WXCHAT_SN, wxSn);
        context.startActivity(intent);
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


}
