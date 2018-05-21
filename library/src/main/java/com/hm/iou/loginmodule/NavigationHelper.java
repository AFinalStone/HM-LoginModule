package com.hm.iou.loginmodule;

import android.content.Context;
import android.content.Intent;

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
//        Intent intent = new Intent(this, WebViewH5Activity.class);
//        String title = getString(R.string.webViewHtml5_titleRegisterAndUseAgreement);
//        intent.putExtra(Constants.INTENT_TITLE, title);
//        intent.putExtra(Constants.INTENT_WEB_URL, Constants.WEB_H5_URL_IOU_AGREEMENT);
//        context.startActivity(intent);
    }

    /**
     * 跳转到隐私协议
     *
     * @param context
     */
    public static void toPrivateAgreement(Context context) {
//        Intent intent = new Intent(this, WebViewH5Activity.class);
//        String title = getString(R.string.webViewHtml5_titlePrivateAgreement);
//        intent.putExtra(Constants.INTENT_TITLE, title);
//        intent.putExtra(Constants.INTENT_WEB_URL, Constants.WEB_H5_URL_PRIVACY_AGREEMENT);
//        startActivity(intent);
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
     * @param email
     * @param mobile
     */
    public static void toFindByEmail(Context context, String email, String mobile) {
        Intent intent = new Intent(context, FindByEmailActivity.class);
        intent.putExtra(FindByEmailActivity.EXTRA_KEY_EMAIL, email);
        intent.putExtra(FindByEmailActivity.EXTRA_KEY_MOBILE, mobile);
        context.startActivity(intent);
    }

    /**
     * 跳转到活体校验
     *
     * @param context
     * @param idCard
     */
    public static void toFindByFace(Context context, String idCard) {
//        Intent intent = new Intent(context, FindByEmailActivity.class);
//        intent.putExtra(FindByEmailActivity.EXTRA_KEY_EMAIL, idCard);
//        intent.putExtra(FindByEmailActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
    }

    /**
     * 跳转到重置登录密码
     *
     * @param context
     * @param mobile
     */
    public static void toResetLoginPsd(Context context, String resetPsdType, String mobile) {
        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, resetPsdType);
        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
//        intent.putExtra(Constants.INTENT_EMAIL_NUMBER, mStrEmail);
//        intent.putExtra(Constants.INTENT_CHECK_CODE, mEtEmailCode);
        context.startActivity(intent);
    }

    /**
     * 跳转到首页
     *
     * @param context
     */
    public static void toMain(Context context) {
//        context.startActivity(new Intent(context,Main));
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
        Intent intent = new Intent(context, LoginLoadingActivity.class);
        context.startActivity(intent);
    }


}
