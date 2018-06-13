package block.com.blockchain.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.TurnAdapter;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.ScoreData;
import block.com.blockchain.bean.TurnoveBean;
import block.com.blockchain.bean.TurnoveData;
import block.com.blockchain.customview.SpaceDecoration;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/17.
 */

public class ScoreActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.score_img)
    ImageView scoreImg;
    @BindView(R.id.score_nickname)
    TextView scoreNickname;
    @BindView(R.id.score_extro_money)
    TextView scoreExtroMoney;
    @BindView(R.id.score_order_count)
    TextView scoreOrderCount;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private List<TurnoveBean> list = new ArrayList<>();
    private TurnAdapter adapter;
    private String nickName, url;
    private boolean refreshing = false;
    private LinearLayoutManager manager;
    private int lastPostion = 0;
    private int pageNum = 1;

    @Override
    public void init() {
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        adapter = new TurnAdapter(this, list);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceDecoration(this));
        nickName = getIntent().getStringExtra("nickName");
        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(nickName))
            scoreNickname.setText(nickName);
        if (!TextUtils.isEmpty(url))

            Glide.with(this).load(HttpConstant.HTTPHOST+url).apply(new RequestOptions().placeholder(R.mipmap
                    .default_head))
                    .into(scoreImg);
        getData();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshing) {
                } else {
                    pageNum = 1;
                    getData();
                }

            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("ApplayFragment", newState + "");
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPostion + 1 == adapter.getItemCount()) {
                    if (lastPostion - manager.findFirstVisibleItemPosition() + 1 < adapter.getItemCount()) {
                        getData();
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPostion = manager.findLastVisibleItemPosition();
            }
        });
    }

    private void getData() {
        AjaxParams params = new AjaxParams();
        params.put("type", 0 + "");
        params.put("page", pageNum + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.TRADE_TURNOVER, new
                AjaxCallBack<ResultInfo<TurnoveData<ScoreData>>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        refreshing = true;
                    }

                    @Override
                    public void onSuccess(ResultInfo<TurnoveData<ScoreData>> s) {
                        super.onSuccess(s);
                        refreshing = false;
                        refresh.setRefreshing(refreshing);
                        if (s.status.equals("success")) {
                            if(pageNum==1)
                                list.clear();
                            pageNum++;
                            scoreExtroMoney.setText(s.data.integral + "");
                            scoreOrderCount.setText(s.data.count + "条");
                            List<TurnoveBean> listTemp = null;
                            if(s.data.data!=null)
                                listTemp=s.data.data.data;
                            if (listTemp != null)
                                list.addAll(listTemp);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ScoreActivity.this, s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        refreshing = false;
                        refresh.setRefreshing(refreshing);
                        Toast.makeText(ScoreActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
