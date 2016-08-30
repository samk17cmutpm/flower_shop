package khoaluan.vn.flowershop.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.ActtachMainView;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.response.CategoryResponse;
import khoaluan.vn.flowershop.realm_data_local.RealmCategoryUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.realm_data_local.RealmFlowerUtils;
import khoaluan.vn.flowershop.retrofit.ServiceGenerator;
import khoaluan.vn.flowershop.retrofit.client.FlowerClient;
import khoaluan.vn.flowershop.search.SearchActivity;
import khoaluan.vn.flowershop.utils.ConvertUtils;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements ActtachMainView, Base, CommonView.ToolBar {

    private BottomBar bottomBar;

    private RealmResults<Flower> flowers;

    private BottomBarBadge nearbyBadge;

    @BindView(R.id.vpPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.left_drawer)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initilizeToolBar();
        setUpBottomTabBar(savedInstanceState);
        setUpViewPager();
        injectBottomTabsToViewPager();
        loadFlowerCategories();
    }

    @Override
    public void setUpBottomTabBar(Bundle bundle) {

        bottomBar = BottomBar.attach(this, bundle,
                Color.parseColor("#FFFFFF"), // Background Color
                ContextCompat.getColor(this, R.color.colorPrimary), // Tab Item Color
                0.25f); // Tab Item Alpha

        bottomBar.setItems(R.menu.bottom_menu_main);

        flowers = RealmFlowerUtils.findBy(RealmFlag.FLAG, RealmFlag.CART);

        nearbyBadge = bottomBar.makeBadgeForTabAt(3, R.color.red, flowers.size());
        nearbyBadge.setAutoShowAfterUnSelection(true);

        flowers.addChangeListener(new RealmChangeListener<RealmResults<Flower>>() {
            @Override
            public void onChange(RealmResults<Flower> element) {
                if (element.size() != flowers.size())
                    nearbyBadge.setCount(element.size());
            }
        });

    }
    @Override
    public void setUpViewPager() {
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
                        getViewPager().setCurrentItem(TAB_FAVORITE, true);
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

    @Override
    public void initilizeToolBar() {
        setSupportActionBar(toolbarView);
        toolbarView.setContentInsetsAbsolute(0, 0);
        toolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("Livizi");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarView, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadFlowerCategories() {

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new MainDrawerAdapter(MainActivity.this, recyclerView,
                ConvertUtils.convertCategoriseToExpandCategories(RealmCategoryUtils.all(RealmFlag.FLOWER))));

        Observable<Response<CategoryResponse>> observable =
                ServiceGenerator.createService(FlowerClient.class).getFlowerCategories();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<CategoryResponse>>() {
                    private List<Category> categories = new ArrayList<Category>();
                    @Override
                    public void onCompleted() {
                        recyclerView.setAdapter(new MainDrawerAdapter(MainActivity.this, recyclerView,
                                ConvertUtils.convertCategoriseToExpandCategories(categories)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<CategoryResponse> categoryResponseResponse) {
                        if (categoryResponseResponse.isSuccessful())
                            categories.addAll(categoryResponseResponse.body().getResult());
                    }
                });
    }
}
