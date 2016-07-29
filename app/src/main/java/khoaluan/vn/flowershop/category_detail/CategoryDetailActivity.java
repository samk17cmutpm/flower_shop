package khoaluan.vn.flowershop.category_detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.detail.DetailsContract;
import khoaluan.vn.flowershop.main.tab_category.CategoryFragment;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class CategoryDetailActivity extends BaseActivity implements Base {
    private CategoryDetailContract.Presenter presenter;
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        category = (Category) getIntent().getParcelableExtra(CATEGORY_PARCELABLE);
        CategoryDetailFragment fragment =
                (CategoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = CategoryDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame
            );
        }
        presenter = new CategoryDetailPresenter(fragment, CategoryDetailActivity.this, category);

    }

}
