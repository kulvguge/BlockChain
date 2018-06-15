package block.com.blockchain.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.MessageCenterAdapter;
import block.com.blockchain.bean.ApplyBean;
import block.com.blockchain.bean.BaseBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.customview.SpaceDecoration;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by ts on 2018/5/28.
 */

@SuppressLint("ValidFragment")
public class ApplyFragment extends BaseFragment {
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    Unbinder unbinder;
    MessageCenterAdapter adapter;
    private List<UserBean> list = new ArrayList<>();
    private int index;
    private boolean refreshing = false;

    private int lastPostion=0;
    private int pageNum=1;
    private boolean enable = false;
    private LinearLayoutManager manager;

    
    public ApplyFragment(int index) {
        this.index = index;
    }

    @Override
    public int getResView() {
        return R.layout.fragment_apply;
    }


    @Override
    public void onRefresh() {
      manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpaceDecoration(getActivity()));
        adapter = new MessageCenterAdapter(getActivity(), list);
        adapter.setType(index);
        recyclerView.setAdapter(adapter);
        if(index==0){
            getOtherList();
        }else if(index==1){
            getApplyList();
        }

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(refreshing){
                }else{
                    pageNum=1;
                    if(index==0){
                        getOtherList();
                    }else if(index==1){
                        getApplyList();
                    }
                }

            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("ApplayFragment",newState+"");
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastPostion + 1 ==adapter.getItemCount()) {
                        if(lastPostion-manager.findFirstVisibleItemPosition()+1<adapter.getItemCount()){
                            if(index==0){
                                getOtherList();
                            }else if(index==1){
                                getApplyList();
                            }
                        }

                }
                }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPostion=manager.findLastVisibleItemPosition();
            }
        });
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = -1;
                Object permit = view.getTag(R.id.tag_permit);
                if(!enable)
                    return;
                if (permit == null) {
                    Object refuse = view.getTag(R.id.tag_refuse);
                    if (refuse != null) {
                        pos = (int) refuse;
                        refuse(list.get(pos));
                    }
                } else {
                    pos = (int) permit;
                    permit(list.get(pos));
                }

            }
        });
    }

    @Override
    public void onNetCanUse() {

    }

    /**
     * 获取我申請的数据
     */
    private void getApplyList() {
        AjaxParams params = new AjaxParams();
        params.put("page",pageNum+"");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.APPLY_I, new
                AjaxCallBack<ResultInfo<ApplyBean>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        refreshing=true;
                    }

                    @Override
                    public void onSuccess(ResultInfo<ApplyBean> resultInfo) {
                        super.onSuccess(resultInfo);
                           refreshing=false;
                        refresh.setRefreshing(refreshing);
                        if (resultInfo.status.equals("success")) {
                            if(pageNum==1)
                                list.clear();
                            pageNum++;
                            if(resultInfo.data!=null&&resultInfo.data.data!=null)
                                list.addAll(resultInfo.data.data);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                           refreshing=false;
                        refresh.setRefreshing(refreshing);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    /**
     * 获取我申請的数据
     */
    private void getOtherList() {
        AjaxParams params = new AjaxParams();
        params.put("page",pageNum+"");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.APPLY_OTHER, new
                AjaxCallBack<ResultInfo<ApplyBean>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        refreshing=true;
                    }

                    @Override
                    public void onSuccess(ResultInfo<ApplyBean> resultInfo) {
                        super.onSuccess(resultInfo);
                           refreshing=false;
                           refresh.setRefreshing(refreshing);
                        if (resultInfo.status.equals("success")) {
                            if(pageNum==1)
                                list.clear();
                            pageNum++;
                            if(resultInfo.data!=null&&resultInfo.data.data!=null)
                                list.addAll(resultInfo.data.data);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), resultInfo.message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                           refreshing=false;
                        refresh.setRefreshing(refreshing);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    /**
     * 同意申请
     * @param userBean
     */
    private void permit(final UserBean userBean) {
        enable = false;
        AjaxParams params = new AjaxParams();
        params.put("flog_id", userBean.getFlog_id() + "");
        HttpSendClass.getInstance().postWithToken(params, SenUrlClass.FRIEND_PERMIT, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        enable = true;
                        if (s.status.equals("success")) {
                            userBean.setStatus(2);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        enable = true;
                    }
                });
    }

    private void refuse(final UserBean userBean) {
        enable = false;
        AjaxParams params = new AjaxParams();
        params.put("flog_id", userBean.getFlog_id() + "");
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.FRIEND_REFUSE, new
                AjaxCallBack<ResultInfo<UserBean>>() {
                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        enable = true;
                        if (s.status.equals("success")) {
                            userBean.setStatus(3);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        enable = true;
                    }
                });
    }

    @Override
    public void setData(List<? extends BaseBean> list1) {
        list.addAll((Collection<? extends UserBean>) list1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
