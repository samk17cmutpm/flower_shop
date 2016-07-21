package khoaluan.vn.flowershop.main.tab_home;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.Flower;
import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class FlowerAdapter extends BaseQuickAdapter<Flower> {

    private List<Flower> flowers;

    public FlowerAdapter(int layoutResId, List<Flower> flowers) {
        super(layoutResId, flowers);
    }
    public FlowerAdapter(Activity activity, List<Flower> flowers) {
        super(R.layout.flower_item, flowers);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Flower flower) {

    }
}
