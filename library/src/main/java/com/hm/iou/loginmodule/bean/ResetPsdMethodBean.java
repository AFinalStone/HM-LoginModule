package com.hm.iou.loginmodule.bean;


import lombok.Data;

/**
 * Created by syl on 2018/1/24.
 * 获取当前手机号重置密码的途径
 */

@Data
public class ResetPsdMethodBean {

    //1(邮箱), 2(人脸), 3(短信);
    int method;
    String field;

}
