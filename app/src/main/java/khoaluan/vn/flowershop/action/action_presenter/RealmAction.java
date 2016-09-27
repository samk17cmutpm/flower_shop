package khoaluan.vn.flowershop.action.action_presenter;

import java.util.List;

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

    interface FlowerByCategory<T> {
        List<T> loadLocalFlowersByCategory(String categoryId);
        void updateLocalFlowersByCategory(String categoryId, List<T> list);
    }

}
