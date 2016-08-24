package khoaluan.vn.flowershop.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class DetailsActivity extends BaseActivity implements Base {
    private DetailsContract.Presenter presenter;
    private Flower flower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        flower = (Flower) getIntent().getParcelableExtra(FLOWER_PARCELABLE);
        DetailsFragment detailsFragment =
                (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (detailsFragment == null) {
            detailsFragment = DetailsFragment.newInstance(flower);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailsFragment, R.id.contentFrame
            );
        }

        presenter = new DetailsPresenter(detailsFragment, DetailsActivity.this);
    }

}