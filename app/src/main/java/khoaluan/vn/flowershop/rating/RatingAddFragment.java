package khoaluan.vn.flowershop.rating;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.BaseFragment;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.action.action_view.CommonView;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Rating;
import khoaluan.vn.flowershop.data.parcelable.FlowerSuggesstion;
import khoaluan.vn.flowershop.data.shared_prefrences.UserUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingAddFragment extends BaseFragment implements RatingContract.View, CommonView.ToolBar, Base {

    private RatingContract.Presenter presenter;
    private View root;
    private Activity activity;

    @BindView(R.id.toolbar)
    Toolbar toolbarView;

    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.rating_bar)
    RatingBar rating_bar;

    @BindView(R.id.send_rating)
    Button send_rating;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_price)
    TextView tv_price;

    private Flower flower;
    private FlowerSuggesstion flowerSuggesstion;

    private ProgressDialog progressDialog;


    public RatingAddFragment() {
        // Required empty public constructor
    }

    public static RatingAddFragment newInstance(Flower flower, FlowerSuggesstion flowerSuggesstion){
        RatingAddFragment fragment = new RatingAddFragment();
        Bundle args = new Bundle();
        args.putParcelable(FLOWER_PARCELABLE, flower);
        args.putParcelable(LIST_FLOWER_PARCELABLE, flowerSuggesstion);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_rating_add, container, false);
        flower = (Flower) getArguments().getParcelable(FLOWER_PARCELABLE);
        flowerSuggesstion = (FlowerSuggesstion) getArguments().getParcelable(LIST_FLOWER_PARCELABLE);
        ButterKnife.bind(this, root);
        initilizeToolBar();
        showUI();
        return root;
    }

    @Override
    public void showUI() {

        activity = getActivity();

        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        tv_name.setText(flower.getName());
        tv_price.setText(flower.getMoney(flower.getPrice()));
        send_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDone()) {
                    showIndicator(true, "Đang gởi nhận xét");
                    presenter.sendRating(UserUtils.getUser(activity).getId(),
                            flower.getId(),
                            rating_bar.getNumStars(),
                            content.getText().toString());

                }
            }
        });

    }

    private boolean isDone() {
        content.setError(null);

        String content = this.content.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(content)) {
            this.content.setError(getString(R.string.error_field_required));
            focusView = this.content;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

        }

        return !cancel;
    }

    @Override
    public void showIndicator(boolean active) {

    }

    @Override
    public void showIndicator(boolean active, String message) {
        if (active) {
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    public void setUpRecyclerView() {

    }

    @Override
    public void addToRatings(List<Rating> ratings) {

    }

    @Override
    public void setPresenter(RatingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void noInternetConnectTion() {
        Snackbar.make(root, R.string.no_internet_connecttion, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setDuration(5000)
                .show();
    }

    @Override
    public void initilizeToolBar() {
        toolbarView = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarView);
        toolbarView.setContentInsetsAbsolute(0, 0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarView.setNavigationIcon(R.drawable.ic_back);
        toolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Viết nhận xét ");
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
