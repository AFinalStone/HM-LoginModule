package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.IsWXExistResp;
import com.hm.iou.loginmodule.bean.ResetPsdMethodBean;
import com.hm.iou.loginmodule.bean.req.MobileLoginReqBean;
import com.hm.iou.loginmodule.bean.req.MobileRegLoginReqBean;
import com.hm.iou.loginmodule.bean.req.TokenLoginReqBean;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * @author syl
 * @time 2018/5/21 下午4:50
 */
public interface LoginModuleService {

    @GET("/api/iou/user/v1/isAccountExist")
    Flowable<BaseResponse<Boolean>> isAccountExist(@Query("mobile") String mobile);

    @POST("/api/iou/user/v1/mobileLogin")
    Flowable<BaseResponse<UserInfo>> mobileLogin(@Body MobileLoginReqBean mobileLoginReqBean);

    @POST("/api/iou/user/v1/mobileRegLogin")
    Flowable<BaseResponse<UserInfo>> mobileRegLogin(@Body MobileRegLoginReqBean mobileRegLoginReqBean);

    @POST("/api/iou/user/v1/tokenLogin")
    Flowable<BaseResponse<UserInfo>> tokenLogin(@Body TokenLoginReqBean tokenLoginReqBean);

    @POST("/mmc/sendSmsCheckCode")
    @FormUrlEncoded
    Flowable<BaseResponse<Boolean>> sendSmsCheckCode(@Field("mobile") String mobile);

    @POST("/mmc/sendResetPswdCheckCodeSMS")
    @FormUrlEncoded
    Flowable<BaseResponse<String>> sendResetPswdCheckCodeSMS(@Field("pswdType") String pswdType, @Field("mobile") String mobile);

    @GET("/acct/isWXExist")
    Flowable<BaseResponse<IsWXExistResp>> isWXExist(@Query("code") String code);


    @POST("/acct/wxLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> wxLogin(@Field("wxSn") String wxSn);

    @GET("/acct/isBindWX")
    Flowable<BaseResponse<Integer>> isBindWX(@Query("mobile") String mobile);

    @POST("/acct/mobileRegBindWXLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileRegBindWXLogin(@Field("loginName") String loginName
            , @Field("queryPswd") String queryPswd, @Field("checkCode") String checkCode, @Field("wxSn") String wxSN);

    /**
     * 根据手机号获得客户找回密码的途径
     * 请求报文
     * 变量名称	变量含义	类型	备注
     * pswdType	密码类型  QueryPswd(登录密码), TradePswd(签约密码);
     * mobile	手机号码	String
     * <p>
     * 应答报文
     * 变量名称	变量含义	类型	备注
     * method	找回密码途径	Mail(邮箱), Real(人脸), Sms(短信);
     * filed	冗余字段	String	根据情况填写。如通过邮箱找回密码，返回脱敏邮箱
     */
    @GET("/acct/getResetPswdMethod")
    Flowable<BaseResponse<ResetPsdMethodBean>> getResetPswdMethod(@Query("pswdType") String pswdType, @Query("mobile") String mobile);

}
