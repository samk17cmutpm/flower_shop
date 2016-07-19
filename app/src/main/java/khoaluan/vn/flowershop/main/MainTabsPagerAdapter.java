package khoaluan.vn.flowershop.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.lib.SmartFragmentStatePagerAdapter;
import khoaluan.vn.flowershop.main.tab_home.HomeFragment;
import khoaluan.vn.flowershop.main.tab_info.InfoFragment;
import khoaluan.vn.flowershop.main.tab_search.SearchFragment;
import khoaluan.vn.flowershop.main.tab_shop.ShopFragment;
import khoaluan.vn.flowershop.main.tab_type.TypeFragment;

/**
 * Created by samnguyen on 7/19/16.
 */
public class MainTabsPagerAdapter extends SmartFragmentStatePagerAdapter implements Base{
    private final MainActivity activity;
    public MainTabsPagerAdapter(FragmentManager fragmentManager, MainActivity activity) {
        super(fragmentManager);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TAB_HOME:
                fragment = new HomeFragment().newInstance();
                break;
            case TAB_TYPE:
                fragment = new TypeFragment().newInstance();
                break;
            case TAB_SEARCH:
                fragment = new SearchFragment().newInstance();
                break;
            case TAB_SHOP:
                fragment = new ShopFragment().newInstance();
                break;
            case TAB_INFO:
                fragment = new InfoFragment().newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
