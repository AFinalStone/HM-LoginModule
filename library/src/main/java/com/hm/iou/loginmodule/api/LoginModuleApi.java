package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.IsWXExistResp;
import com.hm.iou.loginmodule.bean.ResetPsdMethodBean;
import com.hm.iou.loginmodule.bean.req.MobileLoginReqBean;
import com.hm.iou.loginmodule.bean.req.MobileRegLoginReqBean;
import com.hm.iou.loginmodule.bean.req.TokenLoginReqBean;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.Md5Util;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author syl
 * @time 2018/5/17 上午11:28
 */
public class LoginModuleApi {

    private static LoginModuleService getService() {
        return HttpReqManager.getInstance().getService(LoginModuleService.class);
    }

    /**
     * 判断手机号是否存在
     *
     * @param mobile 手机号
     * @return
     */
    public static Flowable<BaseResponse<Boolean>> isAccountExist(String mobile) {
        return getService().isAccountExist(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过手机号进行登录
     *
     * @param mobile   手机号
     * @param loginPsd 登录密码
     * @return
     */
    public static Flowable<BaseResponse<UserInfo>> mobileLogin(String mobile, String loginPsd) {
        String psdMd5 = Md5Util.getMd5ByString(loginPsd);
        MobileLoginReqBean mobileLoginReqBean = new MobileLoginReqBean(mobile, psdMd5);
        return getService().mobileLogin(mobileLoginReqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过手机号注册并登陆
     *
     * @param mobile    手机号
     * @param loginPsd  登录密码
     * @param checkCode 短信验证码
     * @return 成功返回用户信息
     */
    public static Flowable<BaseResponse<UserInfo>> mobileRegLogin(String mobile, String loginPsd, String checkCode) {
        String psdMd5 = Md5Util.getMd5ByString(loginPsd);
        MobileRegLoginReqBean mobileRegLoginReqBean = new MobileRegLoginReqBean(mobile, psdMd5, checkCode);
        return getService().mobileRegLogin(mobileRegLoginReqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<BaseResponse<UserInfo>> tokenLogin(String userId, String token) {
        TokenLoginReqBean tokenLoginReqBean = new TokenLoginReqBean(userId, token);
        return getService().tokenLogin(tokenLoginReqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据手机微信的Code判断当前微信是否已经绑定过账号
     *
     * @param code 微信唯一code
     * @return sn判断微信是否存在的交易流水号 count=0不存在，非0存在
     */
    public static Flowable<BaseResponse<IsWXExistResp>> isWXExist(String code) {
        return getService().isWXExist(code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 微信登录
     *
     * @param wxSn 判断微信是否存在的交易流水号
     * @return
     */
    public static Flowable<BaseResponse<UserInfo>> wxLogin(String wxSn) {
        return getService().wxLogin(wxSn).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询手机号是否绑定微信
     *
     * @param mobile
     * @return 1存在，0不存在
     */
    public static Flowable<BaseResponse<Integer>> isBindWX(String mobile) {
        return getService().isBindWX(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 输入手机号注册并绑定微信
     *
     * @param mobile    手机号
     * @param loginPsd  登录密码
     * @param checkCode 短信验证码
     * @param wxSN      判断微信是否存在的交易流水号
     * @return
     */
    public static Flowable<BaseResponse<UserInfo>> mobileRegBindWXLogin(String mobile, String loginPsd, String checkCode, String wxSN) {
        return getService().mobileRegBindWXLogin(mobile, loginPsd, checkCode, wxSN).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取重置密码的途径
     *
     * @param mobile 手机号
     * @return ResetPsdMethodBean
     * Mail(邮箱), Real(人脸), Sms(短信);
     * field 额外辅助字段
     */
    public static Flowable<BaseResponse<ResetPsdMethodBean>> getResetPswdMethod(String mobile) {
        return getService().getResetPswdMethod("TradePswd", mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送注册使用的短信验证码
     *
     * @param mobile 手机号
     * @return
     */
    public static Flowable<BaseResponse<Boolean>> sendSmsCheckCode(String mobile) {
        return getService().sendSmsCheckCode(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 1.发送微信注册使用的短信验证码
     * 2.发送微信绑定使用的短信验证码
     * 3.发送重置登录密码的短信验证码
     *
     * @param mobile 手机号
     * @return
     */
    public static Flowable<BaseResponse<String>> sendResetPswdCheckCodeSMS(String mobile) {
        String psdType = "QueryPswd";
        return getService().sendResetPswdCheckCodeSMS(psdType, mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}