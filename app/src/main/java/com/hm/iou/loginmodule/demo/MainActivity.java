package com.hm.iou.loginmodule.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.loginmodule.NavigationHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickOpenLoginTypeSelect(View view) {
        NavigationHelper.toSelectLoginType(this);
    }

    public void onClickTokenLogin(View view) {
        NavigationHelper.toLoginLoading(this, true);
    }

    public void onClickResetPsdBySMS(View view) {
        NavigationHelper.toFindBySMS(this, "15267163669");
    }

    public void onClickBindEmail(View view) {
        NavigationHelper.toBindEmail(this);
    }
}
