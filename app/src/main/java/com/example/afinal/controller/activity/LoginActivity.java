package com.example.afinal.controller.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    private LinearLayout mLinearLayout;

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    public int getActivityLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    public int getFragmentContainerResID() {
        return R.id.activity_login_container;
    }

    @Override
    public LinearLayout getBannerContainer() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mLinearLayout = findViewById(R.id.layout_banner);
//
//        showBanner(mLinearLayout);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
