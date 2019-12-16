package com.example.afinal.controller.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.afinal.R;
import com.example.afinal.controller.fragment.SearchProductListFragment;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.model.product.CategoriesItem;
import com.example.afinal.network.ShopFetcher;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements ShopFetcher.ShopFetcherCallback {

//    public static final String SORT_SEARCH_FRAGMENT_TAG = "sortSearchFragment";
    //    private RecyclerView mRecyclerView;
//    private FrameLayout mFrameLayout;
//    private LinearLayout mFilterLinearLayout, mSortLinearLayout;
//    private TextView mFilterTextView, mSortTextView;
    private LinearLayout mLinearLayout;
    private List<Product> mProductlist = new ArrayList<>(), mProductsFromNet = new ArrayList<>();
    private List<Category> mCategoryList = new ArrayList<>();
    private Set<Category> mCategorySet;
    private SearchView mSearchView;
    //    private CardView mCardView;
//    private List<Product> mProductsFromNet;
    private String mQueryString;
    private Banner mBanner;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initUI();

//        showBanner(mLinearLayout);

//        mSortLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SortedSearchFragment.newInstance(mSortTextView.getText().toString()).show(getSupportFragmentManager(), SORT_SEARCH_FRAGMENT_TAG);
//            }
//        });
    }

    private void initUI() {
        mLinearLayout = findViewById(R.id.layout_banner);
//        mCardView = findViewById(R.id.card_view_search_activity);
//        mSortLinearLayout = findViewById(R.id.sort_linear_layout);
//        mFilterLinearLayout = findViewById(R.id.filter_linear_layout);
//        mSortTextView = findViewById(R.id.sort_search_text_view);
//        mFilterTextView = findViewById(R.id.filter_search_text_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_activity_menu, menu);

        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query != null) {
                    mQueryString = query;

                    ShopFetcher mProductFetcher = new ShopFetcher();
                    mProductFetcher.setCallbacks(SearchActivity.this);
                    mProductFetcher.getAllProduct();

//                    searchView.setFocusable(false);
//                    searchView.onActionViewCollapsed();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SearchActivity.this.recreate();
////                mSearchView.setFocusable(false);
////                mSearchView.onActionViewCollapsed();
//            }
//        });

        return true;
    }

    private void searchList(String query) {

        mProductlist = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mCategorySet = new HashSet<>();

        for (Product product : mProductsFromNet) {
            if (product.getName().contains(query)
                    || product.getRegularPrice().contains(query)
                    || product.getDateCreated().contains(query)
                    || product.getDescription().contains(query)
                    || product.getPrice().contains(query)
                    || product.getStatus().contains(query)
                    || product.getType().contains(query)
                    || searchCategoryList(product, query) == true){
                mProductlist.add(product);

                for (CategoriesItem categoriesItem : product.getCategories()) {
                    if (checkSearchCategory(categoriesItem, product.getCategories())){
                        Category category = new Category(categoriesItem.getName(), categoriesItem.getId(), categoriesItem.getSlug());
                        mCategoryList.add(category);
                    }
                }
            }
        }
//        mCategoryList.addAll(mCategorySet);
    }

    private boolean checkSearchCategory(CategoriesItem categories, List<CategoriesItem> categoriesItemList){
        for (CategoriesItem categoriesItem : categoriesItemList) {
            if (categoriesItem.getId() == categories.getId()){
                return false;
            }
        }
        return true;
    }

    private boolean searchCategoryList(Product product, String query){
        for (CategoriesItem category : product.getCategories()) {
            if (category.getName().contains(query)){
                return true;
            }
        }
        return false;
    }

    public static Intent newIntent (Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onProductResponse(List<Product> productList) {
        mProductsFromNet = productList;

        searchList(mQueryString);

        mSearchView.setFocusable(false);
        mSearchView.onActionViewCollapsed();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_search_container);
//        if (fragment == null){
            fragmentManager.beginTransaction().replace(R.id.activity_search_container, SearchProductListFragment.newInstance(mProductlist, mCategoryList)).commit();
//        }

//        mCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryResponse(List<Category> categoryList) {

    }

    @Override
    public void onCustomerResponse(List<Customer> customerList) {

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
                        if (isConnection(SearchActivity.this)){
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
