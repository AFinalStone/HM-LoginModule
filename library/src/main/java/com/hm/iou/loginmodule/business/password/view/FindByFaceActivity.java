package com.hm.iou.loginmodule.business.password.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
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

import java.util.ArrayList;

import butterknife.BindView;


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

    @Override
    protected int getLayoutId() {
        return R.layout.facecheck_activity_face_check_find_login_psd;
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
        }
    }


    private void initView() {
        mTopBar.setOnMenuClickListener(new HMTopBarView.OnTopBarMenuClickListener() {
            @Override
            public void onClickTextMenu() {
                Router.getInstance()
                        .buildWithUrl("hmiou://m.54jietiao.com/login/customer_service")
                        .navigation(mContext);
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
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/facecheck/scan_face")
                .navigation(mContext, CODE_REQ_SCAN_FACE);
    }

    @Override
    public void warnCheckFailed() {
        mInputCodeView.clearInputCode();
        mInputCodeView.setError();
    }

}