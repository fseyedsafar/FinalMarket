package com.example.afinal.controller.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.afinal.R;

import java.util.List;

public class SortedSearchFragment extends DialogFragment {

    public static final String ARG_SORT_STATE_SORT_SEARCH_FRAGMENT = "argSortStateSortSearchFragment";
    public static final String EXTRA_SORT_STATE_SORT_SEARCH_FRAGMENT = "extraSortStateSortSearchFragment";
    private String mSortState;
    private RadioGroup mRadioGroup;
    private RadioButton mSelectRadioButton, newest, priceLowToHigh, priceHighToLow, bestSelling, mostVisited;
    private RadioButton[] mRadioButtonList;

    public static SortedSearchFragment newInstance(String sortState) {

        Bundle args = new Bundle();

        args.putString(ARG_SORT_STATE_SORT_SEARCH_FRAGMENT, sortState);

        SortedSearchFragment fragment = new SortedSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSortState = getArguments().getString(ARG_SORT_STATE_SORT_SEARCH_FRAGMENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_sorted_search, null, false);

        initUI(view);

        setUI();

        initListener();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    private void setUI() {
        for (RadioButton radioButton : mRadioButtonList) {
            if (radioButton.getText().equals(mSortState)){
                radioButton.setChecked(true);
            }
        }
    }

    private void initListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                sendResult(bestSelling);
                SortedSearchFragment.this.dismiss();
            }
        });

        mRadioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(bestSelling);
            }
        });

        mostVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(mostVisited);
            }
        });

        bestSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(bestSelling);
            }
        });

        priceHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(priceHighToLow);
            }
        });

        priceLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(priceLowToHigh);
            }
        });

        newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(newest);
            }
        });
    }

    private void sendResult(RadioButton selectRadioButton) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SORT_STATE_SORT_SEARCH_FRAGMENT, selectRadioButton.getText().toString());

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void initUI(View view) {
        mRadioGroup = view.findViewById(R.id.sort_radio_group);
        mostVisited = view.findViewById(R.id.most_visited_radio_button);
        bestSelling = view.findViewById(R.id.best_selling_radio_button);
        priceHighToLow = view.findViewById(R.id.price_high_to_low_radio_button);
        priceLowToHigh = view.findViewById(R.id.price_low_to_high_radio_button);
        newest = view.findViewById(R.id.newest_radio_button);

        mRadioButtonList = new RadioButton[]{mostVisited, bestSelling, priceHighToLow, priceLowToHigh, newest};
    }

//    private String getRadioButtonChecked(){
//        String mStateRadioButton = "";
//        int selectedItem = mRadioGroup.getCheckedRadioButtonId();
//        mSelectRadioButton = mRadioGroup.findViewById(selectedItem);
//
//        if (selectedItem != -1) {
//            mStateRadioButton = mSelectRadioButton.getText().toString();
//        }
//
//        return mStateRadioButton;
//    }
}
