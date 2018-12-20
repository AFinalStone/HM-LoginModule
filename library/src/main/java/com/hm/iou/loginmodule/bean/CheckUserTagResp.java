package com.hm.iou.loginmodule.bean;

import java.util.List;

import lombok.Data;

/**
 * Created by hjy on 2018/12/20.
 */
@Data
public class CheckUserTagResp {

    boolean needSetLabel;
    List<UserTagBean> labelRespList;

}