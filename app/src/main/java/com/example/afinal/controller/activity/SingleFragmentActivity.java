package com.example.afinal.controller.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.afinal.OnlineShopApplication;
import com.example.afinal.R;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private Banner mBanner;

    public abstract Fragment createFragment();

    public abstract int getActivityLayoutResID();

    public abstract int getFragmentContainerResID();

    public abstract LinearLayout getBannerContainer();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutResID());

//        showBanner(getBannerContainer());

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(getFragmentContainerResID());

        if (fragment == null){
            fragmentManager.beginTransaction().add(getFragmentContainerResID(), createFragment()).commit();
        }
    }

    public void showBanner(LinearLayout linearLayout){

        mBanner = new Banner.Builder(this)
                .setParent(linearLayout)
                .setIcon(R.drawable.ic_no_wifi)
                .setMessage("خطا در اتصال به اینترنت")
                .setLeftButton("Dismiss", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        mBanner.dismiss();
                    }
                })
                .setRightButton("Turn on wifi", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        if (isConnection(SingleFragmentActivity.this)){
                            mBanner.setVisibility(View.GONE);
                            mBanner.dismiss();
                        } else {
//                            mBanner.show();
//                            mBanner.setVisibility(View.VISIBLE);
                            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                        }
                    }
                })
                .create();

        if (isConnection(this)) {
            mBanner.setVisibility(View.GONE);
            mBanner.dismiss();
            recreate();
        } else {
//            mSliderPic.setVisibility(View.INVISIBLE);
            mBanner.show();
            mBanner.setVisibility(View.VISIBLE);
        }
    }

    public boolean isConnection(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isAvalable = manager.getActiveNetworkInfo() != null;
        boolean isConnected = isAvalable && manager.getActiveNetworkInfo().isConnected();

        return isConnected;
    }
}
