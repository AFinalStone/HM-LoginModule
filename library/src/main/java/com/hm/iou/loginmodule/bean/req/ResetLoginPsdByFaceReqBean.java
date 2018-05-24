package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class ResetLoginPsdByFaceReqBean {


    /**
     * idCardNum : string
     * livenessIdnumberVerificationSn : string
     * mobile : string
     * queryPswd : string
     */

    private String idCardNum;
    private String livenessIdnumberVerificationSn;
    private String mobile;
    private String queryPswd;
}
