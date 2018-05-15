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
 * Created by ts on 2018/5/12.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.phone)
    TextInputEditText phone;
    @BindView(R.id.phone_layout)
    TextInputLayout phoneLayout;
    @BindView(R.id.psd)
    TextInputEditText psd;
    @BindView(R.id.psd_layout)
    TextInputLayout psdLayout;
    @BindView(R.id.psd_again)
    TextInputEditText psdAgain;
    @BindView(R.id.psd_again_layout)
    TextInputLayout psdAgainLayout;
    @BindView(R.id.code)
    TextInputEditText code;
    @BindView(R.id.code_layout)
    TextInputLayout codeLayout;
    @BindView(R.id.time)
    TextView timeBtn;
    @BindView(R.id.change_to_login)
    TextView tologin;

    @Override
    public void init() {
        super.init();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register, R.id.change_to_login})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.change_to_reg:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
