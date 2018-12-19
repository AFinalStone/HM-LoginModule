package com.hm.iou.loginmodule.business.tags;

/**
 * Created by hjy on 2018/12/19.
 */

public interface ITagItem {

    String getTagName();

    String getTagId();

    int getTextColor();

    int getBgRedId();

    boolean isSelected();

    void toggle();
}
