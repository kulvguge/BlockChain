package block.com.blockchain.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import block.com.blockchain.R;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TS on 2018/5/12.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.first)
    TextView textView;
    @Override
    public int getResView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onRefresh() {

    }
    @OnClick(R.id.first)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.first:
                Toast.makeText(getActivity(), "FIRST", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
