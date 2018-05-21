package com.hm.iou.loginmodule.bean.req;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
public class MobileLoginReqBean {

    /**
     * mobile : 15267163669
     * queryPswd : 123456
     */

    private String mobile;
    private String queryPswd;

    public MobileLoginReqBean(String mobile, String queryPswd) {
        this.mobile = mobile;
        this.queryPswd = queryPswd;
    }
}
