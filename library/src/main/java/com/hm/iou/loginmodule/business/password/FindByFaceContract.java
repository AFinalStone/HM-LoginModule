package com.hm.iou.loginmodule.business.password;


import com.hm.iou.base.mvp.BaseContract;

/**
 * 通过活体校验找回密码
 *
 * @author syl
 * @time 2018/5/17 下午1:54
 */
public interface FindByFaceContract {

    interface View extends BaseContract.BaseView {

        /**
         * 请求相机权限并跳转到人脸识别的页面
         */
        void toScanFace();

        /**
         * 校验失败
         */
        void warnCheckFailed();

    }

    interface Present extends BaseContract.BasePresenter {

        /**
         * 校验身份证前6位
         *
         * @param mobile    手机号
         * @param idCardNum 身份证前6位
         */
        void checkIdCardWithoutLogin(String mobile, String idCardNum);

        /**
         * 活体校验
         *
         * @param mobile
         * @param idCardNum
         * @param imagePath
         */
        void faceCheckWithoutLogin(String mobile, String idCardNum, String imagePath);

    }

}
