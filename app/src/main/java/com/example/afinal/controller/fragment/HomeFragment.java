package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.afinal.R;
import com.example.afinal.adapter.CategoryAdapter;
import com.example.afinal.adapter.ProductAdapter;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.network.ShopFetcher;
import com.example.afinal.receiver.BroadcastInternetFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BroadcastInternetFragment implements ShopFetcher.ShopFetcherCallback {

    private RecyclerView mDateProductRecyclerView, mCategoryRecyclerView;
    private SliderLayout mSliderLayout;
    private ProductAdapter mProductAdapter;
    private CategoryAdapter mCategoryAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private List<Category> mCategoryList = new ArrayList<>();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(view);

        setUpAdapter();

        setUpSlider();

        return view;
    }

    private void setUpAdapter() {
        if (isAdded()) {
//            mProductAdapter = new ProductAdapter(getActivity(), mProductList);
//            mDateProductRecyclerView.setAdapter(mProductAdapter);

            mCategoryAdapter = new CategoryAdapter(getActivity(), mCategoryList, "HomeFragment");
            mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        }
    }

    private void updateItem() {
        ShopFetcher shopFetcher = new ShopFetcher();
        shopFetcher.setCallbacks(this);
//        mProductFetcher.getLastProduct();
        shopFetcher.getCategoryProduct();

        shopFetcher.getCustomerList();
    }

    private void initUI(View view) {
//        mDateProductRecyclerView = view.findViewById(R.id.date_product_recycler_view);
//        mDateProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mSliderLayout = view.findViewById(R.id.slider_image_fragment_home);

        mCategoryRecyclerView = view.findViewById(R.id.category_button_recycler_view);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        addFragment("date", R.id.date_product_container);
        addFragment("popularity", R.id.popularity_product_container);
        addFragment("rating", R.id.rating_product_container);
    }

    private void addFragment(String statusType, int itemLayout){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(itemLayout);
        if (fragment == null){
            fragmentManager.beginTransaction().add(itemLayout, ProductListFragment.newInstance(statusType)).commit();
        }
    }

    private void setUpSlider(){
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("لوازم برقی", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012855.jpg");
        urlMap.put("صوتی و تصویری", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012860.jpg");
        urlMap.put("مراقبت از پوست", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012909.jpg");
        urlMap.put("لذت از لحظات", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000013192.jpg");
//        for (Category category : mCategoryList) {
//            if (category.getImage() != null) {
//                urlMap.put(category.getName(), category.getImage().getSrc());
//            }
//        }

//        initial slider layout
        for (String name : urlMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(urlMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

//            add extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setDuration(5000);
    }

    @Override
    public void onProductResponse(List<Product> productList) {
//        mProductList = productList;
//        setUpAdapter();
    }

    @Override
    public void onCategoryResponse(List<Category> categoryList) {

        mCategoryList = categoryList;
        setUpAdapter();
       // setUpSlider();
    }

    @Override
    public void onCustomerResponse(List<Customer> customerList) {

    }
}
