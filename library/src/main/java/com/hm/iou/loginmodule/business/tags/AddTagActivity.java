package com.hm.iou.loginmodule.business.tags;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.file.FileUtil;
import com.hm.iou.base.photo.CompressPictureUtil;
import com.hm.iou.base.photo.ImageCropper;
import com.hm.iou.base.photo.PhotoUtil;
import com.hm.iou.logger.Logger;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.bean.UserTagBean;
import com.hm.iou.tools.DensityUtil;
import com.hm.iou.tools.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hjy on 2018/12/19.
 */

public class AddTagActivity extends BaseActivity<AddTagPresenter> implements AddTagContract.View {

    public static final String EXTRA_KEY_TAG_LIST = "tag_list";

    private static final int REQ_CODE_ALBUM = 10;

    @BindView(R2.id.iv_tag_avatar)
    ImageView mIvAvatar;
    @BindView(R2.id.tv_tag_change)
    TextView mTvChangeAvatarTip;
    @BindView(R2.id.tv_tag_nickname)
    TextView mTvNickname;
    @BindView(R2.id.rv_tag_list)
    RecyclerView mRvTagList;
    @BindView(R2.id.btn_tag_submit)
    Button mBtnSubmit;
    @BindView(R2.id.tv_tag_error_hint)
    TextView mTvErrorHint;

    private ArrayList<UserTagBean> mTagList;

    private TagAdapter mTagAdapter;
    private ModifyNameDialog mDialog;

    private ImageCropper mImageCropper;

    private String mNewNickname;
    private File mAvatarFile;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_add_tag;
    }

    @Override
    protected AddTagPresenter initPresenter() {
        return new AddTagPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        mTagList = getIntent().getParcelableArrayListExtra(EXTRA_KEY_TAG_LIST);
        if (bundle != null) {
            mTagList = bundle.getParcelableArrayList(EXTRA_KEY_TAG_LIST);
        }

        mTagAdapter = new TagAdapter();
        List<ITagItem> list = new ArrayList<>();
        if (mTagList != null) {
            for (UserTagBean bean : mTagList) {
                list.add(bean);
            }
        }
        mTagAdapter.setNewData(list);
        mRvTagList.setLayoutManager(new GridLayoutManager(this, 3));
        mRvTagList.setAdapter(mTagAdapter);
        mTagAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ITagItem item = (ITagItem) adapter.getItem(position);
                item.toggle();
                mTagAdapter.notifyDataSetChanged();
                int c = mTagAdapter.getSelectedSize();
                if (c >= 2) {
                    updateBtnBackground(R.drawable.uikit_shape_common_btn_selector);
                    showErrorMsg(View.INVISIBLE);
                } else {
                    updateBtnBackground(R.drawable.uikit_shape_common_btn_unenable);
                    showErrorMsg(View.VISIBLE);
                }
            }
        });

        mPresenter.init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_KEY_TAG_LIST, mTagList);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ALBUM) {
            if (resultCode == RESULT_OK) {
                String path = PhotoUtil.getPath(this, data.getData());
                initImageCropper();
                mImageCropper.crop(path, 150, 150, false, "crop");
            }
        }
    }

    @OnClick(value = {R2.id.btn_tag_submit, R2.id.tv_tag_nickname, R2.id.iv_tag_avatar})
    void onClick(View v) {
        if (v.getId() == R.id.btn_tag_submit) {
            //领取大礼包
            Logger.d("click submit");
            mPresenter.submitData(mTagAdapter.getTagIdList(), mAvatarFile, mNewNickname);
        } else if (v.getId() == R.id.tv_tag_nickname) {
            //点此设置昵称
            if (mDialog == null) {
                mDialog = new ModifyNameDialog(this);
                mDialog.setOnModifyNameListener(new ModifyNameDialog.OnModifyNameListener() {
                    @Override
                    public void onClickSend(String content) {
                        mNewNickname = content;
                        updateNickname(content);
                    }
                });
            }
            mDialog.show();
        } else if (v.getId() == R.id.iv_tag_avatar) {
            PhotoUtil.openAlbum(this, REQ_CODE_ALBUM);
        }
    }

    private void initImageCropper() {
        if (mImageCropper == null) {
            int displayDeviceHeight = getResources().getDisplayMetrics().heightPixels - DensityUtil.dip2px(this, 53);
            mImageCropper = ImageCropper.Helper.with(this).setTranslucentStatusHeight(displayDeviceHeight).create();
            mImageCropper.setCallback(new ImageCropper.Callback() {
                @Override
                public void onPictureCropOut(Bitmap bitmap, String tag) {
                    File fileCrop = new File(FileUtil.getExternalCacheDirPath(mContext) + File.separator + System.currentTimeMillis() + ".jpg");
                    CompressPictureUtil.saveBitmapToTargetFile(fileCrop, bitmap, Bitmap.CompressFormat.JPEG);
                    compressPic(fileCrop.getAbsolutePath());
                }
            });
        }
    }

    private void compressPic(final String fileUrl) {
        CompressPictureUtil.compressPic(this, fileUrl, new CompressPictureUtil.OnCompressListener() {
            public void onCompressPicSuccess(File file) {
                mAvatarFile = file;
                showChangeAvatarTip(View.INVISIBLE);
                updateAvatar("file://" + file.getAbsolutePath());
            }
        });
    }

    @Override
    public void updateNickname(String nickname) {
        mTvNickname.setText(nickname);
    }

    @Override
    public void showChangeAvatarTip(int visibility) {
        mTvChangeAvatarTip.setVisibility(visibility);
    }

    @Override
    public void updateAvatar(String url) {
        ImageLoader.getInstance(this).displayImage(url, mIvAvatar, R.drawable.uikit_bg_pic_loading_place,
                R.drawable.uikit_bg_pic_loading_error);
    }

    @Override
    public void updateBtnBackground(int resId) {
        mBtnSubmit.setBackgroundResource(resId);
    }

    @Override
    public void showErrorMsg(int visibility) {
        mTvErrorHint.setVisibility(visibility);
    }

    @Override
    public void toDataLoadingPage() {
        NavigationHelper.toLoginLoading(this, "hmiou://m.54jietiao.com/login/selecttype");
    }

    class TagAdapter extends BaseQuickAdapter<ITagItem , BaseViewHolder> {

        public TagAdapter() {
            super(R.layout.loginmodule_item_user_tag);
        }

        public int getSelectedSize() {
            int c = 0;
            if (mData != null) {
                for (ITagItem item : mData) {
                    if (item.isSelected()) {
                        c++;
                    }
                }
            }
            return c;
        }

        public List<String> getTagIdList() {
            List<String> list = new ArrayList<>();
            if (mData != null) {
                for (ITagItem item : mData) {
                    if (item.isSelected()) {
                        list.add(item.getTagId());
                    }
                }
            }
            return list;
        }

        @Override
        protected void convert(BaseViewHolder helper, ITagItem item) {
            helper.setText(R.id.tv_tag_name, item.getTagName());
            helper.addOnClickListener(R.id.tv_tag_name);
            TextView tvTagName = helper.getView(R.id.tv_tag_name);
            tvTagName.setTextColor(item.getTextColor());
            tvTagName.setBackgroundResource(item.getBgRedId());
        }
    }

}