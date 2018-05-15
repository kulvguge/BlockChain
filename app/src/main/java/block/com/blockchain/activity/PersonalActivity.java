package block.com.blockchain.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import block.com.blockchain.R;
import block.com.blockchain.customview.BasicInfoView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/14.
 */

public class PersonalActivity extends BaseActivity {
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

    @Override
    public void init() {
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);

    }
}
