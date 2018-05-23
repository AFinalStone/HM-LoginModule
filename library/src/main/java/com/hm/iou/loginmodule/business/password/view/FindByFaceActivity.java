//package com.hm.iou.loginmodule.business.password.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.hm.iou.base.BaseActivity;
//import com.hm.iou.loginmodule.R;
//import com.hm.iou.loginmodule.R2;
//import com.hm.iou.loginmodule.business.password.FindByFaceContract;
//import com.hm.iou.loginmodule.business.password.presenter.FindByFacePresenter;
//import com.hm.iou.tools.StringUtil;
//import com.hm.iou.uikit.keyboard.HMKeyBoardAdapter;
//import com.hm.iou.uikit.keyboard.HMKeyBoardView;
//import com.jakewharton.rxbinding2.widget.RxTextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.BindView;
//import io.reactivex.functions.Consumer;
//
//
///**
// * 通过活体校验找回密码
// *
// * @author AFinalStone
// * @time 2018/3/2 下午5:46
// */
//public class FindByFaceActivity extends BaseActivity<FindByFacePresenter> implements FindByFaceContract.View {
//
//    public static final String EXTRA_KEY_MOBILE = "mobile";
//    public static final String EXTRA_KEY_USER_NAME = "name";
//
//    @BindView(R2.id.tv_title)
//    TextView mTvTitle;
//    @BindView(R2.id.virtualKeyboardView)
//    HMKeyBoardView mHMKeyBoardView;
//    GridView mGridView;
//
//    //用数组保存6个TextView
//    private TextView[] mTvList = new TextView[6];
//    //当前输入框所在的位置
//    private int mCurrentIndex = -1;
//    //输入的数据
//    private ArrayList<Map<String, String>> mListValue;
//    //用户输入的手机号
//    private String mMobile;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.loginmodule_activity_find_by_living_check;
//    }
//
//    @Override
//    protected FindByFacePresenter initPresenter() {
//        return new FindByFacePresenter(this, this);
//    }
//
//    @Override
//    protected void initEventAndData(Bundle savedInstanceState) {
//        Intent intent = getIntent();
//        mMobile = intent.getStringExtra(EXTRA_KEY_MOBILE);
//        String name = intent.getStringExtra(EXTRA_KEY_USER_NAME);
//        String strTitle = getString(R.string.loginmodule_find_by_living_check_title);
//        if (StringUtil.isEmpty(name)) {
//            name = getString(R.string.loginmodule_current_user_name);
//        }
//        strTitle = String.format(strTitle, name);
//        mTvTitle.setText(strTitle);
//        initData();
//    }
//
//    private void initData() {
//        mTvList[0] = findViewById(R.id.tv_code1);
//        mTvList[1] = findViewById(R.id.tv_code2);
//        mTvList[2] = findViewById(R.id.tv_code3);
//        mTvList[3] = findViewById(R.id.tv_code4);
//        mTvList[4] = findViewById(R.id.tv_code5);
//        mTvList[5] = findViewById(R.id.tv_code6);
//        RxTextView.textChanges(mTvList[5]).subscribe(new Consumer<CharSequence>() {
//            @Override
//            public void accept(CharSequence charSequence) throws Exception {
//
//                String lastCode = String.valueOf(charSequence);
//                if (lastCode.length() == 1) {
//
//                    String currentIDCard = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
//
//                    for (int i = 0; i < 6; i++) {
//                        currentIDCard += mTvList[i].getText().toString().trim();
//                    }
//                    mPresenter.checkIdCard(mMobile, currentIDCard);
//                }
//            }
//        });
//        mGridView = mHMKeyBoardView.getGridView();
//        mListValue = new ArrayList<>();
//
//        // 初始化按钮上应该显示的数字
//        for (int i = 1; i < 13; i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            if (i < 10) {
//                map.put("name", String.valueOf(i));
//            } else if (i == 10) {
//                map.put("name", "");
//            } else if (i == 11) {
//                map.put("name", String.valueOf(0));
//            } else if (i == 12) {
//                map.put("name", "");
//            }
//            mListValue.add(map);
//        }
//        // 这里、重新为数字键盘gridView设置了Adapter
//        HMKeyBoardAdapter hmKeyBoardAdapter = new HMKeyBoardAdapter(mContext, mListValue);
//        mGridView.setAdapter(hmKeyBoardAdapter);
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position < 11 && position != 9) {    //点击0~9按钮
//                    if (mCurrentIndex >= -1 && mCurrentIndex < 5) {      //判断输入位置————要小心数组越界
//                        ++mCurrentIndex;
//                        mTvList[mCurrentIndex].setText(mListValue.get(position).get("name"));
//                    }
//                } else {
//                    if (position == 11) {      //点击退格键
//                        if (mCurrentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
//                            mTvList[mCurrentIndex].setText("");
//                            mCurrentIndex--;
//                        }
//                    }
//                }
//            }
//        });
//        mCurrentIndex = -1;
//    }
//
//}