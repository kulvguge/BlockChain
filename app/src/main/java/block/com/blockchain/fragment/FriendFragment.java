package block.com.blockchain.fragment;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import block.com.blockchain.R;
import butterknife.BindView;

/**
 * Created by TS on 2018/5/12.
 */

public class FriendFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public int getResView() {
        return R.layout.fragment_friend;
    }

    @Override
    public void init() {
        toolbar.inflateMenu(R.menu.friend_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {

    }

}
