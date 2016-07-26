package khoaluan.vn.flowershop.main.tab_type;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.FlowerType;

/**
 * Created by samnguyen on 7/21/16.
 */
public class FlowerTypeAdapter extends BaseQuickAdapter<FlowerType>{
    private List<FlowerType> flowerTypes;

    public FlowerTypeAdapter(Activity activity, List<FlowerType> flowerTypes) {
        super(R.layout.tab_type_flower_item, flowerTypes);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, FlowerType flowerType) {

    }
}
