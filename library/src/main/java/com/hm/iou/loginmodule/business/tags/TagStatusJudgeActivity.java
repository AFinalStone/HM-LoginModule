package com.hm.iou.loginmodule.business.tags;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.loginmodule.NavigationHelper;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.api.LoginModuleApi;
import com.hm.iou.loginmodule.bean.UserTagBean;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.model.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by hjy on 2018/12/20.
 */

public class TagStatusJudgeActivity extends BaseActivity {

    public static String EXTRA_KEY_LOAD_FAIL_URL = "load_fail_url";

    private String mLoadFailUrl;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected MvpActivityPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        Intent data = getIntent();
        mLoadFailUrl = data.getStringExtra(EXTRA_KEY_LOAD_FAIL_URL);
        if (bundle != null) {
            mLoadFailUrl = bundle.getString(EXTRA_KEY_LOAD_FAIL_URL);
        }

        showLoadingView();
        LoginModuleApi.getUserTagStatus()
            .subscribe(new Consumer<BaseResponse<List<UserTagBean>>>() {
                @Override
                public void accept(BaseResponse<List<UserTagBean>> response) throws Exception {
                    dismissLoadingView();
                    if( response.getErrorCode() == 0) {
                        List<UserTagBean> tagList = response.getData();
                        if (tagList != null && !tagList.isEmpty()) {
                            //跳转到设置标签页
                            NavigationHelper.toAddTagPage(TagStatusJudgeActivity.this, (ArrayList<UserTagBean>) tagList);
                        } else {
                            //直接进入数据加载页
                            NavigationHelper.toLoginLoading(TagStatusJudgeActivity.this, mLoadFailUrl);
                        }
                    } else {
                        toastErrorMessage(response.getMessage());
                        toErrorPage();
                    }

                    //关闭当前页面
                    closeCurrPage();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    dismissLoadingView();
                    toastErrorMessage(R.string.net_network_error);
                    toErrorPage();
                    closeCurrPage();
                }
            });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_LOAD_FAIL_URL, mLoadFailUrl);
    }

    @Override
    public void onBackPressed() {
    }

    private void toErrorPage() {
        if (!TextUtils.isEmpty(mLoadFailUrl)) {
            Router.getInstance().buildWithUrl(mLoadFailUrl)
                    .navigation(this);
        }
    }

}