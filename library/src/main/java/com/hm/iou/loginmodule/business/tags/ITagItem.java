package com.hm.iou.loginmodule.business.tags;

/**
 * Created by hjy on 2018/12/19.
 */

public interface ITagItem {

    String getTagName();

    int getTagId();

    int getTextColor();

    int getBgResId();

    boolean isSelected();

    void toggle();
}
