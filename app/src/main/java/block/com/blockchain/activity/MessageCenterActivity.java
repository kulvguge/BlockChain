package block.com.blockchain.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.MessageCenterAdapter;
import block.com.blockchain.bean.MessageBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.TokenBean;
import block.com.blockchain.customview.SpaceDecoration;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.NetWork;
import block.com.blockchain.utils.SPUtils;
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
        NetWork.ApiSubscribe(NetWork.getRequestApi().getApplyFriend(), observer);
    }
    Subscriber<ResultInfo<MessageBean>> observer = new Subscriber<ResultInfo<MessageBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            s.request(1);
        }

        @Override
        public void onNext(ResultInfo<MessageBean> resultInfo) {
            if (resultInfo.status.equals("success")) {
                Toast.makeText(MessageCenterActivity.this, "拉取成功", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MessageCenterActivity.this,resultInfo.message, Toast
                        .LENGTH_SHORT)
                        .show();
            }

        }

        @Override
        public void onError(Throwable t) {
            Toast.makeText(MessageCenterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };

}
