package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class CompareSMSCheckCodeReqBean {


    /**
     * checkCode : string
     * mobile : string
     */

    private String checkCode;
    private String mobile;

}
