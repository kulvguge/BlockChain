package block.com.blockchain.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.fragment.FriendFragment;
import block.com.blockchain.fragment.HelpFragment;
import block.com.blockchain.fragment.HomeFragment;
import block.com.blockchain.fragment.PersonFragment;
import block.com.blockchain.mainpage.FragmentViewPagerAdapter;
import block.com.blockchain.utils.BottomSheetViewHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    FragmentViewPagerAdapter adapter;
    FragmentManager manager;
    List<Fragment> list=new ArrayList();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_friend:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_info:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_help:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomSheetViewHelper.disableShiftMode(navigation);
        BottomSheetViewHelper.setColor(this,navigation);
        manager=getSupportFragmentManager();
        list.add(new HomeFragment());
        list.add(new FriendFragment());
        list.add(new PersonFragment());
        list.add(new HelpFragment());
        adapter=new FragmentViewPagerAdapter(manager,list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

    }
}
