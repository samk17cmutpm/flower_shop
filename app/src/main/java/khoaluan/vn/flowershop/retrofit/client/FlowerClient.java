package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.response.AdvertisingResponse;
import khoaluan.vn.flowershop.data.response.CategoryResponse;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by samnguyen on 7/25/16.
 */
public interface FlowerClient {

    @GET("/api/v1/advertising/get-advertising")
    Observable<Response<AdvertisingResponse>> getAdvertisingItems();

    @GET("/api/v1/product/get-top-products")
    Observable<Response<FlowerResponse>> getTopProducts();

    @GET("/api/v1/product/get-best-products/{page}/{size}")
    Observable<Response<FlowerResponse>> getFlowers(@Path("page") int page, @Path("size") int size);

    @GET("/api/v1/flower/getcategories")
    Observable<Response<CategoryResponse>> getFlowerCategories();

    @GET("/api/v1/gift/getcategories")
    Observable<Response<CategoryResponse>> getGiftCategories();

    @GET("/api/v1/product/searchproducts/{key}/{page}/{size}")
    Observable<Response<FlowerResponse>> getFlowersBySearch(@Path("key") String key,
                                                            @Path("page") int page,
                                                            @Path("size") int size);

    @GET("/api/v1/product/get-products/{id}/{page}/{size}")
    Observable<Response<FlowerResponse>> getFlowersByCategory(@Path("id") String id,
                                                              @Path("page") int page,
                                                              @Path("size") int size);


}
