package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.request.UserRequest;
import khoaluan.vn.flowershop.data.request.UserSignUpRequest;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.BillingResponse;
import khoaluan.vn.flowershop.data.response.NotifycationResponse;
import khoaluan.vn.flowershop.data.response.RemoveResponse;
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

    @FormUrlEncoded
    @POST("api/v1/contact/add-contact")
    Observable<Response<RemoveResponse>> feedBack(
            @Field("userId") String userId,
            @Field("Email") String Email,
            @Field("Phone") String Phone,
            @Field("FullName") String FullName,
            @Field("Subject") String Subject,
            @Field("Content") String Content
    );

    @FormUrlEncoded
    @POST("api/v1/notification/get-notifications")
    Observable<Response<NotifycationResponse>> getNotifycations(
            @Field("UserId") String UserId,
            @Field("CurrentPage") int CurrentPage,
            @Field("PageSize") int PageSize
    );

    @GET("api/v1/account/get-my-billing-address/{userId}")
    Observable<Response<BillingAdressResponse>> getUserInfo(@Path("userId") String userId);



}

