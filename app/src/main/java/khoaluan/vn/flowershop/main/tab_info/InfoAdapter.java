package khoaluan.vn.flowershop.main.tab_info;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.InfoType;

/**
 * Created by samnguyen on 7/23/16.
 */
public class InfoAdapter extends BaseQuickAdapter<InfoType> {
    private List<InfoType> infoTypes;

    public InfoAdapter(Activity activity, List<InfoType> infoTypes) {
        super(R.layout.tab_type_flower_item, infoTypes);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, InfoType infoType) {

    }
}
