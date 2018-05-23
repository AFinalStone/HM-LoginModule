package com.hm.iou.loginmodule.bean;

import lombok.Data;

@Data
public class IsBindWXRespBean {
    /**
     * 用户数目,-1：用户不存在，0：用户未绑定，1：用户已绑定
     */
    int count;
}
