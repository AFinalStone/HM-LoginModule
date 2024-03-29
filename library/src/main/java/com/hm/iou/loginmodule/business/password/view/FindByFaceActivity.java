package com.hm.iou.loginmodule.business.password.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.PermissionUtil;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.logger.Logger;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindByFaceContract;
import com.hm.iou.loginmodule.business.password.presenter.FindByFacePresenter;
import com.hm.iou.router.Router;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.uikit.HMTopBarView;
import com.hm.iou.uikit.keyboard.input.HMInputCodeView;
import com.hm.iou.uikit.keyboard.input.OnInputCodeListener;
import com.hm.iou.uikit.keyboard.key.NumberKey;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import lombok.val;


/**
 * 通过活体校验找回密码
 *
 * @author AFinalStone
 * @time 2018/3/2 下午5:46
 */
public class FindByFaceActivity extends BaseActivity<FindByFacePresenter> implements FindByFaceContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";
    public static final String EXTRA_KEY_USER_NAME = "name";

    /**
     * 活体认证，人脸识别请求码
     */
    private static final int CODE_REQ_SCAN_FACE = 100;
    //通过商汤进行活体识别
    private static final int CODE_REQ_SENSTIME_SCANFACE = 101;

    @BindView(R2.id.topBar)
    HMTopBarView mTopBar;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;

    @BindView(R2.id.inputCodeView)
    HMInputCodeView mInputCodeView;

    //用户输入的手机号和用户脱敏姓名
    private String mMobile;
    private String mUserName;
    //用户输入的身份证前六位
    private String mIDCardNum;

    private boolean mHasCameraPermission;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_face_check_find_login_psd;
    }

    @Override
    protected FindByFacePresenter initPresenter() {
        return new FindByFacePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mMobile = intent.getStringExtra(EXTRA_KEY_MOBILE);
        mUserName = intent.getStringExtra(EXTRA_KEY_USER_NAME);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
            mUserName = savedInstanceState.getString(EXTRA_KEY_USER_NAME);
        }
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
        outState.putString(EXTRA_KEY_USER_NAME, mUserName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (RESULT_OK == resultCode && CODE_REQ_SCAN_FACE == requestCode) {
            String imagePath = data.getStringExtra("extra_result_image_path");
            if (!TextUtils.isEmpty(imagePath)) {
                Logger.d("imagePath===" + imagePath);
                mPresenter.faceCheckWithoutLogin(mMobile, mIDCardNum, imagePath);
            }
            return;
        }
        if (CODE_REQ_SENSTIME_SCANFACE == requestCode && resultCode == RESULT_OK) {
            boolean hasError = data.getBooleanExtra("result_deal_error_inner", false);
            Logger.d("活体检测结果：$hasError");
            if (!hasError) {
                String requestId = data.getStringExtra("request_id");
                String imagePath = data.getStringExtra("extra_result_image_path");
                if (!TextUtils.isEmpty(requestId) && !TextUtils.isEmpty(imagePath)) {
                    mPresenter.senseTimeFaceCheckWithoutLogin(mMobile, mIDCardNum, imagePath, requestId);
                }
            } else {
                toastErrorMessage("活体检测出现错误");
            }
            return;
        }
    }


    private void initView() {
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                RouterUtil.toSubmitFeedback(FindByFaceActivity.this, "Find_Pwd_By_Face", "About_Person_Auth");
            }

            @Override
            public void onClickImageMenu() {

            }
        });

        mInputCodeView.bindKeyBoardView(getWindow(), new NumberKey(mContext));
        mInputCodeView.setOnInputCodeListener(new OnInputCodeListener() {
            @Override
            public void onInputCodeFinish(String psd) {
                mIDCardNum = psd;
                mPresenter.checkIdCardWithoutLogin(mMobile, mIDCardNum);
            }

            @Override
            public void onDelete() {

            }
        });

        String strTitle = getString(R.string.loginmodule_please_input_id_card_sub_six);
        if (StringUtil.isEmpty(mUserName)) {
            mUserName = getString(R.string.loginmodule_current_user_name);
        }
        strTitle = String.format(strTitle, mUserName);
        mTvTitle.setText(strTitle);
    }

    @Override
    public void toScanFace() {
        RxPermissions rxPermissions = new RxPermissions(this);
        boolean hasPermission = rxPermissions.isGranted(Manifest.permission.CAMERA);
        if (hasPermission) {
            Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/facecheck/scan_face")
                    .withString("is_return_video", "false")
                    .withString("is_return_encrypted", "false")
                    .navigation(mContext, CODE_REQ_SCAN_FACE);
        } else {
            mHasCameraPermission = false;
            rxPermissions.request(Manifest.permission.CAMERA)
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            if (!mHasCameraPermission) {
                                PermissionUtil.showCameraPermissionDialog(FindByFaceActivity.this, null);
                            } else {
                                Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/facecheck/scan_face")
                                        .withString("is_return_video", "false")
                                        .withString("is_return_encrypted", "false")
                                        .navigation(mContext, CODE_REQ_SCAN_FACE);
                            }
                        }
                    })
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            mHasCameraPermission = aBoolean;
                        }
                    });
        }

    }

    @Override
    public void toScanFaceBySenseTime() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean == true) {
                            Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/facecheck/st/scan_face")
                                    .navigation(FindByFaceActivity.this, CODE_REQ_SENSTIME_SCANFACE);
                        } else {
                            if (!rxPermissions.isGranted(Manifest.permission.CAMERA)) {
                                PermissionUtil.showCameraPermissionDialog(FindByFaceActivity.this, null);
                            } else if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                PermissionUtil.showStoragePermissionDialog(FindByFaceActivity.this, null);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable t) throws Exception {
                    }
                });
    }

    @Override
    public void warnCheckFailed() {
        mInputCodeView.clearInputCode();
        mInputCodeView.setError();
    }

}