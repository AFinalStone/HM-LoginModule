package com.hm.iou.loginmodule.business.launch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.AdvertisementRespBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.sharedata.UserManager;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 初始化
 * 1.用户没有登录过，直接跳转到引导页
 * 2.用户已经成功登录过，校验本地是否有广告缓存，没有，直接进入首页
 * 3.如果本地有广告缓存，显示广告，并进行倒计时
 * 获取广告并进行本地缓存
 *
 * @author syl
 * @time 2018/5/31 下午3:01
 */
public class LaunchPresenter implements LaunchContract.Presenter {

    private Context mContext;
    private LaunchContract.View mView;

    private long mCountDownTime = 3;
    private boolean mIsHaveOpenMain = false;
    private Disposable mCountDownDisposable;

    public LaunchPresenter(@NonNull Context context, @NonNull LaunchContract.View view) {
        mContext = context.getApplicationContext();
        mView = view;
    }

    @Override
    public void onDestroy() {
        pauseCountDown();
        mView = null;
    }

    /**
     * 开启倒计时
     */
    public void startCountDown() {
        pauseCountDown();
        mCountDownDisposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(mCountDownTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return mCountDownTime - aLong;
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        delayToMainPage();
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        String desc = aLong + " 跳过";
                        if (mView != null) {
                            mView.setJumpBtnText(desc);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toMain();
                    }
                });

    }

    @Override
    public void init() {
        LoginModuleApi.getAdvertisement()
                .map(RxUtil.<List<AdvertisementRespBean>>handleResponse())
                .subscribe(new Consumer<List<AdvertisementRespBean>>() {
                    @Override
                    public void accept(List<AdvertisementRespBean> list) throws Exception {
                        CacheDataUtil.cacheAdvertisementList(mContext.getApplicationContext(), list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        if (UserManager.getInstance(mContext).isLogin()) {
            AdvertisementRespBean adBean = CacheDataUtil.getAdvertisement(mContext.getApplicationContext());
            if (adBean != null) {
                mView.showAdvertisement(adBean.getAdimageUrl(), adBean.getLinkUrl());
                startCountDown();
            } else {
                delayToMainPage();
            }
        } else {
            delayToGuidePage();
        }
    }

    @Override
    public void pauseCountDown() {
        if (mCountDownDisposable != null && !mCountDownDisposable.isDisposed()) {
            mCountDownDisposable.dispose();
        }
    }

    @Override
    public void toMain() {
        if (!mIsHaveOpenMain) {
            mIsHaveOpenMain = true;
            NavigationHelper.toMain(mContext);
            if (mView != null) {
                mView.closeCurrPage();
            }
        }
    }

    @Override
    public void toAdDetail(String linkUrl) {
        if (!TextUtils.isEmpty(linkUrl)) {
            mIsHaveOpenMain = true;
            NavigationHelper.toLaunchAdvertisement(mContext, linkUrl);
            mView.closeCurrPage();
        }
    }

    /**
     * 延时跳转到首页
     */
    private void delayToMainPage() {
        Flowable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        toMain();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toMain();
                    }
                });
    }

    /**
     * 延时跳转到引导页
     */
    private void delayToGuidePage() {
        Flowable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        NavigationHelper.toGuide(mContext);
                        if (mView != null) {
                            mView.closeCurrPage();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        NavigationHelper.toGuide(mContext);
                        if (mView != null) {
                            mView.closeCurrPage();
                        }
                    }
                });
    }

}
