package com.example.afinal.network.interfaces;

import com.example.afinal.model.category.Category;
import com.example.afinal.model.customer.Customer;
import com.example.afinal.model.product.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ProductService {

    @GET("products")
    Call<List<Product>> getProductBody(@QueryMap Map<String, String> productQueries);

    @GET("products/categories")
    Call<List<Category>> getProductCategory(@QueryMap Map<String, String> categoryQueries);

    @GET("customers")
    Call<List<Customer>> getCustomersList(@QueryMap Map<String, String> customerQueries);

    @GET("customers")
    Call<Customer> getCustomer(@QueryMap Map<String, String> customerQueries);

    @POST("customers")
    Call<Customer> postCustomer(@Body Customer customer, @QueryMap Map<String, String> customerQueries);




    @GET("products")
    Call<List<Product>> getAllProduct(@QueryMap Map<String, String> categoryQueries);
}
