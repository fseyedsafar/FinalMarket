package com.example.afinal.controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.afinal.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FilterFragment extends Fragment {

    public static final String EXTRA_FILTER_LIST = "extraFilterList";
    private CheckBox mComingSoonCheckBox, mAvailableCheckBox, mUnknownCheckBox;
    private TextView mFilterTextView;

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        initUI(view);

        mFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  getFilterList();
            }
        });

        return view;
    }

    private void initUI(View view) {
        mFilterTextView = view.findViewById(R.id.filter_textView_filterFragment);
        mAvailableCheckBox = view.findViewById(R.id.itemFilter_Available_checkBox_filterFragment);
        mUnknownCheckBox = view.findViewById(R.id.itemFilter_Unknown_checkBox_filterFragment);
        mComingSoonCheckBox = view.findViewById(R.id.itemFilter_ComingSoon_checkBox_filterFragment);
    }

    private void getFilterList(){

        List<String> filterList = new ArrayList<>();

        if (mAvailableCheckBox.isChecked()){
            filterList.add("available");
        }if (mUnknownCheckBox.isChecked()){
            filterList.add("unknown");
        }if (mComingSoonCheckBox.isChecked()){
            filterList.add("comingSoon");
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILTER_LIST, (Serializable) filterList);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }
}
