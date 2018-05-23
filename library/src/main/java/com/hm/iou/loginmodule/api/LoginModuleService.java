package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.GetResetPsdMethodRespBean;
import com.hm.iou.loginmodule.bean.IsBindWXRespBean;
import com.hm.iou.loginmodule.bean.IsWXExistRespBean;
import com.hm.iou.loginmodule.bean.req.BindWXReqBean;
import com.hm.iou.loginmodule.bean.req.MobileLoginReqBean;
import com.hm.iou.loginmodule.bean.req.MobileRegLoginReqBean;
import com.hm.iou.loginmodule.bean.req.SendMessageReqBean;
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

    @GET("/api/iou/user/v1/tokenLogin")
    Flowable<BaseResponse<UserInfo>> tokenLogin();

    @GET("/api/iou/user/v1/isWXExist")
    Flowable<BaseResponse<IsWXExistRespBean>> isWXExist(@Query("code") String code);

    @GET("/api/iou/user/v1/isBindWX")
    Flowable<BaseResponse<IsBindWXRespBean>> isBindWX(@Query("mobile") String mobile);

    @POST("/api/iou/user/v1/bindWX")
    Flowable<BaseResponse<UserInfo>> bindWX(@Body BindWXReqBean bindWXReqBean);

    @GET("/api/iou/user/acct/getResetPswdMethod")
    Flowable<BaseResponse<GetResetPsdMethodRespBean>> getResetPswdMethod(@Query("pswdType") String pswdType, @Query("mobile") String mobile);

    @POST("/api/iou/user/v1/resetQueryPswdBySMS")
    @FormUrlEncoded
    Flowable<BaseResponse<Integer>> resetQueryPswdBySMS(@Field("mobile") String mobile, @Field("checkCode") String checkCode, @Field("newPswd") String newPswd);

    @GET("/api/iou/user/v1/wxLogin")
    Flowable<BaseResponse<UserInfo>> wxLogin(@Query("wxSn") String wxSn);

    @POST("/acct/mobileRegBindWXLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileRegBindWXLogin(@Field("loginName") String loginName
            , @Field("queryPswd") String queryPswd, @Field("checkCode") String checkCode, @Field("wxSn") String wxSN);


    @POST("/api/base/msg/v1/sendMessage")
    Flowable<BaseResponse<Object>> sendMessage(@Body SendMessageReqBean sendMessageReqBean);

}
