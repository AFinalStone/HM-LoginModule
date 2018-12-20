package com.hm.iou.loginmodule.event;

/**
 * Created by hjy on 2018/12/20.
 */

public class InitEvent {

    int type;

    public InitEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
