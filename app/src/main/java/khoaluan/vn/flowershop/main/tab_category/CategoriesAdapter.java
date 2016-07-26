package khoaluan.vn.flowershop.main.tab_category;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;

/**
 * Created by samnguyen on 7/21/16.
 */
public class CategoriesAdapter extends BaseQuickAdapter<Category>{
    private List<Category> categories;

    public CategoriesAdapter(Activity activity, List<Category> categories) {
        super(R.layout.tab_type_flower_item, categories);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Category category) {
        baseViewHolder.setText(R.id.tv_name, category.getName());
    }
}
