package block.com.blockchain.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import block.com.blockchain.R;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.TokenBean;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.SPUtils;
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
        String auth = (String) SPUtils.getFromApp(HttpConstant.UserInfo.AUTH, "");
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
                getToken();
                break;
        }

    }

    private void getToken() {
        AjaxParams params = new AjaxParams();
        params.put("client_id", 2 + "");
        params.put("grant_type", "password");
        params.put("client_secret", "MBXnuUcWOpdxvWfjTxLu2QR7nHt2Fdk7BHtpwks6");
        params.put("scope", "*");
        params.put("username", phone.getText().toString());
        params.put("password", psd.getText().toString());
        HttpSendClass.getInstance().post(params, SenUrlClass.TOKEN, new
                AjaxCallBack<TokenBean>() {
                    @Override
                    public void onSuccess(TokenBean resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.getAccess_token() != null) {
                            SPUtils.saveToApp(HttpConstant.UserInfo.AUTH, resultInfo.getAccess_token());
                            SPUtils.saveToApp(HttpConstant.UserInfo.USER_PHONE, phone.getText().toString());
                            Log.e("Object_接收=responseUrl=", "(" + resultInfo.getAccess_token() + ")");
                            beginLogin();
                        } else {
                            Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R
                                    .string
                                    .login_failure), Toast
                                    .LENGTH_SHORT)
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void beginLogin() {
        AjaxParams params = new AjaxParams();
        params.put("type", 2 + "");
        params.put("mobile", phone.getText().toString());
        params.put("pwd", psd.getText().toString());
        HttpSendClass.getInstance().post(params, SenUrlClass.LOGIN, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            seesion();
                        } else {
                            Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string
                                    .login_failure), Toast
                                    .LENGTH_SHORT)
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void seesion() {
        AjaxParams params = new AjaxParams();
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.IS_SESSION, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        if (s.status.equals("success")) {
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录异常", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                    }
                });
    }


}
