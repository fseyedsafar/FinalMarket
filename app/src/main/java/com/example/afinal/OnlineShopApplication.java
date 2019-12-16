package com.example.afinal;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.LinearLayout;

import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import org.greenrobot.eventbus.EventBus;

public class OnlineShopApplication extends Application {

    private static Banner mBanner;

    //    public static void showBanner(final Context context, LinearLayout linearLayout){
//
//        mBanner = new Banner.Builder(context)
//                .setParent(linearLayout)
//                .setIcon(R.drawable.ic_no_wifi)
//                .setMessage("خطا در اتصال به اینترنت")
//                .setLeftButton("Dismiss", new BannerInterface.OnClickListener() {
//                    @Override
//                    public void onClick(BannerInterface banner) {
//                        mBanner.dismiss();
//                    }
//                })
//                .setRightButton("Turn on wifi", new BannerInterface.OnClickListener() {
//                    @Override
//                    public void onClick(BannerInterface banner) {
//                        if (OnlineShopApplication.isConnection(context)){
//                            mBanner.setVisibility(View.GONE);
//                            mBanner.dismiss();
//                        } else {
//                            mBanner.show();
//                            mBanner.setVisibility(View.VISIBLE);
//                        }
//                    }
//                })
//                .create();
//
//        if (isConnection(context)) {
//            mBanner.setVisibility(View.GONE);
//            mBanner.dismiss();
////            context.recreate();
//        } else {
////            mSliderPic.setVisibility(View.INVISIBLE);
//            mBanner.show();
//            mBanner.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public static boolean isConnection(Context context){
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        boolean isAvalable = manager.getActiveNetworkInfo() != null;
//        boolean isConnected = isAvalable && manager.getActiveNetworkInfo().isConnected();
//
//        return isConnected;
//    }
}
