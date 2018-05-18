package com.hm.iou.loginmodule.bean;


import lombok.Data;

/**
 * Created by syl on 2018/1/24.
 * 获取当前手机号重置密码的途径
 */

@Data
public class ResetPsdMethodBean {

    //Mail(邮箱), Real(人脸), Sms(短信);
    String method;
    String field;

}
