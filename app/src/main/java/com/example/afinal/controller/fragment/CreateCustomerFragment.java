package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.afinal.R;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.network.ShopFetcher;
import com.example.afinal.repository.Repository;


public class CreateCustomerFragment extends Fragment {

    private EditText mUsernameEditText, mEmailEditText, mPasswordEditText;
    private FrameLayout mRegisterFrameLayout;
    private Repository mRepository;
    private ShopFetcher mShopFetcher = new ShopFetcher();

    public static CreateCustomerFragment newInstance() {

        Bundle args = new Bundle();

        CreateCustomerFragment fragment = new CreateCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_customer, container, false);

        initUI(view);

        mRegisterFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShopFetcher.postCustomer(new Customer(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString()));
            }
        });

        return view;
    }

    private void initUI(View view) {
        mUsernameEditText = view.findViewById(R.id.userName_editText_createCustomerFragment);
        mEmailEditText = view.findViewById(R.id.email_editText_createCustomerFragment);
        mPasswordEditText = view.findViewById(R.id.password_editText_createCustomerFragment);
        mRegisterFrameLayout = view.findViewById(R.id.register_frameLayout_createCustomerFragment);
    }
}
