package khoaluan.vn.flowershop.action.action_presenter;

import java.util.List;

/**
 * Created by samnguyen on 7/25/16.
 */
public interface RealmAction<T> {
    List<T> loadAll();
    void updateData(List<T> list);
}
