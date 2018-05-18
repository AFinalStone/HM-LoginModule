package com.hm.iou.loginmodule.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hm.iou.loginmodule.business.type.SelectLoginTypeActivity;
import com.hm.iou.loginmodule.business.type.SelectLoginTypeContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickOpenLoginTypeSelect(View view) {
        startActivity(new Intent(this, SelectLoginTypeActivity.class));
    }
}
