package block.com.blockchain.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import block.com.blockchain.R;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.TokenBean;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.NetWork;
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
    private Intent intent = null;

    @Override
    public void init() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.change_to_reg, R.id.login})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.change_to_reg:

                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.login:
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError(this.getResources().getString(R.string.warn_phone));
                    return;
                } else {
                    phoneLayout.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(psd.getText().toString())) {
                    psdLayout.setErrorEnabled(true);
                    psdLayout.setError(this.getResources().getString(R.string.warn_psd));
                    return;
                } else {
                    psdLayout.setErrorEnabled(false);
                }
                NetWork.ApiSubscribe(NetWork.getRequestApi().loginPwd(2, phone.getText().toString(), psd.getText()
                                .toString()),
                        observer);

                break;
        }

    }

    Subscriber<ResultInfo<UserBean>> observer = new Subscriber<ResultInfo<UserBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(ResultInfo<UserBean> resultInfo) {
            if (resultInfo.status.equals("success")) {
                NetWork.ApiSubscribe(NetWork.getTokenApi().getToken(2, "password",
                        "MBXnuUcWOpdxvWfjTxLu2QR7nHt2Fdk7BHtpwks6", "*", phone.getText().toString(), psd.getText()
                                .toString()),
                        observerToken);
            } else {
                Toast.makeText(LoginActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };
    Subscriber<TokenBean> observerToken = new Subscriber<TokenBean>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(TokenBean resultInfo) {
            if (resultInfo.getAccess_token() != null) {
                HttpConstant.Authorization = resultInfo.getAccess_token();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string
                        .login_failure), Toast
                        .LENGTH_SHORT)
                        .show();
            }

        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };
}
