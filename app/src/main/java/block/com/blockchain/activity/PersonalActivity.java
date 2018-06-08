package block.com.blockchain.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import block.com.blockchain.R;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.BasicInfoView;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/14.
 */

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.big_img)
    ImageView bigImg;
    @BindView(R.id.small_img)
    ImageView smallImg;
    @BindView(R.id.person_nick_name)
    TextView personNickName;
    @BindView(R.id.person_name)
    BasicInfoView personName;
    @BindView(R.id.person_phone)
    BasicInfoView personPhone;
    @BindView(R.id.person_sex)
    BasicInfoView personSex;
    @BindView(R.id.person_birthday)
    BasicInfoView personBirthday;
    @BindView(R.id.person_work)
    BasicInfoView personWork;

    @BindView(R.id.person_signature)
    BasicInfoView personSignature;
    @BindView(R.id.person_title)
    Toolbar personTitle;
    private String moblie = "";
    private Intent intent;
    private final int REQUEST_PHONE = 1;

    @Override
    public void init() {
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        moblie = getIntent().getStringExtra("moblie");
        getUserInfo();
        personPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!personPhone.isEmpty())
                    checkPermission(personPhone.getRightMsg());
            }
        });
        personTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 查询用户资料
     */
    private void getUserInfo() {
        AjaxParams params = new AjaxParams();
        params.put("type", 2 + "");
        params.put("mobile", moblie);
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.USER_INFO, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            dataSet(resultInfo.data);
                        } else {
                            Toast.makeText(PersonalActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(PersonalActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dataSet(UserBean userBean) {
        personNickName.setText(userBean.getNickname());
        personName.setRightMsg(userBean.getReal_name());
        if (userBean.getSex() == 1) {
            personSex.setRightMsg(R.string.person_sex_man);
        } else if (userBean.getSex() == 2) {
            personSex.setRightMsg(R.string.person_sex_woman);
        }
        personPhone.setRightMsg(userBean.getMobile());
        personBirthday.setRightMsg(userBean.getBirthday());
        personWork.setRightMsg(userBean.getEnterprise());
        personSignature.setRightMsg(userBean.getSelf_sign());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.default_head);
        options.error(R.mipmap.default_head);
        Glide.with(this).load(userBean.getPic_url()).apply(options).into(bigImg);
        Glide.with(this).load(userBean.getPic_url()).apply(options).into(smallImg);
    }

    /**
     * 权限检测
     */
    private void checkPermission(String phone) {
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager
                    .PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_PHONE);
                return;
            }
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE && grantResults[0] == PackageManager
                .PERMISSION_GRANTED) {
            startActivity(intent);
        }
    }
}
