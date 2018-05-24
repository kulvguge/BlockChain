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
import block.com.blockchain.bean.CodeBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.TimeButton;
import block.com.blockchain.request.NetWork;
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
    TimeButton timeBtn;
    @BindView(R.id.change_to_login)
    TextView tologin;
    private Intent intent = null;

    @Override
    public void init() {
        super.init();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register, R.id.change_to_login, R.id.time})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register:
                if (hasComplete())
                    NetWork.ApiSubscribe(NetWork.getRequestApi().register(phone.getText().toString(), code.getText()
                                    .toString(), null, psd.getText().toString(), psdAgain.getText().toString()),
                            registerObserver);
                return;
            case R.id.change_to_login:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.time:
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError(this.getResources().getString(R.string.warn_phone));
                    return;
                } else {
                    phoneLayout.setErrorEnabled(false);
                }
                NetWork.ApiSubscribe(NetWork.getRequestApi().getMsgCode(1, phone.getText().toString()), observer);
                return;
        }
        startActivity(intent);
        finish();
    }

    /**
     * 信息完整性检查
     */
    private boolean hasComplete() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            phoneLayout.setErrorEnabled(true);
            phoneLayout.setError(this.getResources().getString(R.string.warn_phone));
            return false;
        } else {
            phoneLayout.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(psd.getText().toString())) {
            psdLayout.setErrorEnabled(true);
            psdLayout.setError(this.getResources().getString(R.string.warn_psd));
            return false;
        } else {
            psdLayout.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(psdAgain.getText().toString())) {
            psdAgainLayout.setErrorEnabled(true);
            psdAgainLayout.setError(this.getResources().getString(R.string.warn_psd_agin));
            return false;
        } else {
            psdAgainLayout.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(code.getText().toString())) {
            codeLayout.setErrorEnabled(true);
            codeLayout.setError(this.getResources().getString(R.string.warn_code));
            return false;
        } else {
            codeLayout.setErrorEnabled(false);
        }
        if (!psd.getText().toString().equals(psdAgain.getText().toString())) {
            psdAgainLayout.setErrorEnabled(true);
            psdAgainLayout.setError(this.getResources().getString(R.string.warn_no_same));
            return false;
        } else {
            psdAgainLayout.setErrorEnabled(false);
        }
        return true;
    }

    Subscriber<ResultInfo<CodeBean>> observer = new Subscriber<ResultInfo<CodeBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(ResultInfo<CodeBean> resultInfo) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_has_send_code), Toast
                    .LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };

    Subscriber<ResultInfo<UserBean>> registerObserver = new Subscriber<ResultInfo<UserBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(ResultInfo<UserBean> resultInfo) {
            if (resultInfo.status.equals("success")) {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_success), Toast
                        .LENGTH_SHORT)
                        .show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };
}
