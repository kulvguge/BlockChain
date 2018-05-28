package block.com.blockchain.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.bean.ApplyBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.fragment.ApplyFragment;
import block.com.blockchain.fragment.BaseFragment;
import block.com.blockchain.mainpage.FragmentViewPagerAdapter;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/16.
 */

public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<UserBean> listIApply = new ArrayList<>();
    private List<UserBean> listOtherApply = new ArrayList<>();
    private List<BaseFragment> list = new ArrayList<>();
    private FragmentViewPagerAdapter adapter;

    @Override
    public void init() {
        setContentView(R.layout.activity_msg_center);
        ButterKnife.bind(this);
        list.add(new ApplyFragment(0));
        list.add(new ApplyFragment(1));
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        getApplyList();

    }

    private void getApplyList() {
        AjaxParams params = new AjaxParams();
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.APPLY_LIST, new
                AjaxCallBack<ResultInfo<ApplyBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<ApplyBean> resultInfo) {
                        super.onSuccess(resultInfo);
                        if (resultInfo.status.equals("success")) {
                            if (resultInfo.data.one != null)
                                listIApply.addAll(resultInfo.data.one);
                            if (resultInfo.data.two != null)
                                listOtherApply.addAll(resultInfo.data.two);
                            list.get(0).setData(listOtherApply);
                            list.get(1).setData(listIApply);
                        } else {
                            Toast.makeText(MessageCenterActivity.this, resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(MessageCenterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
