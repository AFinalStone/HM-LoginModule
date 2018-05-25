package com.hm.iou.loginmodule.business.password.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.loginmodule.R;
import com.hm.iou.loginmodule.R2;
import com.hm.iou.loginmodule.business.password.FindBySMSContract;
import com.hm.iou.loginmodule.business.password.presenter.FindBySMSPresenter;
import com.hm.iou.uikit.keyboard.HMKeyBoardAdapter;
import com.hm.iou.uikit.keyboard.HMKeyBoardView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 通过短信验证码找回登录密码
 *
 * @author syl
 */
public class FindBySMSActivity extends BaseActivity<FindBySMSPresenter> implements FindBySMSContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";

    @BindView(R2.id.tv_mobile)
    TextView mTvMobile;

    @BindView(R2.id.tv_retryCode)
    TextView mTvRetryCode;

    @BindView(R2.id.keyboardView)
    HMKeyBoardView mHMKeyBoardView;

    private GridView mGridView;
    private ArrayList<Map<String, String>> mListValue;

    private String mMobile;
    private TextView[] mTvList = new TextView[6];//用数组保存6个TextView
    private int mCurrentIndex = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.loginmodule_activity_find_by_sms;
    }

    @Override
    protected FindBySMSPresenter initPresenter() {
        return new FindBySMSPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        mTvList[0] = findViewById(R.id.tv_code1);
        mTvList[1] = findViewById(R.id.tv_code2);
        mTvList[2] = findViewById(R.id.tv_code3);
        mTvList[3] = findViewById(R.id.tv_code4);
        mTvList[4] = findViewById(R.id.tv_code5);
        mTvList[5] = findViewById(R.id.tv_code6);
        RxTextView.textChanges(mTvList[5]).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {

                String lastCode = String.valueOf(charSequence);
                if (lastCode.length() == 1) {

                    String smsCheckCode = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        smsCheckCode += mTvList[i].getText().toString().trim();
                    }
                    mPresenter.compareSMSCheckCode(mMobile, smsCheckCode);
                }
            }
        });
        mGridView = mHMKeyBoardView.getGridView();
        mListValue = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            mListValue.add(map);
        }
        // 这里、重新为数字键盘mGridView设置了Adapter
        HMKeyBoardAdapter keyBoardAdapter = new HMKeyBoardAdapter(mContext, mListValue);
        mGridView.setAdapter(keyBoardAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (mCurrentIndex >= -1 && mCurrentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++mCurrentIndex;
                        mTvList[mCurrentIndex].setText(mListValue.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (mCurrentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            mTvList[mCurrentIndex].setText("");
                            mCurrentIndex--;
                        }
                    }
                }
            }
        });
        mMobile = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        if (savedInstanceState != null) {
            mMobile = savedInstanceState.getString(EXTRA_KEY_MOBILE);
        }
        mTvMobile.setText(mMobile);
        mPresenter.sendSMSCheckCode(mMobile);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_MOBILE, mMobile);
    }

    @OnClick({R2.id.tv_retryCode})
    public void onClick(View view) {
        if (R.id.tv_retryCode == view.getId()) {
            mPresenter.sendSMSCheckCode(mMobile);
        }
    }

    @Override
    public void setGetSMSBtnText(boolean enable, String text) {
        mTvRetryCode.setEnabled(enable);
        mTvRetryCode.setText(text);
    }


}
