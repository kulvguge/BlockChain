package block.com.blockchain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.callback.OnItemClickListener;
import block.com.blockchain.utils.pinneheader.MySectionIndexer;

/**
 * Created by ts on 2018/5/31.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {
    List<UserBean> list;
    Context context;
    private MySectionIndexer mIndexer;
    private OnItemClickListener<UserBean> onItemClickListener, onItemLongClickListener;
    private List<View> listView = new ArrayList<>();

    public SearchAdapter(Context context, List<UserBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_city, parent, false);
        listView.add(view);
        return new SearchAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyHolder holder, final int position) {
        final UserBean info = list.get(position);
        holder.tvName.setText(info.getNickname());
        holder.tvTag.setVisibility(View.GONE);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onclik(info);
                }
            }
        });
        holder.tvName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onclik(info);
                }
                return false;
            }
        });

    }

    public View getView(int pos) {
        return listView.get(pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(MySectionIndexer mIndexer) {
        this.mIndexer = mIndexer;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvTag, tvName;

        public MyHolder(View itemView) {
            super(itemView);
            tvTag = (TextView) itemView.findViewById(R.id.city_tag);
            tvName = (TextView) itemView.findViewById(R.id.city_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<UserBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemClickListener<UserBean> onItemClickListener) {
        this.onItemLongClickListener = onItemClickListener;
    }
}
