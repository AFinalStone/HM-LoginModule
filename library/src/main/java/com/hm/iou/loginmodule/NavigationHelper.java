package com.hm.iou.loginmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hm.iou.loginmodule.business.email.BindEmailActivity;
import com.hm.iou.loginmodule.business.loading.LoginLoadingActivity;
import com.hm.iou.loginmodule.business.login.view.InputMobileActivity;
import com.hm.iou.loginmodule.business.login.view.MobileLoginActivity;
import com.hm.iou.loginmodule.business.password.view.FindByEmailActivity;
import com.hm.iou.loginmodule.business.password.view.FindByInputMobileActivity;
import com.hm.iou.loginmodule.business.password.view.FindBySMSActivity;
import com.hm.iou.loginmodule.business.password.view.ResetLoginPsdActivity;
import com.hm.iou.loginmodule.business.register.view.RegisterByMobileActivity;
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
     * @param mobile
     */
    public static void toRegisterByMobile(Context context, String mobile) {
        Intent intent = new Intent(context, RegisterByMobileActivity.class);
        intent.putExtra(RegisterByMobileActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

    /**
     * 跳转到注册与使用协议
     *
     * @param context
     */
    public static void ToRegisterAndUseAgreement(Context context) {
        try {
            Class WebViewH5Activity = Class.forName("com.hm.iou.hmreceipt.ui.activity.html5.WebViewH5Activity");
            Intent intent = new Intent(context, WebViewH5Activity);
            String title = "注册与使用协议";
            intent.putExtra("title", title);
            intent.putExtra("web_url", "file:///android_asset/APPH5/IOUAgreement/IOUAgreement.html");
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到隐私协议
     *
     * @param context
     */
    public static void toPrivateAgreement(Context context) {
        try {
            Class WebViewH5Activity = Class.forName("com.hm.iou.hmreceipt.ui.activity.html5.WebViewH5Activity");
            Intent intent = new Intent(context, WebViewH5Activity);
            String title = "隐私协议";
            intent.putExtra("title", title);
            intent.putExtra("web_url", "file:///android_asset/APPH5/PrivacyAgreement/PrivacyAgreement.html");
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
     * 跳转到找回密码的页面
     *
     * @param context
     * @param mobile
     */
    public static void toFindByInputMobile(Context context, String mobile) {
        Intent intent = new Intent(context, FindByInputMobileActivity.class);
        intent.putExtra(FindByInputMobileActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

    /**
     * 跳转到通过手机获取重置登录密码短信验证的页面
     *
     * @param context
     * @param mobile
     */
    public static void toFindBySMS(Context context, String mobile) {
        Intent intent = new Intent(context, FindBySMSActivity.class);
        intent.putExtra(FindBySMSActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

    /**
     * 跳转到通过邮箱获取重置登录密码的邮箱验证码页面
     *
     * @param context
     * @param mobile
     * @param email
     */
    public static void toFindByEmail(Context context, String mobile, String email) {
        Intent intent = new Intent(context, FindByEmailActivity.class);
        intent.putExtra(FindByEmailActivity.EXTRA_KEY_TIP_EMAIL, email);
        intent.putExtra(FindByEmailActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

    /**
     * 跳转到通过人脸识别找回密码的页面
     *
     * @param context
     * @param mobile
     * @param userName
     */
    public static void toFindByFace(Context context, String mobile, String userName) {
        try {
            Class FaceCheckFindLoginPsdActivity = Class.forName("com.hm.iou.hmreceipt.facecheck.business.view.FaceCheckFindLoginPsdActivity");
            Intent intent = new Intent(context, FaceCheckFindLoginPsdActivity);
            intent.putExtra("mobile", mobile);
            intent.putExtra("name", userName);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 活体校验失败
     *
     * @param context
     * @param remainderNumber 剩余次数
     */
    public static void toLivingCheckFailed(Context context, String remainderNumber) {
        try {
            Class FaceCheckFailedActivity = Class.forName("com.hm.iou.hmreceipt.facecheck.business.view.FaceCheckFailedActivity");
            Intent intent = new Intent(context, FaceCheckFailedActivity);
            intent.putExtra("face_check_remainder_number", remainderNumber);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信验证码校验成功，跳转到重置登录密码
     *
     * @param context
     * @param mobile
     */
    public static void toResetLoginPsdBySMS(Context context, String mobile, String checkCode) {
        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, ResetLoginPsdActivity.RESET_PSD_TYPE_BY_SMS);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_SMS_CHECK_CODE, checkCode);
        context.startActivity(intent);
    }

    /**
     * 邮箱验证码校验成功，跳转到重置登录密码
     *
     * @param context
     * @param mobile
     * @param email
     * @param checkCode
     * @param sn        邮箱验证码校验的交易流水号
     */
    public static void toResetLoginPsdByEmail(Context context, String mobile, String email, String checkCode, String sn) {
        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, ResetLoginPsdActivity.RESET_PSD_TYPE_BY_EMAIL);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL, email);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL_CHECK_CODE, checkCode);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL_CHECK_CODE_SN, sn);
        context.startActivity(intent);
    }

    /**
     * 活体校验校验成功，跳转到重置登录密码
     *
     * @param context
     * @param mobile
     * @param idCard
     * @param faceCheckSN
     */
    public static void toResetLoginPsdByFace(Context context, String mobile, String idCard, String faceCheckSN) {
        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, ResetLoginPsdActivity.RESET_PSD_TYPE_BY_FACE);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_USER_ID_CARD, idCard);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_FACE_CHECK_SN, faceCheckSN);
        context.startActivity(intent);
    }

    /**
     * 跳转到首页
     *
     * @param context
     */
    public static void toMain(Context context) {
        try {
            Class MainActivity = Class.forName("com.hm.iou.hmreceipt.ui.activity.MainActivity");
            context.startActivity(new Intent(context, MainActivity));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过微信进行注册或者绑定操作
     *
     * @param context
     * @param wxSn    判断微信是否绑定过手机的交易流水号
     */
    public static void toRegisterByWXChat(Context context, String wxSn) {
        Intent intent = new Intent(context, RegisterByWXChatActivity.class);
        intent.putExtra(RegisterByWXChatActivity.EXTRA_KEY_WX_CHAT_SN, wxSn);
        context.startActivity(intent);
    }

    /**
     * 跳转到预加载首页数据的页面
     *
     * @param context
     */
    public static void toLoginLoading(Context context, boolean isTokenLogin) {
        Intent intent = new Intent(context, LoginLoadingActivity.class);
        if (isTokenLogin) {
            intent.putExtra(LoginLoadingActivity.EXTRA_LOADING_TYPE, LoginLoadingActivity.LOADING_TYPE_TOKEN_LOGIN);
        }
        context.startActivity(intent);
    }


    /**
     * 跳转到邮箱绑定页面
     *
     * @param context
     */
    public static void toBindEmail(Context context) {
        Intent intent = new Intent(context, BindEmailActivity.class);
        context.startActivity(intent);
    }
}
