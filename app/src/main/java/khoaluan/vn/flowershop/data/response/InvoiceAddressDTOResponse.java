package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;

/**
 * Created by samnguyen on 9/14/16.
 */
public class InvoiceAddressDTOResponse extends ApiResponse<InvoiceAddressDTO> {
    @SerializedName("datas")
    private InvoiceAddressDTO invoiceAddressDTO;

    public InvoiceAddressDTOResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public InvoiceAddressDTO getResult() {
        return invoiceAddressDTO;
    }
}
