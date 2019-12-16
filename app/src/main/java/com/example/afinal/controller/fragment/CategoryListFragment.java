package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.afinal.R;
import com.example.afinal.adapter.CategoryAdapter;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.product.Product;
import com.example.afinal.receiver.BroadcastInternetFragment;
import com.example.afinal.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends BroadcastInternetFragment {

    public static final String ARG_CATEGORY_LIST_FRAGMENT = "argCategoryListFragment";
    public static final String ARG_CURRENT_PAGE_LIST_FRAGMENT = "argCurrentPageListFragment";
    private RecyclerView mRecyclerView;
    private FrameLayout mFrameLayout;
    private Category mCategory;
    private int mCurrentPage;
    private List<Product> mProductList = new ArrayList<>();

    public static CategoryListFragment newInstance(int id) {

        Bundle args = new Bundle();

        args.putInt(ARG_CATEGORY_LIST_FRAGMENT, id);
//        args.putInt(ARG_CURRENT_PAGE_LIST_FRAGMENT, currentPage);

        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategory = Repository.getInstance().getCategory(getArguments().getInt(ARG_CATEGORY_LIST_FRAGMENT));
//        mCurrentPage = getArguments().getInt(ARG_CURRENT_PAGE_LIST_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        initUI(view);

        setAdapter();

        return view;
    }

    public void setAdapter() {
        mProductList = Repository.getInstance().getProductForCategory(mCategory);

        if (mProductList.size() < 1){
            mFrameLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mFrameLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(new CategoryAdapter(getActivity(), mProductList, "CategoryList"));
        }
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.category_list_recycler_view);
        mFrameLayout = view.findViewById(R.id.empty_frame_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
