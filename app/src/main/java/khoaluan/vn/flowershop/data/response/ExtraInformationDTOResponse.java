package khoaluan.vn.flowershop.data.response;

import com.google.gson.annotations.SerializedName;

import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;

/**
 * Created by samnguyen on 9/14/16.
 */
public class ExtraInformationDTOResponse extends ApiResponse<ExtraInformationDTO>{
    @SerializedName("datas")
    private ExtraInformationDTO extraInformationDTO;
    public ExtraInformationDTOResponse(String errcode, String errmessage, boolean hasNext, boolean success) {
        super(errcode, errmessage, hasNext, success);
    }

    @Override
    public ExtraInformationDTO getResult() {
        return extraInformationDTO;
    }
}
