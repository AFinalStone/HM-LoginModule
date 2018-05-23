package com.hm.iou.loginmodule.business.password.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.loginmodule.business.password.FindByFaceContract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class FindByFacePresenter extends BaseLoginModulePresenter<FindByFaceContract.View> implements FindByFaceContract.Present {

    public FindByFacePresenter(@NonNull Context context, @NonNull FindByFaceContract.View view) {
        super(context, view);
    }

    public boolean saveBitmapToTargetFile(File target, Bitmap bitmap) {
        try {
            FileOutputStream out = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void checkIdCard(String mobile, String idCardNum) {
        mView.showLoadingView();
        LoginModuleApi.checkIDCard(mobile, idCardNum)
                .compose(getProvider().<BaseResponse<Boolean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<Boolean>handleResponse())
                .subscribeWith(new CommSubscriber<Boolean>(mView) {
                    @Override
                    public void handleResult(Boolean aBoolean) {
                        mView.dismissLoadingView();
                        if (aBoolean) {
                            mView.toScanFace();
                        } else {
                            mView.toastMessage(R.string.loginmodule_check_Id_card_failed);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    @Override
    public void livingCheckWithoutLogin(final String mobile, final String idCardNum, Bitmap bitmap) {
        mView.showLoadingView();
        String path = mContext.getCacheDir().getAbsolutePath() + "/livingCheck.png";
        File file = new File(path);
        saveBitmapToTargetFile(file, bitmap);
        LoginModuleApi.livingCheckWithoutLogin(mobile, idCardNum, file)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String livingCheckSN) {
                        mView.dismissLoadingView();
                        NavigationHelper.toResetLoginPsdByFace(mContext, mobile, idCardNum, livingCheckSN);
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        NavigationHelper.toLivingCheckFailed(mContext, msg);
                    }
                });
    }
}
