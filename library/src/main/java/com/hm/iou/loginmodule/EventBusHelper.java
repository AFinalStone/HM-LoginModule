package com.hm.iou.loginmodule;

import com.hm.iou.sharedata.event.CommBizEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author syl
 * @time 2018/5/19 下午2:59
 */

public class EventBusHelper {

    /**
     * 用户成功绑定了邮箱
     */
    public static void postBindEmailEvent() {
        EventBus.getDefault().post(new CommBizEvent("bindEmailSuccess", "邮箱绑定成功"));
    }


}
