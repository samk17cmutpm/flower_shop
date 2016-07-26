package khoaluan.vn.flowershop.action.action_presenter;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;

/**
 * Created by samnguyen on 7/25/16.
 */
public interface RealmAction {

    interface Flower<T> {
        List<T> loadAll(int limit);
        void updateData(List<T> list);
    }

    interface Category<T> {
        List<T> loadLocalFlowerCategories();
        List<T> loadLocalGiftCategories();
        void updateLocalFlowerCategories(List<T> newList);
        void updateLocalGiftCategories(List<T> newList);
    }

}
