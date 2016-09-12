package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import retrofit2.Response;
import retrofit2.http.GET;
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
}
