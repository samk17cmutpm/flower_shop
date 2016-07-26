package khoaluan.vn.flowershop.retrofit.client;

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

    @GET("/api/v1/product/getbestproducts/{page}/{size}")
    Observable<Response<FlowerResponse>> getFlowers(@Path("page") int page, @Path("size") int size);

    @GET("/api/v1/flower/getcategories")
    Observable<Response<CategoryResponse>> getFlowerCategories();

    @GET("/api/v1/gift/getcategories")
    Observable<Response<CategoryResponse>> getGiftCategories();
}
