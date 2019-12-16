package com.example.afinal.controller.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.adapter.CategoryAdapter;
import com.example.afinal.controller.activity.FilterActivity;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.product.Product;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductListFragment extends Fragment {


    public static final String ARG_PRODUCT_LIST_SEARCH_LIST_FRAGMENT = "argProductListSearchListFragment";
    public static final String ARG_CATEGORY_LIST_SEARCH_LIST_FRAGMENT = "argCategoryListSearchListFragment";
    public static final String SORT_SEARCH_FRAGMENT_TAG = "sortSearchFragmentTag";
    public static final int REQUEST_CODE_SEARCH_PRODUCT_LIST_FRAGMENT = 0;
    public static final int REQUEST_CODE_FILTER_FRAGMENT = 1;
    private List<Product> mProductList, mFilterList;
    private List<Category> mCategoryList;
    private RecyclerView mProductRecyclerView;
    private RecyclerView mCategoryRecyclerView;
    private CardView mCardView;
    private LinearLayout mFilterLinearLayout, mSortLinearLayout;
    private TextView mFilterTextView, mSortTextView;

    public static SearchProductListFragment newInstance(List<Product> productList, List<Category> categoryList) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_PRODUCT_LIST_SEARCH_LIST_FRAGMENT, (Serializable) productList);
        args.putSerializable(ARG_CATEGORY_LIST_SEARCH_LIST_FRAGMENT, (Serializable) categoryList);

        SearchProductListFragment fragment = new SearchProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductList = (List<Product>) getArguments().getSerializable(ARG_PRODUCT_LIST_SEARCH_LIST_FRAGMENT);
        mCategoryList = (List<Category>) getArguments().getSerializable(ARG_CATEGORY_LIST_SEARCH_LIST_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_product_list, container, false);

        initUI(view);

        initListener();

        setAdapter();

        return view;
    }

    private void initListener() {
        mSortLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortedSearchFragment sortedSearchFragment = SortedSearchFragment.newInstance(mSortTextView.getText().toString());
                sortedSearchFragment.setTargetFragment(SearchProductListFragment.this, REQUEST_CODE_SEARCH_PRODUCT_LIST_FRAGMENT);
                sortedSearchFragment.show(getFragmentManager(), SORT_SEARCH_FRAGMENT_TAG);
//                SortedSearchFragment.newInstance(mSortTextView.getText().toString()).show(getFragmentManager(), SORT_SEARCH_FRAGMENT_TAG);
            }
        });

        mFilterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(FilterActivity.newIntent(getActivity()), REQUEST_CODE_FILTER_FRAGMENT);
            }
        });
    }

    private void initUI(View view) {

        mCardView = view.findViewById(R.id.card_view_search_activity);
        mSortLinearLayout = view.findViewById(R.id.sort_linear_layout);
        mFilterLinearLayout = view.findViewById(R.id.filter_linear_layout);
        mSortTextView = view.findViewById(R.id.sort_search_text_view);
        mFilterTextView = view.findViewById(R.id.filter_search_text_view);

        mProductRecyclerView = view.findViewById(R.id.product_list_recycle_search_fragment);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCategoryRecyclerView = view.findViewById(R.id.category_button_recycle_search_fragment);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void setAdapter() {
        if (isAdded()) {
            mProductRecyclerView.setAdapter(new CategoryAdapter(getActivity(), mProductList, "CategoryList"));
            mCategoryRecyclerView.setAdapter(new CategoryAdapter(getActivity(), mCategoryList, "HomeFragment"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        else if (requestCode == REQUEST_CODE_SEARCH_PRODUCT_LIST_FRAGMENT && data != null){
            mSortTextView.setText(data.getStringExtra(SortedSearchFragment.EXTRA_SORT_STATE_SORT_SEARCH_FRAGMENT));

            getSortedList(mSortTextView.getText().toString());
            setAdapter();
        }else if (requestCode == REQUEST_CODE_FILTER_FRAGMENT && data != null){
            getFilterList((List<String>) data.getSerializableExtra(String.valueOf(REQUEST_CODE_FILTER_FRAGMENT)));
            setAdapter();
        }
    }

    private List<Product> getFilterList(List<String> stateFilterList){
        for (String s : stateFilterList) {
            if (s == "available"){
                for (Product product : mProductList) {
                    if (product.isOnSale()){
                        mFilterList.add(product);
                    }
                }
            }if (s == "unknown"){
                for (Product product : mProductList) {
                    if (!product.isOnSale()){
                        mFilterList.add(product);
                    }
                }
            }
        }
        mProductList = mFilterList;
        return mProductList;
    }

    private List<Product> getSortedList(String sortState){

        switch (sortState){
            case "پربازدیدترین":{
                mProductList = (List<Product>) getArguments().getSerializable(ARG_PRODUCT_LIST_SEARCH_LIST_FRAGMENT);
                break;

            }case "پرفروش ترین":{
                Collections.sort(mProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return Integer.valueOf(product.getTotalSales()).compareTo(t1.getTotalSales());
                    }
                });
                break;

            }case "قیمت از زیاد به کم":{
                Collections.reverse(sortPricelist());
                break;

            }case "قیمت از کم به زیاد":{
                mProductList = sortPricelist();
                break;

            }case "جدیدترین":{
                Collections.sort(mProductList, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return product.getDateCreated().compareToIgnoreCase(t1.getDateCreated());
                    }
                });
                break;
            }
        }
        return mProductList;
    }

    private List<Product> sortPricelist() {
        Collections.sort(mProductList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                return Double.valueOf(product.getPrice()).compareTo(Double.valueOf(t1.getPrice()));
            }
        });
        return mProductList;
    }
}
