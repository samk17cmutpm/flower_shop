package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.response.CartResponse;
import khoaluan.vn.flowershop.data.response.RemoveCartResponse;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by samnguyen on 9/11/16.
 */
public interface CartClient {

    @FormUrlEncoded
    @POST("api/v1/product/add-product-to-cart")
    Observable<Response<CartResponse>> addToCart(
            @Field("cartId") String cartId,
            @Field("productId") String productId
    );

    @FormUrlEncoded
    @POST("api/v1/product/update-product-quantity-to-cart")
    Observable<Response<CartResponse>> updateQuantityCart(
            @Field("cartId") String cartId,
            @Field("productId") String productId,
            @Field("quantity") int quantity
    );

    @GET("api/v1/product/get-products-from-cart/{cartId}")
    Observable<Response<CartResponse>> getCarts(@Path("cartId") String cartId);

    @FormUrlEncoded
    @POST("api/v1/product/remove-products-from-cart")
    Observable<Response<RemoveCartResponse>> removeCartItem(
            @Field("cartId") String cartId,
            @Field("productId") String productId
    );

}
