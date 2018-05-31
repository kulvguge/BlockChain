package block.com.blockchain.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import block.com.blockchain.bean.TurnoveBean;
import block.com.blockchain.bean.TurnoveData;
import block.com.blockchain.customview.SpaceDecoration;
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
    private List<TurnoveBean> list = new ArrayList<>();
    private TurnAdapter adapter;
    private String nickName, url;

    @Override
    public void init() {
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        adapter = new TurnAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceDecoration(this));
        nickName = getIntent().getStringExtra("nickName");
        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(nickName))
            scoreNickname.setText(nickName);
        if (!TextUtils.isEmpty(url))
            Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.default_head))
                    .into(scoreImg);
        getData();
    }

    private void getData() {
        AjaxParams params = new AjaxParams();
        params.put("type", 0 + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.TRADE_TURNOVER, new
                AjaxCallBack<ResultInfo<TurnoveData<TurnoveBean>>>() {
                    @Override
                    public void onSuccess(ResultInfo<TurnoveData<TurnoveBean>> s) {
                        super.onSuccess(s);
                        if (s.status.equals("success")) {
                            scoreExtroMoney.setText(s.data.integral + "");
                            scoreOrderCount.setText(s.data.count + "条");
                            list.clear();
                            List<TurnoveBean> listTemp = s.data.data;
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
                        Toast.makeText(ScoreActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
