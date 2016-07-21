package khoaluan.vn.flowershop.main.tab_type;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.Type;
import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/21/16.
 */
public class TypeAdapter extends BaseQuickAdapter<Type>{
    private List<Type> types;

    public TypeAdapter(Activity activity, List<Type> types) {
        super(R.layout.tab_type_flower_item, types);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Type type) {

    }
}
