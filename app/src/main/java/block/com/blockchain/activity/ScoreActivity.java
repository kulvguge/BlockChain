package block.com.blockchain.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import block.com.blockchain.R;
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
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @Override
    public void init() {
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
    }
}
