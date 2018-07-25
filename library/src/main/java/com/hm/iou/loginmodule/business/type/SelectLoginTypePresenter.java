package com.hm.iou.loginmodule.business.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.event.OpenWxResultEvent;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxJavaStopException;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.base.version.CheckVersionResBean;
import com.hm.iou.base.version.VersionApi;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.IsWXExistRespBean;
import com.hm.iou.loginmodule.business.BaseLoginModulePresenter;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.SystemUtil;
import com.hm.iou.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * 选择登录方式
 *
 * @author syl
 * @time 2018/5/17 下午5:24
 */
public class SelectLoginTypePresenter extends BaseLoginModulePresenter<SelectLoginTypeContract.View> implements SelectLoginTypeContract.Presenter {

    private static final String PACKAGE_NAME_OF_WX_CHAT = "com.tencent.mm";
    private static final String KEY_OPEN_WX_CHAT_GET_COE = "loginmodule.selectLoginType";

    private IWXAPI mWXApi;

    public SelectLoginTypePresenter(@NonNull Context context, @NonNull SelectLoginTypeContract.View view) {
        super(context, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXApi != null) {
            mWXApi.detach();
            mWXApi = null;
        }
    }

    private void isWXAccountHaveBindMobile(String code) {
        mView.showLoadingView();
        LoginModuleApi.isWXExist(code)
                .compose(getProvider().<BaseResponse<IsWXExistRespBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IsWXExistRespBean>handleResponse())
                .flatMap(new Function<IsWXExistRespBean, Publisher<BaseResponse<UserInfo>>>() {
                    @Override
                    public Publisher<BaseResponse<UserInfo>> apply(IsWXExistRespBean resp) throws Exception {
                        String wxSn = resp.getSn();
                        if (resp.getCount() == 0) {
                            mView.dismissLoadingView();
                            //微信没有绑定过手机号
                            NavigationHelper.toRegisterByWXChat(mContext, wxSn);
                            return Flowable.error(new RxJavaStopException());
                        }
                        return LoginModuleApi.wxLogin(wxSn);
                    }
                })
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        mView.dismissLoadingView();
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        NavigationHelper.toLoginLoading(mContext,"hmiou://m.54jietiao.com/login/selecttype");
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void checkVersion() {
        VersionApi.checkVersion()
                .compose(getProvider().<BaseResponse<CheckVersionResBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<CheckVersionResBean>handleResponse())
                .subscribeWith(new CommSubscriber<CheckVersionResBean>(mView) {
                    @Override
                    public void handleResult(CheckVersionResBean data) {
                        if (data == null || TextUtils.isEmpty(data.getDownloadUrl())) {
                            return;
                        }
                        if (data.getType() != 2 && data.getType() != 3) {
                            return;
                        }
                        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/homedialog")
                                .withString("dialog_type", data.getType() + "")
                                .withString("dialog_title", data.getTitile())
                                .withString("dialog_content", data.getContent())
                                .withString("dialog_sub_content", data.getSubContent())
                                .withString("dialog_file_down_url", data.getDownloadUrl())
                                .withString("dialog_file_md5", data.getFileMD5())
                                .navigation(mContext);
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });
    }

    /**
     * 打开微信,获取code
     */
    @Override
    public void getWxCode() {
        if (mWXApi != null) {
            mWXApi.detach();
        }
        mWXApi = WXEntryActivity.openWxAuth(mContext, KEY_OPEN_WX_CHAT_GET_COE);
    }

    @Override
    public void isInstalledWxChatAPP() {
        boolean flag = SystemUtil.isAppInstalled(mContext, PACKAGE_NAME_OF_WX_CHAT);
        if (!flag) {
            mView.hideButtonForLoginByWx();
        }
    }

    /**
     * 打开微信获取到的code
     *
     * @param openWxResultEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusOpenWXResult(OpenWxResultEvent openWxResultEvent) {
        if (KEY_OPEN_WX_CHAT_GET_COE.equals(openWxResultEvent.getKey())) {
            isWXAccountHaveBindMobile(openWxResultEvent.getCode());
        }
    }


}
