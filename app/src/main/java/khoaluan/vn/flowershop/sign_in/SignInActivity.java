package khoaluan.vn.flowershop.sign_in;

import android.os.Bundle;

import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class SignInActivity extends BaseActivity {
    private SignInContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignInFragment fragment =
                (SignInFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = SignInFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame
            );
        }
        presenter = new SignInPresenter(fragment, SignInActivity.this);
    }
}

