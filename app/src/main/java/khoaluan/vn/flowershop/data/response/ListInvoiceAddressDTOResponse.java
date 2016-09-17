package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;

/**
 * Created by samnguyen on 9/17/16.
 */
public class ListInvoiceAddressDTOResponse extends ApiResponse<List<InvoiceAddressDTO>> {

    @SerializedName("datas")
    private List<InvoiceAddressDTO> datas;

    public ListInvoiceAddressDTOResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public List<InvoiceAddressDTO> getResult() {
        return datas;
    }
}
