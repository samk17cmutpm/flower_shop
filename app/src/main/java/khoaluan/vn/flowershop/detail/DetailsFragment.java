package khoaluan.vn.flowershop.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View, ObservableScrollViewCallbacks, Base {
    private View root;
    private DetailsContract.Presenter presenter;
    private Flower flower;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.scroll)
    ObservableScrollView scrollView;

    @BindView(R.id.content)
    TextView content;

    private int parallaxImageHeight;
    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Flower flower) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FLOWER_PARCELABLE, flower);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        flower = (Flower) getArguments().getParcelable(FLOWER_PARCELABLE);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        return root;
    }

    @Override
    public void showUI() {
        toolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        scrollView.setScrollViewCallbacks(this);
        parallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        ImageUniversalUtils.imageLoader.displayImage(flower.getImage(), imageView, ImageUniversalUtils.options);
        content.setText(flower.getFullDescription());
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / parallaxImageHeight);
        toolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(imageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void initilizeToolBar() {
        toolbarView = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarView);
        toolbarView.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarView.setNavigationIcon(R.drawable.ic_back);
        toolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(flower.getName());
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
