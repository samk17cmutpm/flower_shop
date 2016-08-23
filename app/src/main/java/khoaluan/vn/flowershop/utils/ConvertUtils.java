package khoaluan.vn.flowershop.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.main.tab_home.MultipleAdvertisingItem;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItem;

/**
 * Created by samnguyen on 8/23/16.
 */
public class ConvertUtils {

    public static List<MultipleMainItem> convertTopProductsToMultipleItems(List<Flower> flowers) {
        // Create new Set
        Set<String> idCategories = new LinkedHashSet<>();
        for (Flower flower : flowers) {
            idCategories.add(flower.getCategoryId());
        }
        // Create new List
        List<MultipleMainItem> multipleMainItemses = new ArrayList<>();

        for (String idCategory : idCategories) {
            // Title
            MultipleMainItem multipleMainItemTitle = new MultipleMainItem();
            multipleMainItemTitle.setItemType(MultipleMainItem.TITLE);
            // Flowers
            MultipleMainItem multipleMainItemFlower = new MultipleMainItem();
            multipleMainItemFlower.setItemType(MultipleMainItem.FLOWER);

            List<Flower> flowersTemp = new ArrayList<>();
            for (Flower flower : flowers) {
                if (flower.getCategoryId().equals(idCategory)) {
                    flowersTemp.add(flower);
                    multipleMainItemTitle.setTitle(flower.getCategoryName());
                }

            }
            multipleMainItemFlower.setFlowers(flowersTemp);

            multipleMainItemses.add(multipleMainItemTitle);
            multipleMainItemses.add(multipleMainItemFlower);
        }

        return multipleMainItemses;

    }

    public static List<MultipleAdvertisingItem> convertAdvertisingToMultipleAdvertisingItem(List<Advertising> advertisings) {
        List<MultipleAdvertisingItem> multipleAdvertisingItems = new ArrayList<>();
        for (Advertising advertising : advertisings) {
            MultipleAdvertisingItem multipleAdvertisingItem = new MultipleAdvertisingItem(advertising.getAdvertisingItems());
            if (advertising.getAdvertisingItems().size() > 1) {
                multipleAdvertisingItem.setItemType(MultipleAdvertisingItem.MORE);
            } else {
                multipleAdvertisingItem.setItemType(MultipleAdvertisingItem.ONLY_ONE);
            }
            multipleAdvertisingItems.add(multipleAdvertisingItem);
        }
        return multipleAdvertisingItems;
    }
}
