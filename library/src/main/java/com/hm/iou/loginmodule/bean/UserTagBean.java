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
public class UserTagBean implements Parcelable, ITagItem {

    String tagId;
    String tagName;

    boolean selected;

    @Override
    public int getTextColor() {
        return selected ? 0xffffffff : 0xff000a00;
    }

    @Override
    public int getBgRedId() {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tagId);
        dest.writeString(this.tagName);
    }

    protected UserTagBean(Parcel in) {
        this.tagId = in.readString();
        this.tagName = in.readString();
    }

    public static final Parcelable.Creator<UserTagBean> CREATOR = new Parcelable.Creator<UserTagBean>() {
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
