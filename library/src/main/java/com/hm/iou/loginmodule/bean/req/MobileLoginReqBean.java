package com.hm.iou.loginmodule.bean.req;

import lombok.Data;

/**
 * @author : syl
 * @Date : 2018/5/21 16:49
 * @E-Mail : shiyaolei@dafy.com
 */
@Data
public class MobileLoginReqBean {

    /**
     * mobile : 15267163669
     * queryPswd : 123456
     */

    private String mobile;
    private String queryPswd;
}
