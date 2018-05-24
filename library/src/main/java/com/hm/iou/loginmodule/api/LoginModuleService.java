package com.hm.iou.loginmodule.api;

import com.hm.iou.loginmodule.bean.GetResetPsdMethodRespBean;
import com.hm.iou.loginmodule.bean.IsBindWXRespBean;
import com.hm.iou.loginmodule.bean.IsWXExistRespBean;
import com.hm.iou.loginmodule.bean.req.BindEmailReqBean;
import com.hm.iou.loginmodule.bean.req.BindWXReqBean;
import com.hm.iou.loginmodule.bean.req.CheckIDCardReqBean;
import com.hm.iou.loginmodule.bean.req.CompareEmailCheckCodeReqBean;
import com.hm.iou.loginmodule.bean.req.CompareSMSCheckCodeReqBean;
import com.hm.iou.loginmodule.bean.req.MobileLoginReqBean;
import com.hm.iou.loginmodule.bean.req.MobileRegLoginReqBean;
import com.hm.iou.loginmodule.bean.req.ResetLoginPsdByFaceReqBean;
import com.hm.iou.loginmodule.bean.req.ResetLoginPsdBySMSReqBean;
import com.hm.iou.loginmodule.bean.req.ResetLoginPsdByEmailReqBean;
import com.hm.iou.loginmodule.bean.req.SendMessageReqBean;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("/api/iou/user/v1/wxLogin")
    Flowable<BaseResponse<UserInfo>> wxLogin(@Query("wxSn") String wxSn);

    @POST("/acct/mobileRegBindWXLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileRegBindWXLogin(@Field("loginName") String loginName
            , @Field("queryPswd") String queryPswd, @Field("checkCode") String checkCode, @Field("wxSn") String wxSN);

    @POST("/api/base/msg/v1/sendMessage")
    Flowable<BaseResponse<String>> sendMessage(@Body SendMessageReqBean sendMessageReqBean);

    @POST("/api/iou/user/v1/checkIdCardNumWithoutLogin")
    Flowable<BaseResponse<Boolean>> checkIdCardNumWithoutLogin(@Body CheckIDCardReqBean checkIDCardReqBean);

    @POST("/api/iou/user/v1/livenessIdnumberVerificationWithoutLogin")
    Flowable<BaseResponse<String>> livenessIdnumberVerificationWithoutLogin(@Part MultipartBody.Part file, @Part("mobile") RequestBody mobile
            , @Part("idCardNum") RequestBody idCardNum);

    @POST("/api/iou/user/v1/compareCheckCode")
    Flowable<BaseResponse<Integer>> compareSMSCheckCode(@Body CompareSMSCheckCodeReqBean compareSMSCheckCodeReqBean);

    @POST("/api/iou/user/v1/getValidateCode")
    @FormUrlEncoded
    Flowable<BaseResponse<Integer>> getValidateCode(@Field("receiverEmail") String receiverEmail);

    @POST("/api/iou/user/v1/bindEmail")
    Flowable<BaseResponse<String>> bindEmail(@Body BindEmailReqBean bindEmailReqBean);

    @POST("/api/iou/user/v1/checkMailWithVCode")
    Flowable<BaseResponse<String>> compareEmailCheckCode(@Body CompareEmailCheckCodeReqBean compareEmailCheckCodeReqBean);


    @POST("/api/iou/user/v1/resetQueryPswdBySMS")
    Flowable<BaseResponse<Integer>> resetLoginPsdBySMS(@Body ResetLoginPsdBySMSReqBean resetLoginPsdBySMSReqBean);

    @POST("/api/iou/user/v1/resetPWDByEmail")
    Flowable<BaseResponse<Integer>> resetLoginPsdByEmail(@Body ResetLoginPsdByEmailReqBean resetLoginPsdByEmailReqBean);

    @POST("/api/iou/user/v1/resetQueryPswdWithLiveness")
    Flowable<BaseResponse<Integer>> resetLoginPsdByFace(@Body ResetLoginPsdByFaceReqBean resetLoginPsdByFaceReqBean);

}
