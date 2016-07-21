package khoaluan.vn.flowershop.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BasePresenter;
import khoaluan.vn.flowershop.lib.SmartFragmentStatePagerAdapter;
import khoaluan.vn.flowershop.main.tab_home.HomeContract;
import khoaluan.vn.flowershop.main.tab_home.HomeFragment;
import khoaluan.vn.flowershop.main.tab_home.HomePresenter;
import khoaluan.vn.flowershop.main.tab_info.InfoContract;
import khoaluan.vn.flowershop.main.tab_info.InfoFragment;
import khoaluan.vn.flowershop.main.tab_info.InfoPresenter;
import khoaluan.vn.flowershop.main.tab_search.SearchContract;
import khoaluan.vn.flowershop.main.tab_search.SearchFragment;
import khoaluan.vn.flowershop.main.tab_search.SearchPresenter;
import khoaluan.vn.flowershop.main.tab_shop.ShopContract;
import khoaluan.vn.flowershop.main.tab_shop.ShopFragment;
import khoaluan.vn.flowershop.main.tab_shop.ShopPresenter;
import khoaluan.vn.flowershop.main.tab_type.TypeContract;
import khoaluan.vn.flowershop.main.tab_type.TypeFragment;
import khoaluan.vn.flowershop.main.tab_type.TypePresenter;

/**
 * Created by samnguyen on 7/19/16.
 */
public class MainTabsPagerAdapter extends SmartFragmentStatePagerAdapter implements Base{
    private final MainActivity activity;
    private BasePresenter presenter;
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
                presenter = new
                        HomePresenter(activity, (HomeContract.View) fragment);
                break;
            case TAB_TYPE:
                fragment = new TypeFragment().newInstance();
                presenter = new
                        TypePresenter(activity, (TypeContract.View) fragment);
                break;
            case TAB_SEARCH:
                fragment = new SearchFragment().newInstance();
                presenter = new
                        SearchPresenter(activity, (SearchContract.View) fragment);
                break;
            case TAB_SHOP:
                fragment = new ShopFragment().newInstance();
                presenter = new
                        ShopPresenter(activity, (ShopContract.View) fragment);
                break;
            case TAB_INFO:
                fragment = new InfoFragment().newInstance();
                presenter = new
                        InfoPresenter(activity, (InfoContract.View) fragment);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
