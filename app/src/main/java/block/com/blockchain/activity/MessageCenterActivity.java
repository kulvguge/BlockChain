package block.com.blockchain.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.MessageCenterAdapter;
import block.com.blockchain.bean.MessageBean;
import block.com.blockchain.customview.SpaceDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/16.
 */

public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    MessageCenterAdapter adapter;
    private List<MessageBean> list = new ArrayList<>();

    @Override
    public void init() {
        setContentView(R.layout.activity_msg_center);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpaceDecoration(this));
        getData();
        adapter = new MessageCenterAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    private void getData() {
        list.add(new MessageBean("1"));
        list.add(new MessageBean("2"));
        list.add(new MessageBean("3"));

    }
}
