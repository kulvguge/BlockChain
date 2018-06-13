package block.com.blockchain.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import block.com.blockchain.R;
import block.com.blockchain.activity.LoginActivity;
import block.com.blockchain.activity.MainActivity;
import block.com.blockchain.activity.MyInfoActivity;
import block.com.blockchain.activity.ScoreActivity;
import block.com.blockchain.bean.MotifyUserBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.customview.CommonInfoView;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.DialogUtil;
import block.com.blockchain.utils.SPUtils;
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
    @BindView(R.id.parent_layout)
    RelativeLayout parent_layout;
    private String url = "";
    private String inviteCode = "-1";
    private MotifyUserBean userBean;
    @Override
    public int getResView() {
        return R.layout.fragment_person;
    }

    @Override
    public void onRefresh() {
        getUserInfo();
    }

    @Override
    public void init() {
        parent_layout.setPadding(0, HttpConstant.PhoneInfo.STATUS_HEIGHT, 0, 0);
        mineTitle.inflateMenu(R.menu.mine);
        mineTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DialogUtil.showPromptDialog(getActivity(), null, "确定退出当前账号", "确定", null, "取消", new DialogUtil
                        .OnMenuClick() {


                    @Override
                    public void onLeftMenuClick() {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        SPUtils.saveToApp(HttpConstant.UserInfo.AUTH, "");
                        getActivity().finish();
                    }

                    @Override
                    public void onCenterMenuClick() {

                    }

                    @Override
                    public void onRightMenuClick() {

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
//            case R.id.layout_phone:
//                if (!TextUtils.isEmpty(layoutPhone.getRightText())) {
//                    checkPermission(layoutPhone.getRightText());
//                }
//                break;
            case R.id.layout_qrcode:
                if (TextUtils.isEmpty(inviteCode)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.mine_no_invite), Toast
                            .LENGTH_SHORT).show();
                } else {
                    if ("-1".equals(inviteCode)) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.mine_date_err), Toast
                                .LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showQRCodeDialog(inviteCode, getActivity());
                    }
                }
                break;
            case R.id.layout_phone:
            case R.id.layout_motify:
            case R.id.layout_work:
            case R.id.mine_to_person:
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                intent.putExtra("user_info",userBean);
                if (Build.VERSION.SDK_INT >= 21) {
                    Pair pair = new Pair<View, String>(mineImg, "btn1");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);
                    this.startActivityForResult(intent, MainActivity.USER_INFO, options.toBundle());
                } else {
                    this.startActivityForResult(intent, MainActivity.USER_INFO);
                }

                break;
            case R.id.layout_score:
                Intent intentS = new Intent(getActivity(), ScoreActivity.class);
                intentS.putExtra("nickName", mineNick.getText().toString());
                intentS.putExtra("url", url);
                startActivity(intentS);
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        AjaxParams params = new AjaxParams();
        params.put("type", 1 + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.USER_INFO, new
                AjaxCallBack<ResultInfo<MotifyUserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<MotifyUserBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            userBean=resultInfo.data;
                            dataSet(resultInfo.data);
                        } else {
                            Toast.makeText(getActivity(), resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dataSet(MotifyUserBean userBean) {
        mineNick.setText(userBean.getNickname());
        mineName.setText(userBean.getReal_name());
        layoutPhone.setCenterText(userBean.getMobile());
        layoutWork.setCenterText(userBean.getEnterprise());
        score.setText(userBean.getIntegral());
        url = userBean.getPic_url();
        inviteCode = userBean.getInvite_code();
        Glide.with(this).load(HttpConstant.HTTPHOST+userBean.getPic_url()).apply(new RequestOptions().placeholder(R.mipmap.default_head))
                .into(mineImg);
    }
}
