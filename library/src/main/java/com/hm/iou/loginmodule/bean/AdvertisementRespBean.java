package com.hm.iou.loginmodule.bean;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by syl on 2018/1/24.
 * 获取当前手机号重置密码的途径
 */

@Data
public class AdvertisementRespBean implements Serializable {
    /**
     * adimageUrl : http://iou-steward.oss-cn-hangzhou.aliyuncs.com/contentCollect/20180530102248
     * title : 核心技术买不来讨不来！这是发展的出路
     * linkUrl : http://m2.people.cn/r/MV8wXzExMDU1Mjk4XzE5MF8xNTI3NTQ3NTE3
     * pushType : android
     * startTime : 2018-05-29 14:27:44
     * endTime : 2018-06-08 00:00:00
     */

    private String adimageUrl;
    private String title;
    private String linkUrl;
    private String pushType;
    private String startTime;
    private String endTime;
    private int showHowLong;

}
