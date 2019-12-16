package com.example.afinal.controller.activity;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.ProductDetailFragment;

public class ProductDetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_ID_DETAIL_ACTIVITY = "extraIdDetailActivity";
    private LinearLayout mLinearLayout;

    @Override
    public Fragment createFragment() {
        return ProductDetailFragment.newInstance(getIntent().getIntExtra(EXTRA_ID_DETAIL_ACTIVITY, 0));
    }

    @Override
    public int getActivityLayoutResID() {
        return R.layout.activity_product_detail;
    }

    @Override
    public int getFragmentContainerResID() {
        return R.id.activity_product_detail_container;
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

    public static Intent newIntent(Context context, int id){
        Intent intent = new Intent(context, ProductDetailActivity.class);

        intent.putExtra(EXTRA_ID_DETAIL_ACTIVITY, id);

        return intent;
    }
}
