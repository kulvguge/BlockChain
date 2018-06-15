package block.com.blockchain.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import block.com.blockchain.bean.BaseBean;
import block.com.blockchain.utils.IsNetwork;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ts on 2018/5/12.
 */

public abstract class BaseFragment extends Fragment {
    Unbinder binder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(getResView(), container, false);
        binder = ButterKnife.bind(this, view);
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.blockchain.net");
        getActivity().registerReceiver(receiver, intentFilter1);
        init();
        setHasOptionsMenu(true);
        onRefresh();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void init() {
    }

    public void setData(List<? extends BaseBean> list) {
    }

    public abstract @LayoutRes
    int getResView();

    /**
     * 刷新頁面
     */
    public abstract void onRefresh();
    public abstract void onNetCanUse();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("netWorkState", 1);
            if (status != IsNetwork.NETWORK_NONE) {
                onNetCanUse();
            } else {
                Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
        if (binder != null)
            binder.unbind();
    }
}
