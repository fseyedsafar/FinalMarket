package com.example.afinal.controller.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afinal.R;
import com.example.afinal.adapter.ProductAdapter;
import com.example.afinal.model.product.Product;
import com.example.afinal.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private Repository mRepository;
    private List<Product> mProductList = new ArrayList<>();

    public static ShoppingCartFragment newInstance() {

        Bundle args = new Bundle();

        ShoppingCartFragment fragment = new ShoppingCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = Repository.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        initUI(view);

        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        setUpAdapter();

        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.shopping_product_list_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void setUpAdapter() {
        if (isAdded()) {
            mProductAdapter = new ProductAdapter(getActivity(), mRepository.getShoppingProductList(getActivity()), R.layout.product_list_item, "ShoppingCartFragment");
            mRecyclerView.setAdapter(mProductAdapter);
        }
    }
}
