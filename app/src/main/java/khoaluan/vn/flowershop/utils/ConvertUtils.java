package khoaluan.vn.flowershop.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Advertising;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExpandCategory;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Item;
import khoaluan.vn.flowershop.main.tab_favorite.FavoriteItem;
import khoaluan.vn.flowershop.main.tab_home.MultipleAdvertisingItem;
import khoaluan.vn.flowershop.main.tab_home.MultipleMainItem;
import khoaluan.vn.flowershop.user_data.billings.MultipleBillingItem;

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

    public static List<MultipleMainItem> convertItemsToMultipleItems(List<Item> items) {
        List<MultipleMainItem> multipleMainItemses = new ArrayList<>();

        for (Item item : items)  {
            MultipleMainItem multipleMainItem = new MultipleMainItem();
            switch (item.type()) {
                case MultipleMainItem.ADVERTISING:
                    multipleMainItem.setItemType(MultipleMainItem.ADVERTISING);
                    List<Advertising> advertisings = new ArrayList<>();
                    advertisings.add(item.getAdvertising());
                    multipleMainItem.setAdvertisings(advertisings);

                    multipleMainItemses.add(multipleMainItem);
                    break;
                case MultipleMainItem.BOTH_TITLE_FLOWER:
                    multipleMainItem.setItemType(MultipleMainItem.TITLE);
                    multipleMainItem.setTitle(item.getTitle());
                    multipleMainItemses.add(multipleMainItem);

                    multipleMainItem = new MultipleMainItem();
                    multipleMainItem.setItemType(MultipleMainItem.FLOWER);
                    multipleMainItem.setFlowers(item.getFlowers());
                    multipleMainItemses.add(multipleMainItem);
                    break;
            }
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

    public static List<ExpandCategory> convertCategoriseToExpandCategories(List<Category> categories) {
        Set<String> titles = new LinkedHashSet<>();
        for (Category category : categories)
            titles.add(category.getType());

        final String FLOWER = "FLOWER";
        final String COLOR = "COLOR";
        final String TOPIC = "TOPIC";
        final String BIRTHDAY = "BIRTHDAY";
        final String GIFT = "GIFT";

        List<ExpandCategory> expandCategories = new ArrayList<>();

        for (String title : titles) {
            List<Category> categoryTemp = new ArrayList<>();
            for (Category category : categories) {
                if (title.equals(category.getType()))
                    categoryTemp.add(category);

            }

            String tempTitle = "Hoa";

            switch (title) {
                case FLOWER:
                    tempTitle = "Hoa";
                    break;
                case COLOR:
                    tempTitle = "Màu Sắc";
                    break;
                case TOPIC:
                    tempTitle = "Chủ Đề";
                    break;
                case BIRTHDAY:
                    tempTitle = "Hoa Sinh Nhật";
                    break;

                case GIFT:
                    tempTitle = "Quà Tặng";
                    break;

            }
            expandCategories.add(new ExpandCategory(tempTitle, categoryTemp));

        }

        return expandCategories;
    }

    public static List<MultipleBillingItem> convertBillingToMultipleBillingItem(Billing billing) {
        List<MultipleBillingItem> list = new ArrayList<>();

        MultipleBillingItem header = new MultipleBillingItem();
        header.setItemType(MultipleBillingItem.HEADER);
        header.setBilling(billing);

        list.add(header);

        MultipleBillingItem products = new MultipleBillingItem();
        products.setItemType(MultipleBillingItem.PROODUCT);
        products.setBilling(billing);

        list.add(products);

        MultipleBillingItem info = new MultipleBillingItem();
        info.setItemType(MultipleBillingItem.INFO);
        info.setBilling(billing);

        list.add(info);

        return list;

    }

}
