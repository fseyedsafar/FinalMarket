package com.example.afinal.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.afinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcastInternetFragment extends Fragment {

    public static final String TAG = "broadcastTag";
    private BroadcastReceiver mInternetReceiver;

    public static BroadcastInternetFragment newInstance() {

        Bundle args = new Bundle();

        BroadcastInternetFragment fragment = new BroadcastInternetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInternetReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getActivity(), "net", Toast.LENGTH_LONG).show();
                Log.d(TAG, "screen");
            }
        };

        getActivity().registerReceiver(mInternetReceiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
//        getActivity().registerReceiver(mInternetReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_broadcast_internet, container, false);

//        getActivity().registerReceiver(mInternetReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
//        getActivity().registerReceiver(mInternetReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(mInternetReceiver);
    }
}
