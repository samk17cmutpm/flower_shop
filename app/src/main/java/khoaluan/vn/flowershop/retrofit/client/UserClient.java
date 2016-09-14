package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.request.UserRequest;
import khoaluan.vn.flowershop.data.request.UserSignUpRequest;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.BillingResponse;
import khoaluan.vn.flowershop.data.response.ExtraInformationDTOResponse;
import khoaluan.vn.flowershop.data.response.UserResponse;
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
public interface UserClient {
    @POST("/api/v1/account/login")
    Observable<Response<UserResponse>> signIn(@Body UserRequest userRequest);

    @POST("/api/v1/account/register")
    Observable<Response<UserResponse>> signUp(@Body UserSignUpRequest userRequest);

    @POST("/api/v1/account/loginexternal")
    Observable<Response<UserResponse>> signSocial(@Body UserRequest userRequest);

    @FormUrlEncoded
    @POST("api/v1/account/update-profile")
    Observable<Response<UserResponse>> updateProfile(
            @Field("UserId") String UserId,
            @Field("FullName") String FullName,
            @Field("Address") String Address,
            @Field("Phone") String Phone
    );

    @GET("api/v1/product/get-order-list/{userId}")
    Observable<Response<BillingResponse>> getBillings(@Path("userId") String userId);

    @GET("api/v1/product/get-order-detail/{orderId}")
    Observable<Response<BillingDetailResponse>> getBillingDetail(@Path("orderId") String orderId);



}

