package khoaluan.vn.flowershop.main.tab_info;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.data.InfoType;
import khoaluan.vn.flowershop.main.MainActivity;

/**
 * Created by samnguyen on 7/19/16.
 */
public class InfoPresenter implements InfoContract.Presenter {
    private static String TAG = InfoPresenter.class.getName();
    private final MainActivity activity;
    private final InfoContract.View view;
    public InfoPresenter (MainActivity activity, InfoContract.View view) {
        this.activity = activity;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void loadData() {
        this.view.showInfoTypes(loadDumpData());
    }

    private List<InfoType> loadDumpData() {
        List<InfoType> infoTypes = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            infoTypes.add(new InfoType(i, i + ""));
        return infoTypes;
    }

    @Override
    public void start() {
        Log.d(TAG, "Starting Info Tab");
    }
}
