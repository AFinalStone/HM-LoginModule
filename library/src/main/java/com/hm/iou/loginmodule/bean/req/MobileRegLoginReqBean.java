package com.hm.iou.loginmodule.bean.req;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
public class MobileRegLoginReqBean {

    /**
     * mobile : 15267163669
     * queryPswd : 123456
     * checkCode : string
     */

    private String mobile;
    private String queryPswd;
    private String checkCode;

    public MobileRegLoginReqBean(String mobile, String queryPswd, String checkCode) {
        this.mobile = mobile;
        this.queryPswd = queryPswd;
        this.checkCode = checkCode;
    }
}
