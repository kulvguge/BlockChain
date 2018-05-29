package block.com.blockchain.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.MessageCenterAdapter;
import block.com.blockchain.bean.BaseBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.SpaceDecoration;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by ts on 2018/5/28.
 */

@SuppressLint("ValidFragment")
public class ApplyFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    Unbinder unbinder;
    MessageCenterAdapter adapter;
    private List<UserBean> list = new ArrayList<>();
    private int index;
    private boolean enable = true;

    @SuppressLint("ValidFragment")
    public ApplyFragment(int index) {
        this.index = index;
    }

    @Override
    public int getResView() {
        return R.layout.fragment_apply;
    }

    @Override
    public void onRefresh() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpaceDecoration(getActivity()));
        adapter = new MessageCenterAdapter(getActivity(), list);
        adapter.setType(index);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = -1;
                Object permit = view.getTag(R.id.tag_permit);
                if (permit == null) {
                    Object refuse = view.getTag(R.id.tag_refuse);
                    if (refuse != null) {
                        pos = (int) refuse;
                        refuse(list.get(pos));
                    }
                } else {
                    pos = (int) permit;
                    permit(list.get(pos));
                }

            }
        });
    }

    private void permit(final UserBean userBean) {
        enable = false;
        AjaxParams params = new AjaxParams();
        params.put("flog_id", userBean.getFlog_id() + "");
        HttpSendClass.getInstance().postWithToken(params, SenUrlClass.FRIEND_PERMIT, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        enable = true;
                        if (s.status.equals("success")) {
                            userBean.setStatus(2);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        enable = true;
                    }
                });
    }

    private void refuse(final UserBean userBean) {
        enable = false;
        AjaxParams params = new AjaxParams();
        params.put("flog_id", userBean.getFlog_id() + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.FRIEND_REFUSE, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        enable = true;
                        if (s.status.equals("success")) {
                            userBean.setStatus(3);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        enable = true;
                    }
                });
    }

    @Override
    public void setData(List<? extends BaseBean> list1) {
        list.addAll((Collection<? extends UserBean>) list1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
