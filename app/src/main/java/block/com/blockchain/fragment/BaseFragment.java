package block.com.blockchain.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import block.com.blockchain.bean.BaseBean;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binder != null)
            binder.unbind();
    }
}
