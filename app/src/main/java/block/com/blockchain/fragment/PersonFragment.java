package block.com.blockchain.fragment;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import block.com.blockchain.R;
import block.com.blockchain.activity.LoginActivity;
import block.com.blockchain.activity.MyInfoActivity;
import block.com.blockchain.activity.ScoreActivity;
import block.com.blockchain.callback.SelectListener;
import block.com.blockchain.customview.CommonInfoView;
import block.com.blockchain.utils.DialogUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ts on 2018/5/12.
 */

public class PersonFragment extends BaseFragment {
    @BindView(R.id.mine_title)
    Toolbar mineTitle;
    @BindView(R.id.mine_img)
    ImageView mineImg;
    @BindView(R.id.mine_nick)
    TextView mineNick;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_to_person)
    LinearLayout mineToPerson;
    @BindView(R.id.layout_score)
    LinearLayout layout_score;

    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.right_icon)
    ImageView rightIcon;
    @BindView(R.id.layout_phone)
    CommonInfoView layoutPhone;
    @BindView(R.id.layout_work)
    CommonInfoView layoutWork;
    @BindView(R.id.layout_qrcode)
    CommonInfoView layoutQrcode;
    @BindView(R.id.layout_motify)
    CommonInfoView layoutMotify;


    @Override
    public int getResView() {
        return R.layout.fragment_person;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void init() {
        mineTitle.inflateMenu(R.menu.mine);
        mineTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                DialogUtil.showDialog(getActivity(), "", "确定退出当前账号", "确定", "取消", new SelectListener() {
                    @Override
                    public void confirm() {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void cancel() {

                    }
                });
                return false;
            }
        });
    }

    @OnClick({R.id.layout_phone, R.id.layout_work, R.id.layout_qrcode, R.id.layout_motify, R.id.mine_to_person, R.id
            .layout_score})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_phone:
                break;
            case R.id.layout_work:
                break;
            case R.id.layout_qrcode:
                break;
            case R.id.layout_motify:
                break;
            case R.id.mine_to_person:
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_score:
                Intent intentS = new Intent(getActivity(), ScoreActivity.class);
                startActivity(intentS);
                break;
        }
    }
}
