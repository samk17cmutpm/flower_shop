package khoaluan.vn.flowershop.main.tab_type;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.data.Type;
import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class TypePresenter implements TypeContract.Presenter {
    private final TypeContract.View view;
    private final MainActivity activity;

    public TypePresenter(MainActivity activity, TypeContract.View view) {
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
    }

    @Override
    public void loadData() {
        this.view.showFlowerTypes(dumpData());
    }

    private List<Type> dumpData() {
        List<Type> types = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            types.add(new Type(i, "data"));
        }
        return types;
    }

    @Override
    public void start() {

    }
}
