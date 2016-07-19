package khoaluan.vn.flowershop.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.ActtachMainView;

public class MainActivity extends BaseActivity implements ActtachMainView, Base {

    private BottomBar bottomBar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomTabBar(savedInstanceState);
        setUpViewPager();
        injectBottomTabsToViewPager();
    }

    @Override
    public void setUpBottomTabBar(Bundle bundle) {

        bottomBar = BottomBar.attach(this, bundle,
                Color.parseColor("#FFFFFF"), // Background Color
                ContextCompat.getColor(this, R.color.colorPrimary), // Tab Item Color
                0.25f); // Tab Item Alpha

        bottomBar.setItems(R.menu.bottom_menu_main);

        BottomBarBadge nearbyBadge = bottomBar.makeBadgeForTabAt(3, R.color.red, 5);
        nearbyBadge.setAutoShowAfterUnSelection(true);

    }

    @Override
    public void setUpViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(new MainTabsPagerAdapter(getSupportFragmentManager(), this));
    }

    @Override
    public void injectBottomTabsToViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getBottomBar().selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.tab_home:
                        getViewPager().setCurrentItem(TAB_HOME, true);
                        break;
                    case R.id.tab_types:
                        getViewPager().setCurrentItem(TAB_TYPE, true);
                        break;
                    case R.id.tab_search:
                        getViewPager().setCurrentItem(TAB_SEARCH, true);
                        break;
                    case R.id.tab_cart:
                        getViewPager().setCurrentItem(TAB_SHOP, true);
                        break;
                    case R.id.tab_info:
                        getViewPager().setCurrentItem(TAB_INFO, true);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                Toast.makeText(getApplicationContext(), TabMessage.get(menuItemId, true), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public BottomBar getBottomBar() {
        return bottomBar;
    }

    public void setBottomBar(BottomBar bottomBar) {
        this.bottomBar = bottomBar;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
