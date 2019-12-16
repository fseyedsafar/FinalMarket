package com.example.afinal.controller.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.FilterFragment;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends SingleFragmentActivity {

//    public static final String EXTRA_FILTER_LIST = "extraFilterList";

    @Override
    public Fragment createFragment() {
        return FilterFragment.newInstance();
    }

    @Override
    public int getActivityLayoutResID() {
        return R.layout.activity_filter;
    }

    @Override
    public int getFragmentContainerResID() {
        return R.id.activity_filter_container;
    }

    @Override
    public LinearLayout getBannerContainer() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        setContentView(R.layout.activity_filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.close_filter_activity:{
                FilterActivity.this.finish();
            }default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, FilterActivity.class);
        return intent;
    }
}
