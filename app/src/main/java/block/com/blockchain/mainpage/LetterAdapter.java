package block.com.blockchain.mainpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.bean.PersonaBean;
import block.com.blockchain.callback.OnItemClickListener;
import block.com.blockchain.utils.pinneheader.MySectionIndexer;

/**
 * Created by ts on 2017/12/27.
 */

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.MyHolder> {
    List<PersonaBean> list;
    Context context;
    private MySectionIndexer mIndexer;
    private OnItemClickListener<PersonaBean> onItemClickListener;
    private List<View> listView = new ArrayList<>();

    public LetterAdapter(Context context, List<PersonaBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_city, parent, false);
        listView.add(view);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final PersonaBean info = list.get(position);
        holder.tvName.setText(info.nickName);
        Log.i("letter_value=",info.nickName);
        Log.i("letter_value_tag=",info.nameTag);
        int section = 0;
        if (mIndexer != null) {
            section = mIndexer.getSectionForPosition(position);
        }
        if (mIndexer.getPositionForSection(section) == position) {
            holder.tvTag.setVisibility(View.VISIBLE);
            holder.tvTag.setText(info.nameTag);
        } else {
            holder.tvTag.setVisibility(View.GONE);
        }
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onclik(info);
                }
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

    public void setOnItemClickListener(OnItemClickListener<PersonaBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}