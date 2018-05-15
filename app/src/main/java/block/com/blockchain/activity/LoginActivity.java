package block.com.blockchain.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import block.com.blockchain.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ts on 2018/5/15.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phone)
    TextInputEditText phone;
    @BindView(R.id.phone_layout)
    TextInputLayout phoneLayout;
    @BindView(R.id.psd)
    TextInputEditText psd;
    @BindView(R.id.psd_layout)
    TextInputLayout psdLayout;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.change_to_reg)
    TextView to_reg;

    @Override
    public void init() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.change_to_reg, R.id.login})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.change_to_reg:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.login:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}
