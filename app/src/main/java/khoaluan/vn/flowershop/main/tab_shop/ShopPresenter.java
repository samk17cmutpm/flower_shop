package khoaluan.vn.flowershop.main.tab_shop;

import java.util.List;

import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.main.MainActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;

/**
 * Created by samnguyen on 7/19/16.
 */
public class ShopPresenter implements ShopContract.Presenter {
    private final MainActivity activity;
    private final ShopContract.View view;
    public ShopPresenter (MainActivity activity, ShopContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {

    }

    @Override
    public RealmResults<Flower> loadCartFlowers() {
        return RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.CART);
    }

    @Override
    public int countTotalMoney(List<Flower> flowers) {
        int total = 0;
        for (Flower flower : flowers) {
            total = total + (flower.getPrice() * flower.getNumber());
        }
        return total;
    }

    @Override
    public void start() {

    }
}
