package com.hm.iou.loginmodule.business.guide.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hm.iou.loginmodule.R;

import java.util.List;

public class GuidePagerAdapter extends PagerAdapter {

    private List<Integer> mPicListData;
    private Context mContext;

    public GuidePagerAdapter(Context context, List<Integer> mPicListData) {
        this.mContext = context;
        this.mPicListData = mPicListData;
    }

    @Override
    public int getCount() {
        if (mPicListData == null) {
            return 0;
        }
        return mPicListData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.loginmodule_item_guide_pager, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(mPicListData.get(position));
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

}