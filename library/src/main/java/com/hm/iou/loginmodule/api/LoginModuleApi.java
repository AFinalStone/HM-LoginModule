package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.GetResetPsdMethodRespBean;
import com.hm.iou.loginmodule.bean.IsBindWXRespBean;
import com.hm.iou.loginmodule.bean.IsWXExistRespBean;
import com.hm.iou.loginmodule.bean.req.BindWXReqBean;
import com.hm.iou.loginmodule.bean.req.MobileLoginReqBean;
import com.hm.iou.loginmodule.bean.req.MobileRegLoginReqBean;
import com.hm.iou.loginmodule.bean.req.SendMessageReqBean;
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
        MobileLoginReqBean reqBean = new MobileLoginReqBean();
        String psdMd5 = Md5Util.getMd5ByString(loginPsd);
        reqBean.setMobile(mobile);
        reqBean.setQueryPswd(psdMd5);
        return getService().mobileLogin(reqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
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
        MobileRegLoginReqBean reqBean = new MobileRegLoginReqBean();
        String psdMd5 = Md5Util.getMd5ByString(loginPsd);
        reqBean.setMobile(mobile);
        reqBean.setQueryPswd(psdMd5);
        reqBean.setCheckCode(checkCode);
        return getService().mobileRegLogin(reqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 令牌登录
     *
     * @return
     */
    public static Flowable<BaseResponse<UserInfo>> tokenLogin(String userId, String token) {
//        TokenLoginReqBean reqBean = new TokenLoginReqBean();
//        reqBean.setUserId(userId);
//        reqBean.setToken(token);
        return getService().tokenLogin().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据手机微信的Code判断当前微信是否已经绑定过账号
     *
     * @param code 微信唯一code
     * @return sn判断微信是否存在的交易流水号 count=0不存在，非0存在
     */
    public static Flowable<BaseResponse<IsWXExistRespBean>> isWXExist(String code) {
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
    public static Flowable<BaseResponse<IsBindWXRespBean>> isBindWX(String mobile) {
        return getService().isBindWX(mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 绑定微信
     *
     * @param mobile
     * @param checkCode
     * @param loginPsd
     * @param wxSn
     * @return
     */
    public static Flowable<BaseResponse<UserInfo>> bindWX(String mobile, String checkCode, String loginPsd, String wxSn) {
        BindWXReqBean reqBean = new BindWXReqBean();
        reqBean.setMobile(mobile);
        reqBean.setCheckCode(checkCode);
        String psdMD5 = Md5Util.getMd5ByString(loginPsd);
        reqBean.setQueryPswd(psdMD5);
        reqBean.setWxSn(wxSn);
        return getService().bindWX(reqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
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
     * @return GetResetPsdMethodRespBean
     * Mail(邮箱), Real(人脸), Sms(短信);
     * field 额外辅助字段
     */
    public static Flowable<BaseResponse<GetResetPsdMethodRespBean>> getResetPswdMethod(String mobile) {
        return getService().getResetPswdMethod("TradePswd", mobile).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 发送短信验证码或者邮箱验证码
     *
     * @param type 用途--1:短信注册码，2:短信重置验证码，3:修改手机号，4:绑定邮箱，5:重置邮箱 ,
     * @param to   手机号码或者邮箱
     * @return
     */
    public static Flowable<BaseResponse<Object>> sendMessage(int type, String to) {
        SendMessageReqBean reqBean = new SendMessageReqBean();
        reqBean.setPurpose(type);
        reqBean.setTo(to);
        return getService().sendMessage(reqBean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过手机验证码重置登录密码
     *
     * @param mobile
     * @param checkCode
     * @param newPsd
     * @return
     */
    public static Flowable<BaseResponse<Integer>> resetQueryPswdBySMS(String mobile, String checkCode, String newPsd) {
        String psdMd5 = Md5Util.getMd5ByString(newPsd);
        return getService().resetQueryPswdBySMS(mobile, checkCode, psdMd5).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}