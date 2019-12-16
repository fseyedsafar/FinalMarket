package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinal.R;
import com.example.afinal.adapter.ProductAdapter;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.network.ShopFetcher;
import com.example.afinal.receiver.BroadcastInternetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends BroadcastInternetFragment implements ShopFetcher.ShopFetcherCallback {

    public static final String ARG_STATUS_TYPE_PRODUCT_LIST_FRAGMENT = "argStatusTypeProductListFragment";
    private ProductAdapter mProductAdapter;
    private RecyclerView mRecyclerView;
    private List<Product> mProductList = new ArrayList<>();
    private String statusType;

    public static ProductListFragment newInstance(String statusType) {

        Bundle args = new Bundle();

        args.putString(ARG_STATUS_TYPE_PRODUCT_LIST_FRAGMENT, statusType);

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statusType = getArguments().getString(ARG_STATUS_TYPE_PRODUCT_LIST_FRAGMENT);
        updateItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        initUI(view);

        setUpAdapter();

        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.product_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpAdapter() {
        if (isAdded()) {
            mProductAdapter = new ProductAdapter(getActivity(), mProductList, R.layout.product_list_item, "HomeFragment");
            mRecyclerView.setAdapter(mProductAdapter);
        }
    }

    private void updateItem() {
        ShopFetcher shopFetcher = new ShopFetcher();
        shopFetcher.setCallbacks(this);
        shopFetcher.getAllProduct(statusType);
    }

    @Override
    public void onProductResponse(List<Product> productList) {
        mProductList = productList;
        setUpAdapter();
    }

    @Override
    public void onCategoryResponse(List<Category> categoryList) {

    }

    @Override
    public void onCustomerResponse(List<Customer> customerList) {

    }
}
