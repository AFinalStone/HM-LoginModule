package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class BindEmailReqBean {


    /**
     * purpose : 0
     * receiverEmail : string
     * validateCode : 0
     */
    private String receiverEmail;
    private String validateCode;

}
