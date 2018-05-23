package com.hm.iou.loginmodule.bean;


import lombok.Data;

/**
 * Created by syl on 2018/1/24.
 * 获取当前手机号重置密码的途径
 */

@Data
public class GetResetPsdMethodRespBean {

    //Mail(1:邮件方式), Real(2:人脸识别方式), Sms(3:短信方式)
    int method;
    String field;

}
