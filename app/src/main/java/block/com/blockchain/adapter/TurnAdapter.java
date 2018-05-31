package block.com.blockchain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.bean.TurnoveBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/31.
 */

public class TurnAdapter extends RecyclerView.Adapter<TurnAdapter.MyHolder> {

    private List<TurnoveBean> list;
    private Context context;

    public TurnAdapter(Context context, List<TurnoveBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_score, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        TurnoveBean turnoveBean = list.get(position);
        holder.orderNum.setText(turnoveBean.getOrder_number());
        holder.orderTime.setText(turnoveBean.getCreate_time());
        holder.orderMoney.setText(turnoveBean.getNum() + "");
        holder.orderIntro.setText(turnoveBean.getRemark());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.order_time)
        TextView orderTime;
        @BindView(R.id.order_money)
        TextView orderMoney;
        @BindView(R.id.order_intro)
        TextView orderIntro;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
