package block.com.blockchain.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.activity.PersonalActivity;
import block.com.blockchain.bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/16.
 */

public class MessageCenterAdapter extends RecyclerView.Adapter<MessageCenterAdapter.MyHolder> {

    private List<UserBean> list;
    private Context context;
    private View.OnClickListener onClickListener;
    private int type = 0;//0:别人申请1:我申请
    private RequestOptions options;

    public MessageCenterAdapter(Context context, List<UserBean> list) {
        this.list = list;
        this.context = context;
        options = new RequestOptions();
        options.placeholder(R.mipmap.default_head);
        options.error(R.mipmap.default_head);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final UserBean bean = list.get(position);
        holder.msgName.setText(bean.getNickname());
        if (bean.getSex() == 1) {
            holder.msgSex.setImageResource(R.mipmap.man);
        } else if (bean.getSex() == 2) {
            holder.msgSex.setImageResource(R.mipmap.woman);
        }
        holder.msgDate.setText(bean.getUpdate_time());
        Glide.with(context).load(bean.getPic_url()).apply(options).into(holder.msgIcon);
        if (type == 0) {
            if (bean.getStatus() == 1) {
                holder.layoutWaitPermit.setVisibility(View.VISIBLE);
                holder.layoutHasPermit.setVisibility(View.GONE);
            } else if (bean.getStatus() == 2) {
                holder.layoutHasPermit.setVisibility(View.VISIBLE);
                holder.layoutWaitPermit.setVisibility(View.GONE);
                holder.msgHasPermit.setText(R.string.msg_has_permit);
            } else {
                holder.layoutHasPermit.setVisibility(View.VISIBLE);
                holder.layoutWaitPermit.setVisibility(View.GONE);
                holder.msgHasPermit.setText(R.string.msg_has_refuse);
            }
            holder.msgWaitPermit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //同意
                    if (onClickListener != null) {
                        view.setTag(R.id.tag_permit, position);
                        onClickListener.onClick(view);
                    }

                }
            });
            holder.msg_wait_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //拒绝
                    if (onClickListener != null) {
                        view.setTag(R.id.tag_refuse, position);
                        onClickListener.onClick(view);
                    }

                }
            });
            holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, PersonalActivity.class);
                    if (Build.VERSION.SDK_INT >= 21) {
                        Pair pair = new Pair<View, String>(holder.msgIcon, "btn2");
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                pair);
                        context.startActivity(intent, options.toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            holder.layoutHasPermit.setVisibility(View.VISIBLE);
            holder.layoutWaitPermit.setVisibility(View.GONE);
            if (bean.getStatus() == 1) {
                holder.msgHasPermit.setText(R.string.msg_wait_resolve);
            } else if (bean.getStatus() == 2) {
                holder.msgHasPermit.setText(R.string.msg_has_permit);
            } else {
                holder.msgHasPermit.setText(R.string.msg_has_refuse);
            }
        }


    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
        @BindView(R.id.layout_wait_permit)
        LinearLayout layoutWaitPermit;
        @BindView(R.id.msg_wait_refuse)
        TextView msg_wait_refuse;
        @BindView(R.id.parent_layout)
        LinearLayout parent_layout;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
