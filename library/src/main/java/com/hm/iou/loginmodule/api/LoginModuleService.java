package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.IsWXExistResp;
import com.hm.iou.loginmodule.bean.ResetPsdMethodBean;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hjy on 18/4/27.<br>
 */

public interface LoginModuleService {

    @GET("/acct/isAccountExist")
    Flowable<BaseResponse<Integer>> isAccountExist(@Query("loginName") String loginName);

    @POST("/mmc/sendSmsCheckCode")
    @FormUrlEncoded
    Flowable<BaseResponse<Integer>> sendSmsCheckCode(@Field("mobile") String mobile);

    @POST("/acct/mobileRegLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileRegLogin(@Field("loginName") String loginName
            , @Field("queryPswd") String queryPswd, @Field("checkCode") String checkCode);

    @POST("/acct/mobileLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileLogin(@Field("loginName") String userPhone, @Field("queryPswd") String queryPswd);

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
