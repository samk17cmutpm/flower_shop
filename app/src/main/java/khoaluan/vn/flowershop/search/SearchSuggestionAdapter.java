package khoaluan.vn.flowershop.search;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;

/**
 * Created by samnguyen on 9/16/16.
 */
public class SearchSuggestionAdapter extends BaseQuickAdapter<String> {
    public SearchSuggestionAdapter(List<String> data) {
        super(R.layout.search_suggestion_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_suggestion, s);
    }
}
