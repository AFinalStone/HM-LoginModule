package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/22 20:39
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class SendMessageReqBean {

    /**
     * p (integer): 用途--1:短信注册码，2:短信重置验证码，3:修改手机号，4:绑定邮箱，5:重置邮箱 ,
     */
    private int purpose;
    /**
     * to (string): 手机号码或者邮箱
     */
    private String to;

    /**
     * 发送邮箱验证码的时候，需要用到这个参数
     */
    private String mobile;

}
