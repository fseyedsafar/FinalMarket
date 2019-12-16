package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.network.ShopFetcher;
import com.example.afinal.repository.Repository;

import java.util.List;

public class LoginFragment extends Fragment implements ShopFetcher.ShopFetcherCallback {

    private EditText mEmailEditText, mPasswordEditText;
    private TextView mRegisterTextView;
    private FrameLayout mEnterFrameLayout;
    private Repository mRepository;
    private ShopFetcher mShopFetcher = new ShopFetcher();

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = Repository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initUI(view);

        initListener();

        return view;
    }

    private void initListener() {
        mEnterFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShopFetcher.setCallbacks(LoginFragment.this);
                mShopFetcher.getCustomerList();
            }
        });

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_login_container, CreateCustomerFragment.newInstance()).commit();
            }
        });
    }

    private void initUI(View view) {
        mEmailEditText = view.findViewById(R.id.email_editText_loginFragment);
        mPasswordEditText = view.findViewById(R.id.password_editText_loginFragment);
        mRegisterTextView = view.findViewById(R.id.register_textView_loginFragment);
        mEnterFrameLayout = view.findViewById(R.id.enter_frameLayout_loginFragment);
    }

    @Override
    public void onProductResponse(List<Product> productList) {

    }

    @Override
    public void onCategoryResponse(List<Category> categoryList) {

    }

    @Override
    public void onCustomerResponse(List<Customer> customerList) {
        if (mRepository.isCustomer(mEmailEditText.getText().toString())){
            Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity(), "نام کاربری یا رمز عبور نامعتبر است", Toast.LENGTH_LONG).show();
        }
    }
}
