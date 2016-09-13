package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.OrderResponse;
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
public interface OrderClient {
    @GET("api/v1/common/get-city")
    Observable<Response<CityResponse>> getCities();

    @GET("api/v1/common/get-district/{cityId}")
    Observable<Response<DistrictResponse>> getDistricts(@Path("cityId") String cityId);


    @FormUrlEncoded
    @POST("api/v1/product/update-billing-address")
    Observable<Response<OrderResponse>> setBillingOrder(
            @Field("cartId") String cartId,
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("mail") String mail,
            @Field("cityid") String cityid,
            @Field("districtid") String districtid,
            @Field("address") String address
    );


    @FormUrlEncoded
    @POST("api/v1/product/update-shipping-address")
    Observable<Response<OrderResponse>> setShippingOrder(
            @Field("cartId") String cartId,
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("mail") String mail,
            @Field("cityid") String cityid,
            @Field("districtid") String districtid,
            @Field("address") String address
    );
}
