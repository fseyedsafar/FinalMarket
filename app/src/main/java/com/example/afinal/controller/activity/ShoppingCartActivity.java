package com.example.afinal.controller.activity;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.ShoppingCartFragment;

public class ShoppingCartActivity extends SingleFragmentActivity {

    private LinearLayout mLinearLayout;

    @Override
    public Fragment createFragment() {
        return ShoppingCartFragment.newInstance();
    }

    @Override
    public int getActivityLayoutResID() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public int getFragmentContainerResID() {
        return R.id.frame_layout_shopping_cart_activity_container;
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
//        showBanner(mLinearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.shopping_cart_menu, menu);

        return true;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        return intent;
    }
}
