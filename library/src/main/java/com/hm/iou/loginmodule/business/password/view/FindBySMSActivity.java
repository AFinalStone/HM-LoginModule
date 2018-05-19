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
import com.hm.iou.uikit.keyboard.KeyBoardAdapter;
import com.hm.iou.uikit.keyboard.VirtualKeyboardView;
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
 * @author AFinalStone
 */
public class FindBySMSActivity extends BaseActivity<FindBySMSPresenter> implements FindBySMSContract.View {

    public static final String EXTRA_KEY_MOBILE = "mobile";


    @BindView(R2.id.tv_phone)
    TextView tvPhone;
    String currentMobileNumber;

    @BindView(R2.id.tv_retryCode)
    TextView tvRetryCode;

    private TextView[] tvList = new TextView[6];//用数组保存6个TextView
    private int currentIndex = -1;

    @BindView(R2.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    GridView gridView;
    private ArrayList<Map<String, String>> valueList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_login_psd_by_send_sms_code;
    }

    @Override
    protected FindBySMSPresenter initPresenter() {
        return new FindBySMSPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        tvList[0] = findViewById(R.id.tv_code1);
        tvList[1] = findViewById(R.id.tv_code2);
        tvList[2] = findViewById(R.id.tv_code3);
        tvList[3] = findViewById(R.id.tv_code4);
        tvList[4] = findViewById(R.id.tv_code5);
        tvList[5] = findViewById(R.id.tv_code6);
        RxTextView.textChanges(tvList[5]).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {

                String lastCode = String.valueOf(charSequence);
                if (lastCode.length() == 1) {

                    String currentCheckCode = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱

                    for (int i = 0; i < 6; i++) {
                        currentCheckCode += tvList[i].getText().toString().trim();
                    }
                    jumpToFindLoginPsdByPhone02View(currentCheckCode);
                }
            }
        });
        gridView = virtualKeyboardView.getGridView();
        valueList = new ArrayList<>();

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
            valueList.add(map);
        }
        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex].setText("");
                            currentIndex--;
                        }
                    }
                }
            }
        });
        currentMobileNumber = getIntent().getStringExtra(EXTRA_KEY_MOBILE);
        tvPhone.setText(currentMobileNumber);
        mPresenter.sendResetPsdCheckCodeSMS(currentMobileNumber);
    }


    @OnClick({R2.id.tv_retryCode})
    public void onClick(View view) {
        if (R.id.tv_retryCode == view.getId()) {
            mPresenter.sendResetPsdCheckCodeSMS(currentMobileNumber);
        }
    }


    private void timeCountDown() {
//        CommonSubscriber<Long> commonSubscriber = new CommonSubscriber<Long>(this) {
//            @Override
//            public void onNext(Long time) {
//                if (tvRetryCode != null) {
//                    String countDownDesc = getString(R.string.btn_getCode_timeCountDown);
//                    tvRetryCode.setEnabled(false);
//                    tvRetryCode.setText(time + countDownDesc);
//                }
//            }
//        }.setShowLoading(false);
//        Flowable.interval(0, 1, TimeUnit.SECONDS)
//                .map(s -> 59 - s)
//                .take(60)
//                .compose(RxUtil.rxSchedulerHelper())
//                .compose(bindUntilEvent(ActivityEvent.DESTROY))
//                .doOnComplete(() -> {
//                    if (tvRetryCode != null) {
//                        tvRetryCode.setEnabled(true);
//                        tvRetryCode.setText(R.string.btn_getCode);
//                    }
//                })
//                .subscribeWith(commonSubscriber);

    }

//
//    @Override
//    public void sendResetPsdCheckCodeSMSSuccess() {
//        showSuccessMsg(R.string.getCode_success);
//        timeCountDown();
//    }

    //通过手机验证码实现重置登录密码
    public void jumpToFindLoginPsdByPhone02View(String currentCheckCode) {
//        Intent intent = new Intent(this, ResetLoginPsdActivity.class);
//        intent.putExtra(Constants.INTENT_RESET_LOGIN_PSD_TYPE, LoginResetPsdTypeEnum.LoginResetPsdBySMS);
//        intent.putExtra(Constants.INTENT_MOBILE_NUMBER, currentMobileNumber);
//        intent.putExtra(Constants.INTENT_CHECK_CODE, currentCheckCode);
//        startActivity(intent);
    }

}
