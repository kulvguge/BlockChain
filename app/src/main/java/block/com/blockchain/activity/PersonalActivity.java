package block.com.blockchain.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import block.com.blockchain.R;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.BasicInfoView;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.NetWork;
import block.com.blockchain.utils.SPUtils;
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
    private String moblie="";
    @Override
    public void init() {
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        moblie=getIntent().getStringExtra("intent");
        NetWork.ApiSubscribe(NetWork.getRequestApi().queryUserInfo(2,moblie),subscriber);
    }
    private void dataSet(UserBean userBean){
        personNickName.setText(userBean.getNickname());
        personName.setRightMsg(userBean.getReal_name());
        if(userBean.getSex()==1){
            personSex.setRightMsg(R.string.person_sex_man);
        }else if(userBean.getSex()==2){
            personSex.setRightMsg(R.string.person_sex_woman);
        }
        personPhone.setRightMsg(userBean.getMobile());
        personBirthday.setRightMsg(userBean.getBirthday());
        personWork.setRightMsg(userBean.getEnterprise());
        personSignature.setRightMsg(userBean.getSelf_sign());
        Glide.with(this).load(userBean.getUrl()).into(bigImg);
        Glide.with(this).load(userBean.getUrl()).into(smallImg);
    }

    Subscriber<ResultInfo<UserBean>> subscriber=new Subscriber<ResultInfo<UserBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(ResultInfo<UserBean> userBeanResultInfo) {
             if(userBeanResultInfo.status.equals("success")){
                 dataSet(userBeanResultInfo.data);
             }else{
                 Toast.makeText(PersonalActivity.this, userBeanResultInfo.message, Toast.LENGTH_SHORT).show();
             }
        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(PersonalActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };
}
