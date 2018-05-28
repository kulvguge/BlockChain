package block.com.blockchain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/16.
 */

public class MessageCenterAdapter extends RecyclerView.Adapter<MessageCenterAdapter.MyHolder> {

    private List<UserBean> list;
    private Context context;

    public MessageCenterAdapter(Context context, List<UserBean> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        UserBean bean = list.get(position);
        holder.msgName.setText(bean.getNickname());
        if (bean.getSex() == 1) {
            holder.msgSex.setImageResource(R.mipmap.man);
        } else if (bean.getSex() == 2) {
            holder.msgSex.setImageResource(R.mipmap.woman);
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.msg_icon)
        ImageView msgIcon;
        @BindView(R.id.msg_name)
        TextView msgName;
        @BindView(R.id.msg_sex)
        ImageView msgSex;
        @BindView(R.id.msg_wait_permit)
        TextView msgWaitPermit;
        @BindView(R.id.msg_has_permit)
        TextView msgHasPermit;
        @BindView(R.id.msg_date)
        TextView msgDate;
        @BindView(R.id.layout_has_permit)
        LinearLayout layoutHasPermit;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
