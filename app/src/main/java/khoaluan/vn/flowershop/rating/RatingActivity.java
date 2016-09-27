package khoaluan.vn.flowershop.rating;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.parcelable.Action;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForRating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.utils.ActivityUtils;
import khoaluan.vn.flowershop.utils.OnItemClickUtils;

public class RatingActivity extends BaseActivity implements Base {

    private RatingContract.Presenter presenter;
    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;
    private ActionDefined actionDefined;
    private RatingContract.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        flower = (Flower) getIntent().getParcelableExtra(FLOWER_PARCELABLE);
        flowerSuggesstion = (FlowerSuggesstion) getIntent().getParcelableExtra(LIST_FLOWER_PARCELABLE);
        actionDefined = (ActionDefined) getIntent().getParcelableExtra(Action.ACTION_FOR_RATING);

        switch (actionDefined.getGo()) {
            case ActionForRating.VIEW_RATINGS:
                view = (RatingFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = RatingFragment.newInstance(flower, flowerSuggesstion);
                break;
            case ActionForRating.ADD_RATINGS:
                view = (RatingAddFragment)
                        getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                view = RatingAddFragment.newInstance(flower, flowerSuggesstion);
                break;
        }


        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), (Fragment) view, R.id.contentFrame
        );


        presenter = new RatingPresenter(RatingActivity.this, view);
    }

    @Override
    public void onBackPressed() {
        OnItemClickUtils.flowerDetail(RatingActivity.this, flower,
                flowerSuggesstion, true);
    }
}
