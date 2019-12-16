package com.example.afinal.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.model.product.CategoriesItem;
import com.example.afinal.network.ShopFetcher;
import com.example.afinal.prefs.QueryPrefences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repository {

    public static Repository instance;
    private List<Product> mProductList, mShoppingProductList = new ArrayList<>();
    private List<Category> mCategorytList;
    private List<Customer> mCustomerList;

    public static Repository getInstance() {
        if (instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public List<Product> getShoppingProductList(Context context) {
        return mShoppingProductList;

//        Set<Product> set = QueryPrefences.getLastShoppingCartList(context);

//        return null;
    }

    public void addShoppingProduct(Context context, Product product){

        if (checkShopProduct(product)){
            mShoppingProductList.add(product);
        }
//        QueryPrefences.setLastShoppingCartList(context, mShoppingProductList);
    }

    private boolean checkShopProduct(Product product){
        for (Product product1 : mShoppingProductList) {
            if (product1.getId() == product.getId()){
                return false;
            }
        }
        return true;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public List<Customer> getCustomerList() {
        return mCustomerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        mCustomerList = customerList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public List<Category> getCategorytList() {
        return mCategorytList;
    }

    public void setCategorytList(List<Category> categorytList) {
        mCategorytList = categorytList;
    }

    public Product getProduct(int id){

        for (Product product : mProductList) {
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public Category getCategory(int id){

        for (Category category : mCategorytList) {
            if (category.getId() == id){
                return category;
            }
        }
        return null;
    }

    public List<Product> getProductForCategory(int currentPage){
        List<Product> list = new ArrayList<>();
        Set<Product> set = new HashSet<>();

        Category category = getCategoryPosition(currentPage);

        for (Product product : mProductList) {
            for (CategoriesItem categoriesItem : product.getCategories()) {
                if (categoriesItem.getId() == category.getId()){
                    set.add(product);
                }
            }
        }
        list.addAll(set);
        return list;
    }

    public List<Product> getProductForCategory(Category category){
        List<Product> list = new ArrayList<>();
        Set<Product> set = new HashSet<>();

//        Category category = getCategoryPosition(currentPage);

        for (Product product : mProductList) {
            for (CategoriesItem categoriesItem : product.getCategories()) {
                if (categoriesItem.getId() == category.getId()){
                    set.add(product);
                }
            }
        }
        list.addAll(set);
        return list;
    }

    public int getCategoryPosition(Category category){
        for (int i = 0 ; i < mCategorytList.size() ; i++){
            if (category.equals(mCategorytList.get(i))){
                return i;
            }
        }
        return 0;
    }

    public Category getCategoryPosition(int currentPage){
        for (int i = 0 ; i < mCategorytList.size() ; i++){
            if (i == currentPage){
                return getCategorytList().get(i);
            }
        }
        return null;
    }

    public boolean isCustomer(String email){
        for (Customer customer : mCustomerList) {
            if (customer.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }
}
