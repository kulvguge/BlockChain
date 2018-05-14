package block.com.blockchain.mainpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ts on 2018/5/12.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<Fragment> list;


    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentManager = fm;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
