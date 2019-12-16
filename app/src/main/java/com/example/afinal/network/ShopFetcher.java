package com.example.afinal.network;

import android.util.Log;

import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;
import com.example.afinal.network.interfaces.ProductService;
import com.example.afinal.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFetcher {

    public static String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String TAG_PRODUCT = "tagProduct";
    public static final String TAG = "shopFetcher";

    private Map<String, String> mQueries;
    private Retrofit mRetrofit;
    private ProductService mProductService;
    private ShopFetcherCallback mCallbacks;
    private Repository mRepository = Repository.getInstance();
//    private String mStatusType;

    public ShopFetcher() {

        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        mQueries = new HashMap<String, String>() {{
            put("consumer_key", "ck_1c7bb6ff68953b8e57ed7da1487cd80ff87e332b");
            put("consumer_secret", "cs_256f95ec8c941ffdcb655cccf462e0af9b1ebbb7");
           // put("per_page", "100");
        }};

        mProductService = mRetrofit.create(ProductService.class);
    }

    public void setCallbacks(ShopFetcherCallback callbacks) {
        mCallbacks = callbacks;
    }

    public void getAllProduct(){
        Call<List<Product>> call = mProductService.getProductBody(mQueries);

        call.enqueue(getProduct_RetrofitCallback());
    }

    public void getAllProduct(String statusType){
        mQueries.put("orderby", statusType);
        Call<List<Product>> call = mProductService.getAllProduct(mQueries);

        call.enqueue(getProduct_RetrofitCallback());
    }

    public void getCategoryProduct(){

        Call<List<Category>> call = mProductService.getProductCategory(mQueries);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                List<Category> categoryList = response.body();

                if (response.body() == null){
                    categoryList = new ArrayList<>();
                }

                mRepository.setCategorytList(categoryList);

                mCallbacks.onCategoryResponse(categoryList);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public void getCustomerList(){
        Call<List<Customer>> call = mProductService.getCustomersList(mQueries);
        call.enqueue(getCustomerList_RetrofitCallback());
    }

    public void getCustomer(String pass){
        mQueries.put("password", pass);
        Call<Customer> call = mProductService.getCustomer(mQueries);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()){
                    Customer customer = response.body();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }

    public void postCustomer(Customer customer){
        Call<Customer> call = mProductService.postCustomer(customer, mQueries);
        call.enqueue(getCustomer_RetrofitCallback());
    }

    private Callback<Customer> getCustomer_RetrofitCallback(){
        return new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()){
                    Customer customer = response.body();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }

    private Callback<List<Product>> getProduct_RetrofitCallback(){
        return new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){

                    List<Product> productList = response.body();

                    if (response.body() == null){
                        productList = new ArrayList<>();
                    }

                    mRepository.setProductList(productList);

                    mCallbacks.onProductResponse(productList);
                }
                Log.d(TAG_PRODUCT, "onResponse");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failure" + t.getMessage());
            }
        };
    }

    private Callback<List<Customer>> getCustomerList_RetrofitCallback(){
        return new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()){

                    List<Customer> customerList = response.body();

                    if (response.body() == null){
                        customerList = new ArrayList<>();
                    }

                    mRepository.setCustomerList(customerList);
                    mCallbacks.onCustomerResponse(customerList);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        };
    }

    public interface ShopFetcherCallback {
        void onProductResponse(List<Product> productList);
        void onCategoryResponse(List<Category> categoryList);
        void onCustomerResponse(List<Customer> customerList);
    }
}
