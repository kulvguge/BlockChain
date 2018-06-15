package block.com.blockchain.activity;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.app.NetBroadCastReceiver;
import block.com.blockchain.fragment.BaseFragment;
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
    List<BaseFragment> list = new ArrayList();
    public static final int SEARCH = 1;
    public static final int USER_INFO = 2;
    private NetBroadCastReceiver netBroadCastReceiver = new NetBroadCastReceiver();
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
        noStatusBar();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netBroadCastReceiver, intentFilter);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomSheetViewHelper.disableShiftMode(navigation);
        BottomSheetViewHelper.setColor(this, navigation);
        manager = getSupportFragmentManager();
        list.add(new HomeFragment());
        list.add(new FriendFragment());
        list.add(new PersonFragment());
        list.add(new HelpFragment());
        adapter = new FragmentViewPagerAdapter(manager, list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(navigation.getMenu().getItem(position).getItemId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH && resultCode == RESULT_OK) {
            list.get(1).onRefresh();
        } else if (requestCode == USER_INFO && resultCode == RESULT_OK) {
            list.get(2).onRefresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.get(2).onRefresh();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netBroadCastReceiver);
    }
}
