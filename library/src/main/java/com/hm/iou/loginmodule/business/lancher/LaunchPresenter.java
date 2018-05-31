package com.hm.iou.loginmodule.business.lancher;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.AdvertisementRespBean;
import com.hm.iou.sharedata.UserManager;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author syl
 * @time 2018/5/31 下午3:01
 */
public class LaunchPresenter extends MvpActivityPresenter<LaunchContract.View> implements LaunchContract.Presenter {

    private Boolean mHaveLogin = false;
    private int countDownTime = 3;

    public LaunchPresenter(@NonNull Context context, @NonNull LaunchContract.View view) {
        super(context, view);
    }

    /**
     * 开启倒计时
     */
    public void startCountDown() {
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(countDownTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return countDownTime - aLong;
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        checkUserHaveLogin();
                    }
                })
                .subscribeWith(new CommSubscriber<Long>(mView) {
                    @Override
                    public void handleResult(Long aLong) {
                        String desc = "跳过（" + (aLong - 1) + "）";
                        mView.setJumpBtnText(desc);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        throwable.printStackTrace();
                    }
                });


    }

    @Override
    public void init() {
        AdvertisementRespBean adBean = CacheDataUtil.getAdvertisement(mContext);
        if (adBean != null) {
            mView.showAdvertisement(adBean.getAdimageUrl(), adBean.getLinkUrl());
            startCountDown();
        } else {
            checkUserHaveLogin();
        }
        LoginModuleApi.getAdvertisement()
                .map(RxUtil.<List<AdvertisementRespBean>>handleResponse())
                .subscribeWith(new CommSubscriber<List<AdvertisementRespBean>>(mView) {
                    @Override
                    public void handleResult(List<AdvertisementRespBean> list) {
                        CacheDataUtil.cacheAdvertisementList(mContext, list);
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }
                });
    }

    @Override
    public void checkUserHaveLogin() {
        if (mHaveLogin) {
            return;
        }
        mHaveLogin = true;
        if (UserManager.getInstance(mContext).isLogin()) {
            NavigationHelper.toLoginLoading(mContext, true);
        } else {
            NavigationHelper.toGuide(mContext);
        }
        mView.closeCurrPage();
    }

}
