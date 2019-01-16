package com.hm.iou.loginmodule.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.business.tags.ITagItem;

import lombok.Data;

/**
 * Created by hjy on 2018/12/20.
 */
@Data
public class UserTagBean implements ITagItem, Parcelable {

    int labelId;
    String name;

    boolean selected;

    @Override
    public int getTextColor() {
        return selected ? 0xffffffff : 0xff9b9b9b;
    }

    @Override
    public int getBgResId() {
        return selected ? R.drawable.loginmodule_bg_tag_selected : R.drawable.loginmodule_bg_tag_normal;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void toggle() {
        selected = !selected;
    }

    @Override
    public String getTagName() {
        return name;
    }

    @Override
    public int getTagId() {
        return labelId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.labelId);
        dest.writeString(this.name);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected UserTagBean(Parcel in) {
        this.labelId = in.readInt();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<UserTagBean> CREATOR = new Creator<UserTagBean>() {
        @Override
        public UserTagBean createFromParcel(Parcel source) {
            return new UserTagBean(source);
        }

        @Override
        public UserTagBean[] newArray(int size) {
            return new UserTagBean[size];
        }
    };
}