package com.hm.iou.loginmodule.bean.req;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
public class TokenLoginReqBean {


    /**
     * token : string
     * userId : 0
     */

    private String token;
    private String userId;

    public TokenLoginReqBean(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }
}
