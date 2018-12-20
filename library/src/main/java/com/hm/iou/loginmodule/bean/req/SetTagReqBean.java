package com.hm.iou.loginmodule.bean.req;

import java.util.List;

import lombok.Data;

/**
 * Created by hjy on 2018/12/20.
 */
@Data
public class SetTagReqBean {

    String avatarUrl;

    String nickName;

    List<Integer> lableIdList;

}
