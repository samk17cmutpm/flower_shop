package khoaluan.vn.flowershop.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.ActtachMainView;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.notifycation.NotifycationActivity;
import khoaluan.vn.flowershop.realm_data_local.RealmCartUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmCategoryUtils;
import khoaluan.vn.flowershop.realm_data_local.RealmFlag;
import khoaluan.vn.flowershop.search.SearchActivity;
import khoaluan.vn.flowershop.utils.CartUtils;
import khoaluan.vn.flowershop.utils.ConvertUtils;

public class MainActivity extends BaseActivity implements ActtachMainView, Base, CommonView.ToolBar {

    private BottomBar bottomBar;

    private RealmResults<Cart> carts;

    private BottomBarBadge nearbyBadge;

    private RealmResults<Category> categories;

    @BindView(R.id.vpPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.left_drawer)
    RecyclerView recyclerView;

    private ActionDefined actionDefined;

    private Menu menu;

    private MenuItem menuItemBadge;

    private MainDrawerAdapter adapter;

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        } else {
            initilizeToolBar();
            setUpBottomTabBar(savedInstanceState);
            setUpViewPager();
            injectBottomTabsToViewPager();

            categories = RealmCategoryUtils.all(RealmFlag.FLOWER);
            categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
                @Override
                public void onChange(RealmResults<Category> element) {
                    loadFlowerCategories();
                }
            });

            loadFlowerCategories();

            actionDefined = (ActionDefined) getIntent().getParcelableExtra(Action.TAB);
            if (actionDefined != null) {
                viewPager.setCurrentItem(actionDefined.getGo(), false);
            }
        }
    }

    @Override
    public void setUpBottomTabBar(Bundle bundle) {

        bottomBar = BottomBar.attach(this, bundle,
                Color.parseColor("#FFFFFF"), // Background Color
                ContextCompat.getColor(this, R.color.colorPrimary), // Tab Item Color
                0.25f); // Tab Item Alpha

        bottomBar.setItems(R.menu.bottom_menu_main);
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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItemBadge = this.menu.findItem(R.id.item_samplebadge);

        carts = RealmCartUtils.all();
        carts.addChangeListener(new RealmChangeListener<RealmResults<Cart>>() {
            @Override
            public void onChange(RealmResults<Cart> element) {
                updateBadge(CartUtils.getSum(element), menuItemBadge);
            }
        });
        updateBadge(CartUtils.getSum(carts), menuItemBadge);

        return true;
    }

    public void updateBadge(int number, MenuItem menuItemBadge) {
        if (number != 0)
            ActionItemBadge.update(this, menuItemBadge,
                this.getResources().getDrawable(R.drawable.shopping_cart),
                ActionItemBadge.BadgeStyles.RED, number);

        else
            menuItemBadge.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_samplebadge:
                viewPager.setCurrentItem(3);
                break;
            case R.id.alarm:
                Intent intentAlarm = new Intent(MainActivity.this, NotifycationActivity.class);
                startActivity(intentAlarm);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new MaterialDialog.Builder(MainActivity.this)
                    .title("Livizi")
                    .content("Bạn muốn thoát khỏi ứng dụng ?")
                    .positiveText("Có")
                    .negativeText("Không")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }
    }

    public void loadFlowerCategories() {
        adapter = new MainDrawerAdapter(MainActivity.this, recyclerView,
                ConvertUtils.convertCategoriseToExpandCategories(categories), drawerLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        if (categories != null)
            categories.removeChangeListeners();
        super.onDestroy();
    }


}
