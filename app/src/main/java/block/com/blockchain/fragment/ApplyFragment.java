package block.com.blockchain.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.MessageCenterAdapter;
import block.com.blockchain.bean.BaseBean;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.SpaceDecoration;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/28.
 */

@SuppressLint("ValidFragment")
public class ApplyFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    Unbinder unbinder;
    MessageCenterAdapter adapter;
    private List<UserBean> list = new ArrayList<>();
    private int index;

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
        recyclerView.setAdapter(adapter);
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
