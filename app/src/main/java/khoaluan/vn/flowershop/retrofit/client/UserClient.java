package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.request.UserRequest;
import khoaluan.vn.flowershop.data.request.UserSignUpRequest;
import khoaluan.vn.flowershop.data.response.UserResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
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
}

