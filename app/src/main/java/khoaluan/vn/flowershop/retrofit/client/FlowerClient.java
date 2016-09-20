package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.request.SearchRequest;
import khoaluan.vn.flowershop.data.response.AdvertisingResponse;
import khoaluan.vn.flowershop.data.response.CategoryResponse;
import khoaluan.vn.flowershop.data.response.FlowerResponse;
import khoaluan.vn.flowershop.data.response.ListFlowerResponse;
import khoaluan.vn.flowershop.data.response.NewestResponse;
import khoaluan.vn.flowershop.data.response.RatingListResponse;
import khoaluan.vn.flowershop.data.response.RatingResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by samnguyen on 7/25/16.
 */
public interface FlowerClient {

    @GET("/api/v1/advertising/get-advertising")
    Observable<Response<AdvertisingResponse>> getAdvertisingItems();

    @GET("/api/v1/product/get-top-products")
    Observable<Response<ListFlowerResponse>> getTopProducts();

    @GET("/api/v1/product/get-best-products/{page}/{size}")
    Observable<Response<ListFlowerResponse>> getFlowers(@Path("page") int page, @Path("size") int size);

    @GET("/api/v1/flower/getcategories")
    Observable<Response<CategoryResponse>> getFlowerCategories();

    @GET("/api/v1/gift/getcategories")
    Observable<Response<CategoryResponse>> getGiftCategories();

    @GET("/api/v1/product/searchproducts/{key}/{page}/{size}")
    Observable<Response<ListFlowerResponse>> getFlowersBySearch(@Path("key") String key,
                                                                @Path("page") int page,
                                                                @Path("size") int size);

    @POST("/api/v1/product/search-products")
    Observable<Response<ListFlowerResponse>> getFlowersBySearch(@Body SearchRequest searchRequest);

    @GET("/api/v1/product/get-products/{id}/{page}/{size}")
    Observable<Response<ListFlowerResponse>> getFlowersByCategory(@Path("id") String id,
                                                                  @Path("page") int page,
                                                                  @Path("size") int size);

    @GET("/api/v1/summary/get-summary")
    Observable<Response<NewestResponse>> getNewestData();

    @FormUrlEncoded
    @POST("api/v1/rating/get-rating-list")
    Observable<Response<RatingListResponse>> getRatings(
            @Field("ProductId") String ProductId,
            @Field("CurrentPage") int CurrentPage,
            @Field("PageSize") int PageSize
    );

    @FormUrlEncoded
    @POST("api/v1/rating/create-rating")
    Observable<Response<RatingResponse>> createRating(
            @Field("UserId") String UserId,
            @Field("ProductId") String ProductId,
            @Field("StarRating") int StarRating,
            @Field("Feedback") String Feedback
    );

    @GET("/api/v1/product/get-product-detail/{id}")
    Observable<Response<FlowerResponse>> getFlowerDetails(
            @Path("id") String id
    );


}
