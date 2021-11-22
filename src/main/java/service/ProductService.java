package service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import dto.Product;

import java.util.ArrayList;

public interface ProductService {

    @POST("products")
    Call<ArrayList<Product>>createProducts(@Body ArrayList<Product> productList);

    @GET("products")
    Call<ArrayList<Product>> getProducts(ArrayList productList);

    @PUT("products")
    Call<ArrayList<Product>>updateProducts(@Body ArrayList<Product> productList);



    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Integer id);



    @DELETE("products/{id}")
    Call<ResponseBody>deleteProduct(@Path("id") Integer id);
}

