package khoaluan.vn.flowershop.sign_up;

import android.os.Bundle;

import khoaluan.vn.flowershop.BaseActivity;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.ActivityUtils;

public class SignUpActivity extends BaseActivity {
    private SignUpContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpFragment fragment =
                (SignUpFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = SignUpFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        presenter = new SignUpPresenter(fragment, SignUpActivity.this);
    }

    @Override
    public void onBackPressed() {
        ActionUtils.go(SignUpActivity.this, 4);
    }

}
