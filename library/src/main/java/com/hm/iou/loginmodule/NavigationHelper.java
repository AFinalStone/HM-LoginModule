package com.hm.iou.loginmodule;

import android.content.Context;
import android.content.Intent;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.loginmodule.business.email.BindEmailActivity;
import com.hm.iou.loginmodule.business.login.view.InputMobileActivity;
import com.hm.iou.loginmodule.business.login.view.MobileLoginActivity;
import com.hm.iou.loginmodule.business.password.view.FindByEmailActivity;
import com.hm.iou.loginmodule.business.password.view.FindByInputMobileActivity;
import com.hm.iou.loginmodule.business.password.view.FindBySMSActivity;
import com.hm.iou.loginmodule.business.password.view.ResetLoginPsdActivity;
import com.hm.iou.loginmodule.business.register.view.RegisterByMobileActivity;
import com.hm.iou.loginmodule.business.register.view.RegisterByWXChatActivity;
import com.hm.iou.router.Router;

/**
 * @author syl
 * @time 2018/5/19 下午2:59
 */
public class NavigationHelper {

    /**
     * 跳转到启动页广告详情
     *
     * @param context
     */
    public static void toLaunchAdvertisement(Context context, String url) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/login/launchAdvertisementDetail")
                .withString("url", url)
                .navigation(context);
    }

    /**
     * 跳转到引导页
     *
     * @param context
     */
    public static void toGuide(Context context) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/login/guide")
                .navigation(context);
    }


    /**
     * 跳转到登录方式选择页面
     *
     * @param context
     */
    public static void toSelectLoginType(Context context) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/login/selecttype")
                .navigation(context);
    }

    /**
     * 跳转到首页
     *
     * @param context
     */
    public static void toMain(Context context) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/main/index")
                .navigation(context);

    }


    /**
     * 跳转到输入手机号的页面
     *
     * @param context
     */
    public static void toInputMobile(Context context) {
//        context.startActivity(new Intent(context, InputMobileActivity.class));
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/login/inputmobile")
                .navigation(context);
    }

    /**
     * 通过手机号进行注册
     *
     * @param context
     * @param mobile
     */
    public static void toRegisterByMobile(Context context, String mobile) {
//        Intent intent = new Intent(context, RegisterByMobileActivity.class);
//        intent.putExtra(RegisterByMobileActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/login/register_by_mobile")
                .withString("mobile", mobile)
                .navigation(context);
    }

    /**
     * 跳转到注册与使用协议
     *
     * @param context
     */
    public static void ToRegisterAndUseAgreement(Context context) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                .withString("url", BaseBizAppLike.getInstance().getH5Server() + LoginModuleConstants.H5_REGISTER_AGREEMENT)
                .navigation(context);
    }

    /**
     * 跳转到隐私协议
     *
     * @param context
     */
    public static void toPrivateAgreement(Context context) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                .withString("url", BaseBizAppLike.getInstance().getH5Server() + LoginModuleConstants.H5_PRIVACYAGREEMENT)
                .navigation(context);
    }


    /**
     * 跳转到手机号登录页面
     *
     * @param context
     * @param mobile
     */
    public static void toMobileLogin(Context context, String mobile) {
//        Intent intent = new Intent(context, MobileLoginActivity.class);
//        intent.putExtra(MobileLoginActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/mobilelogin")
                .withString("mobile", mobile)
                .navigation(context);
    }

    /**
     * 跳转到找回密码的页面
     *
     * @param context
     * @param mobile
     */
    public static void toFindByInputMobile(Context context, String mobile) {
//        Intent intent = new Intent(context, FindByInputMobileActivity.class);
//        intent.putExtra(FindByInputMobileActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/find_by_input_mobile")
                .withString("mobile", mobile)
                .navigation(context);
    }

    /**
     * 跳转到通过手机获取重置登录密码短信验证的页面
     *
     * @param context
     * @param mobile
     */
    public static void toFindBySMS(Context context, String mobile) {
//        Intent intent = new Intent(context, FindBySMSActivity.class);
//        intent.putExtra(FindBySMSActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/find_by_sms")
                .withString("mobile", mobile)
                .navigation(context);
    }

    /**
     * 跳转到通过邮箱获取重置登录密码的邮箱验证码页面
     *
     * @param context
     * @param mobile   手机号
     * @param tipEmail 脱敏的提示邮箱
     */
    public static void toFindByEmail(Context context, String mobile, String tipEmail) {
//        Intent intent = new Intent(context, FindByEmailActivity.class);
//        intent.putExtra(FindByEmailActivity.EXTRA_KEY_TIP_EMAIL, tipEmail);
//        intent.putExtra(FindByEmailActivity.EXTRA_KEY_MOBILE, mobile);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/find_by_email")
                .withString("mobile", mobile)
                .withString("tip_email", tipEmail)
                .navigation(context);
    }

    /**
     * 跳转到通过人脸识别找回密码的页面
     *
     * @param context
     * @param mobile
     * @param userName
     */
    public static void toFindByFace(Context context, String mobile, String userName) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/facecheck/facecheckfindloginpsd")
                .withString("mobile", mobile)
                .withString("name", userName)
                .navigation(context);
    }


    /**
     * 短信验证码校验成功，跳转到重置登录密码
     *
     * @param context
     * @param mobile
     */
    public static void toResetLoginPsdBySMS(Context context, String mobile, String checkCode) {
//        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, ResetLoginPsdActivity.RESET_PSD_TYPE_BY_SMS);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_SMS_CHECK_CODE, checkCode);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/reset_login_psd")
                .withString("reset_psd_type", "sms")
                .withString("mobile", mobile)
                .withString("sms_check_code", checkCode)
                .navigation(context);
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
//        Intent intent = new Intent(context, ResetLoginPsdActivity.class);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_RESET_PSD_TYPE, ResetLoginPsdActivity.RESET_PSD_TYPE_BY_EMAIL);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_MOBILE, mobile);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL, email);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL_CHECK_CODE, checkCode);
//        intent.putExtra(ResetLoginPsdActivity.EXTRA_EMAIL_CHECK_CODE_SN, sn);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/reset_login_psd")
                .withString("reset_psd_type", "email")
                .withString("mobile", mobile)
                .withString("email", email)
                .withString("email_check_code", checkCode)
                .withString("email_check_code_sn", sn)
                .navigation(context);
    }


    /**
     * 通过微信进行注册或者绑定操作
     *
     * @param context
     * @param wxSn    判断微信是否绑定过手机的交易流水号
     */
    public static void toRegisterByWXChat(Context context, String wxSn) {
//        Intent intent = new Intent(context, RegisterByWXChatActivity.class);
//        intent.putExtra(RegisterByWXChatActivity.EXTRA_KEY_WX_CHAT_SN, wxSn);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/register_by_wx_chat")
                .withString("wx_chat_sn", wxSn)
                .navigation(context);
    }

    /**
     * 跳转到预加载首页数据的页面
     *
     * @param context
     * @param loadingFailedUrl 预加载失败跳转的页面
     */
    public static void toLoginLoading(Context context, String loadingFailedUrl) {
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/iou/loading")
                .withString("loading_failed_url", loadingFailedUrl)
                .navigation(context);
    }


    /**
     * 跳转到邮箱绑定页面
     *
     * @param context
     */
    public static void toBindEmail(Context context) {
//        Intent intent = new Intent(context, BindEmailActivity.class);
//        context.startActivity(intent);
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/login/bindemail")
                .navigation(context);
    }
}
