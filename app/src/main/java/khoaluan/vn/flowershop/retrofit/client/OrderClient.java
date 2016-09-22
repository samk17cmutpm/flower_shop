package khoaluan.vn.flowershop.retrofit.client;

import khoaluan.vn.flowershop.data.request.InvoiceRequest;
import khoaluan.vn.flowershop.data.response.BankResponse;
import khoaluan.vn.flowershop.data.response.BillingDetailResponse;
import khoaluan.vn.flowershop.data.response.CityResponse;
import khoaluan.vn.flowershop.data.response.DistrictResponse;
import khoaluan.vn.flowershop.data.response.ExtraInformationDTOResponse;
import khoaluan.vn.flowershop.data.response.InvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.BillingAdressResponse;
import khoaluan.vn.flowershop.data.response.ListInvoiceAddressDTOResponse;
import khoaluan.vn.flowershop.data.response.ListShippingAddressResponse;
import khoaluan.vn.flowershop.data.response.RemoveResponse;
import khoaluan.vn.flowershop.data.response.ShippingAdressResponse;
import retrofit2.Response;
import retrofit2.http.Body;
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

    @GET("api/v1/common/get-city-delivery")
    Observable<Response<CityResponse>> getCitiesPayment();

    @GET("api/v1/common/get-district/{cityId}")
    Observable<Response<DistrictResponse>> getDistricts(@Path("cityId") String cityId);


    @FormUrlEncoded
    @POST("api/v1/product/update-billing-address")
    Observable<Response<BillingAdressResponse>> setBillingOrder(
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
    Observable<Response<ShippingAdressResponse>> setShippingOrder(
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
    @POST("api/v1/product/update-extra-information")
    Observable<Response<ExtraInformationDTOResponse>> setExtraInfo(
            @Field("cartId") String cartId,
            @Field("userId") String userId,
            @Field("deliverydate") long deliverydate,
            @Field("hidesender") int hidesender,
            @Field("note") String note,
            @Field("message") String message,
            @Field("paymentmethodId") int paymentmethodId
    );

    @FormUrlEncoded
    @POST("api/v1/product/update-invoice-address")
    Observable<Response<InvoiceAddressDTOResponse>> setInvoice(
            @Field("cartId") String cartId,
            @Field("userId") String userId,
            @Field("companyName") String companyName,
            @Field("taxCode") String taxCode,
            @Field("address") String address
    );


    @POST("api/v1/account/update-my-invoice-address")
    Observable<Response<InvoiceAddressDTOResponse>> setInvoice(@Body InvoiceRequest invoiceRequest);

    @FormUrlEncoded
    @POST("api/v1/account/update-my-invoice-address")
    Observable<Response<InvoiceAddressDTOResponse>> UpdateInvoice(
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("companyName") String companyName,
            @Field("taxCode") String taxCode,
            @Field("districtid") String districtid,
            @Field("address") String address
    );


    @FormUrlEncoded
    @POST("api/v1/product/create-order")
    Observable<Response<BillingDetailResponse>> makeAnOrder(
            @Field("cartId") String cartId,
            @Field("userId") String userId
    );


    @GET("api/v1/bank-account/get-account")
    Observable<Response<BankResponse>> getBanks();

    @FormUrlEncoded
    @POST("api/v1/account/update-my-billing-address")
    Observable<Response<BillingAdressResponse>> updateBillingAddress(
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("cityid") String cityid,
            @Field("districtid") String districtid,
            @Field("address") String address
    );

    @GET("api/v1/account/get-my-billing-address/{userId}")
    Observable<Response<BillingAdressResponse>> getBillingAddress(@Path("userId") String userId);

    @GET("/api/v1/account/get-list-my-shipping-address/{userId}")
    Observable<Response<ListShippingAddressResponse>> getShippingAddress(@Path("userId") String userId);

    @GET("/api/v1/account/get-list-my-invoice-address/{userId}")
    Observable<Response<ListInvoiceAddressDTOResponse>> getInvoiceAddress(@Path("userId") String userId);


    @FormUrlEncoded
    @POST("api/v1/account/update-my-shipping-address")
    Observable<Response<ShippingAdressResponse>> updateShippingAddress(
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("cityid") String cityid,
            @Field("districtid") String districtid,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("api/v1/account/update-my-shipping-address")
    Observable<Response<ShippingAdressResponse>> updateShippingAddress(
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("cityid") String cityid,
            @Field("districtid") String districtid,
            @Field("address") String address
    );



    @FormUrlEncoded
    @POST("api/v1/account/update-my-invoice-address")
    Observable<Response<InvoiceAddressDTOResponse>> updateInvoiceAddress (
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("companyName") String companyName,
            @Field("taxCode") String taxCode,
            @Field("address") String address

    );

    @FormUrlEncoded
    @POST("api/v1/account/update-my-invoice-address")
    Observable<Response<InvoiceAddressDTOResponse>> updateInvoiceAddress (
            @Field("userId") String userId,
            @Field("companyName") String companyName,
            @Field("taxCode") String taxCode,
            @Field("address") String address

    );

    @FormUrlEncoded
    @POST("api/v1/account/delete-my-billing-address")
    Observable<Response<RemoveResponse>> deleteBilling(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/v1/account/delete-my-shipping-address")
    Observable<Response<RemoveResponse>> deleteShipping(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("api/v1/account/delete-my-invoice-address")
    Observable<Response<RemoveResponse>> deleteInvoice(
            @Field("id") String id
    );
}
