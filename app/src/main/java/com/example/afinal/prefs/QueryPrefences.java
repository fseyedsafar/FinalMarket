package com.example.afinal.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.afinal.model.product.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryPrefences {
    private static final String PREF_SHOPPING_CART_LIST = "shoppingCartList";

    private static SharedPreferences getSharedPrefences(Context context){
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static Set<Product> getLastShoppingCartList(Context context){
        SharedPreferences prefs = getSharedPrefences(context);

        Set shopSet = new HashSet<>();
        shopSet = prefs.getStringSet(PREF_SHOPPING_CART_LIST, null);

//        if (shopList == null){
//            shopList = new ArrayList();
//        }

//        Set set = prefs.getStringSet(PREF_SHOPPING_CART_LIST, null);
        return shopSet;
    }

    public static void setLastShoppingCartList(Context context, List<Product> shopList){
        SharedPreferences prefs = getSharedPrefences(context);

        Set set = new HashSet();
        set.addAll(shopList);
        prefs.edit().putStringSet(PREF_SHOPPING_CART_LIST, set).apply();
    }
}
