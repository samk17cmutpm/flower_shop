package khoaluan.vn.flowershop.utils;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.City;
import khoaluan.vn.flowershop.data.model_parse_and_realm.District;

/**
 * Created by samnguyen on 9/24/16.
 */

public class AddressUtils {
    public static String getIdOfCity(String name, List<City> cities) {
        for (City city : cities)
            if (city.getName().equals(name))
                return city.getId();

        return null;
    }

    public static String getIdOfDistrict(String name, List<District> districts) {
        for (District district : districts )
            if (district.getName().equals(name))
                return district.getId();

        return null;
    }
}
