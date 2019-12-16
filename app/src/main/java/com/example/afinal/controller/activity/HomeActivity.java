package com.example.afinal.controller.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afinal.OnlineShopApplication;
import com.example.afinal.R;
import com.example.afinal.controller.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.sergivonavi.materialbanner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private LinearLayout mLinearLayout;
    private View mHeaderLinearLayout;
    private TextView mHeaderTextView;
    private Banner mBanner;

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }

    @Override
    public int getActivityLayoutResID() {
        return R.layout.activity_home;
    }

    @Override
    public int getFragmentContainerResID() {
        return R.id.container_homepage;
    }

    @Override
    public LinearLayout getBannerContainer() {
        return mLinearLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        OnlineShopApplication.showBanner(this, mLinearLayout);

        initUI();

        initListener();

//        showBanner(getBannerContainer());
    }

    private void initListener() {
        mHeaderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.newIntent(HomeActivity.this));
            }
        });
    }

    private void initUI() {
        mLinearLayout = findViewById(R.id.layout_banner);
        mToolbar = findViewById(R.id.toolbar_activity_home);
        mDrawerLayout = findViewById(R.id.activity_home_container);
        mNavigationView = findViewById(R.id.navigation_home_activity);
        mHeaderLinearLayout = mNavigationView.getHeaderView(0);
        mHeaderTextView = mHeaderLinearLayout.findViewById(R.id.enter_textView_header);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        mDrawerLayout.setDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_menu, menu);

        mSearchView = (SearchView) menu.findItem(R.id.home_activity_search).getActionView();

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.newIntent(HomeActivity.this));
                mSearchView.setFocusable(false);
                mSearchView.onActionViewCollapsed();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_activity_shopping_cart:{
                startActivity(ShoppingCartActivity.newIntent(HomeActivity.this));
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent (Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home_page: {

                break;
            }
            case R.id.navigation_list_category: {
                break;
            }
            case R.id.navigation_shopping_cart: {
                break;
            }
            case R.id.navigation_newest_products: {
                break;
            }
            case R.id.navigation_most_viewed_products: {
                break;
            }
            case R.id.navigation_most_points_products: {
                break;
            }
            case R.id.navigation_frequently_questions: {
                break;
            }
            case R.id.navigation_setting: {
                break;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
