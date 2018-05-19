package com.hm.iou.loginmodule.business.type;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.event.OpenWxResultEvent;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.IsWXExistResp;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.SystemUtil;
import com.hm.iou.wxapi.WXEntryActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 选择登录方式
 *
 * @author syl
 * @time 2018/5/17 下午5:24
 */
public class SelectLoginTypePresenter extends MvpActivityPresenter<SelectLoginTypeContract.View> implements SelectLoginTypeContract.Presenter {

    private static final String PACKAGE_NAME_OF_WXCHAT = "com.tencent.mm";
    private static final String KEY_OPEN_WXCHAT_GET_COE = "loginmodule.selectLoginType";


    public SelectLoginTypePresenter(@NonNull Context context, @NonNull SelectLoginTypeContract.View view) {
        super(context, view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 打开微信获取到的code
     *
     * @param openWxResultEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenBusOpenWXResult(OpenWxResultEvent openWxResultEvent) {
        if (KEY_OPEN_WXCHAT_GET_COE.equals(openWxResultEvent.getKey())) {
            isWXExist(openWxResultEvent.getCode());
        }
    }

    /**
     * 打开微信,获取code
     */
    @Override
    public void getWxCode() {
        WXEntryActivity.openWx(mContext, KEY_OPEN_WXCHAT_GET_COE);
    }


    @Override
    public void isExistsWx() {
        boolean flag = SystemUtil.isAppInstalled(mContext, PACKAGE_NAME_OF_WXCHAT);
        if (!flag) {
            mView.hideButtonForLoginByWx();
        }
    }

    @Override
    public void isWXExist(String code) {
        LoginModuleApi.isWXExist(code)
                .compose(getProvider().<BaseResponse<IsWXExistResp>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IsWXExistResp>handleResponse())
                .subscribeWith(new CommSubscriber<IsWXExistResp>(mView) {
                    @Override
                    public void handleResult(IsWXExistResp resp) {
                        String wxSn = resp.getSn();
                        if (resp.getCount() == 0) {
                            //微信没有绑定过手机号
                            NavigationHelper.toRegisterByWXChat(mContext, wxSn);
                        } else {
                            //微信绑定过手机号
                            wxLogin(wxSn);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }
                });
    }


    @Override
    public void wxLogin(String wxSN) {
        LoginModuleApi.wxLogin(wxSN)
                .compose(getProvider().<BaseResponse<UserInfo>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<UserInfo>handleResponse())
                .subscribeWith(new CommSubscriber<UserInfo>(mView) {
                    @Override
                    public void handleResult(UserInfo userInfo) {
                        UserManager.getInstance(mContext).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                        NavigationHelper.toLoginLoading(mContext);
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {

                    }

                });
    }

}
