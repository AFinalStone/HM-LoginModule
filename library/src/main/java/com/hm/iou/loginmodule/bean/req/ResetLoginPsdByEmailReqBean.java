package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class ResetLoginPsdByEmailReqBean {

    private String newPwd;
    private String receiverEmail;
    private String sn;
    private String validateCode;
}
