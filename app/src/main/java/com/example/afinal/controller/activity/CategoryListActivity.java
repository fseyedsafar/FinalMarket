package com.example.afinal.controller.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.CategoryListFragment;
import com.example.afinal.model.category.Category;
import com.example.afinal.repository.Repository;
import com.google.android.material.tabs.TabLayout;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    public static final String EXTRA_ID_CATEGORY_LIST_ACTIVITY = "extraIdCategoryListActivity";
    public static final String EXTRA_CURRENT_ITEM_CATEGORY_LIST_ACTIVITY = "extraCurrentItemCategoryListActivity";
    private FrameLayout mFrameLayout;
    private LinearLayout mLinearLayout;
    private ViewPager mCategoryViewPager;
    private TabLayout mCategoryTabLayout;
    private List<Category> mCategoryList;
    private int scrollPage;
    private InterfaceCategory mInterfaceCategory;
    private CategoryListFragment mCategoryListFragment;
    private FragmentPagerAdapter mAdapter;
    private Banner mBanner;

    public void setInterfaceCategory(InterfaceCategory interfaceCategory) {
        mInterfaceCategory = interfaceCategory;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initUI();

//        showBanner(mLinearLayout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_category_list_container);
        if (fragment == null){
            fragmentManager.beginTransaction().add(R.id.activity_category_list_container, CategoryListFragment.newInstance(getIntent().getIntExtra(EXTRA_ID_CATEGORY_LIST_ACTIVITY, 0))).commit();
        }



//        mCategoryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
////                mInterfaceCategory.setCategoryForScroll(mCategoryViewPager.getCurrentItem());
//
////                mCategoryListFragment.setCategory(Repository.getInstance().getCategoryPosition(mCategoryViewPager.getCurrentItem()));
////                mCategoryListFragment.setAdapter();
////                notifyViewPager();
////
////                String s = Repository.getInstance().getCategoryPosition(mCategoryViewPager.getCurrentItem()).getName();
////                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//            }
//        });

//        mCategoryTabLayout.setupWithViewPager(mCategoryViewPager);

//        mCategoryViewPager.setAdapter(mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @NonNull
//            @Override
//            public Fragment getItem(int position) {
////                mCategoryViewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_CURRENT_ITEM_CATEGORY_LIST_ACTIVITY, 0));
//                mCategoryListFragment = CategoryListFragment.newInstance(getIntent().getIntExtra(EXTRA_ID_CATEGORY_LIST_ACTIVITY, 0), mCategoryViewPager.getCurrentItem());
//                return mCategoryListFragment;
//            }
//
//            @Override
//            public int getCount() {
//                return mCategoryList.size();
//            }
//
//            @Override
//            public int getItemPosition(@NonNull Object object) {
//                return POSITION_NONE;
//            }
//
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
//
//                CharSequence title = "";
//
//                for (int i = 0 ; i < mCategoryList.size() ; i++){
//                    if (i == position){
//                        title = mCategoryList.get(i).getName();
//                    }
//                }
//                return title;
//            }
//        });
    }

    private void initUI() {
        mCategoryList = Repository.getInstance().getCategorytList();
        mFrameLayout = findViewById(R.id.activity_category_list_container);
        mLinearLayout = findViewById(R.id.layout_banner);
//        mCategoryViewPager = findViewById(R.id.category_view_pager);
//        mCategoryTabLayout = findViewById(R.id.category_tab_layout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.close_category_list:{
                CategoryListActivity.this.finish();
                break;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

    public static Intent newIntent(Context context, int id, int currentItem){
        Intent intent = new Intent(context, CategoryListActivity.class);

        intent.putExtra(EXTRA_ID_CATEGORY_LIST_ACTIVITY, id);
        intent.putExtra(EXTRA_CURRENT_ITEM_CATEGORY_LIST_ACTIVITY, currentItem);

        return intent;
    }

    public interface InterfaceCategory {
        void setCategoryForScroll(int scrollPage);
    }

    public void notifyViewPager(){
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public void showBanner(LinearLayout linearLayout){

        mBanner = new Banner.Builder(this)
                .setParent(linearLayout)
                .setIcon(R.drawable.ic_no_wifi)
                .setMessage("خطا در اتصال به اینترنت")
                .setLeftButton("Dismiss", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        mBanner.dismiss();
                    }
                })
                .setRightButton("Turn on wifi", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        if (isConnection(CategoryListActivity.this)){
                            mBanner.setVisibility(View.GONE);
                            mBanner.dismiss();
                        } else {
//                            mBanner.show();
//                            mBanner.setVisibility(View.VISIBLE);
                            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                        }
                    }
                })
                .create();

        if (isConnection(this)) {
            mBanner.setVisibility(View.GONE);
            mBanner.dismiss();
            recreate();
        } else {
//            mSliderPic.setVisibility(View.INVISIBLE);
            mBanner.show();
            mBanner.setVisibility(View.VISIBLE);
        }
    }

    public boolean isConnection(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isAvalable = manager.getActiveNetworkInfo() != null;
        boolean isConnected = isAvalable && manager.getActiveNetworkInfo().isConnected();

        return isConnected;
    }
}
