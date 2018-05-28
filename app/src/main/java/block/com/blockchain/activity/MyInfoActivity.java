package block.com.blockchain.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import block.com.blockchain.R;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.BasicEditInfoView;
import block.com.blockchain.customview.BasicInfoView;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/14.
 */

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.big_img)
    ImageView bigImg;
    @BindView(R.id.small_img)
    ImageView smallImg;
    @BindView(R.id.person_nick_name)
    TextView personNickName;
    @BindView(R.id.person_name)
    BasicEditInfoView personName;
    @BindView(R.id.person_phone)
    BasicEditInfoView personPhone;
    @BindView(R.id.person_sex)
    BasicInfoView personSex;
    @BindView(R.id.person_birthday)
    BasicInfoView personBirthday;
    @BindView(R.id.person_work)
    BasicEditInfoView personWork;
    @BindView(R.id.person_signature)
    BasicEditInfoView personSignature;
    @BindView(R.id.person_title)
    Toolbar personTitle;

    @Override
    public void init() {
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);

        getUserInfo();
    }

    private void getUserInfo() {
        AjaxParams params = new AjaxParams();
        params.put("type", 1 + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.TOKEN, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            dataSet(resultInfo.data);
                        } else {
                            Toast.makeText(MyInfoActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(MyInfoActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
        Glide.with(this).load(userBean.getPic_url()).into(bigImg);
        Glide.with(this).load(userBean.getPic_url()).into(smallImg);
    }
}