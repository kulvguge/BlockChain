package block.com.blockchain.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.activity.MessageCenterActivity;
import block.com.blockchain.bean.PersonaBean;
import block.com.blockchain.mainpage.LetterAdapter;
import block.com.blockchain.utils.GroupUtils;
import block.com.blockchain.utils.pinneheader.BladeView;
import block.com.blockchain.utils.pinneheader.MySectionIndexer;
import butterknife.BindView;

/**
 * Created by TS on 2018/5/12.
 */

public class FriendFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_all)
    RecyclerView recyclerAll;
    @BindView(R.id.blade_view)
    BladeView bladeView;
    LetterAdapter adapter;
    private List<PersonaBean> list = new ArrayList<>();
    private int[] counts;
    private MySectionIndexer mIndexer;
    private String ALL_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String[] sections = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public LinearLayoutManager manager;
    private int postion = -1;

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
                switch (item.getItemId()) {
                    case R.id.action_search:
                        break;
                    case R.id.action_msg_center:
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MessageCenterActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAll.setLayoutManager(manager);
        PersonaBean bean = new PersonaBean();
        bean.nickName = "A";
        PersonaBean bean5 = new PersonaBean();
        bean5.nickName = "bbbbb";
        PersonaBean bean1 = new PersonaBean();
        bean1.nickName = "E";
        PersonaBean bean2 = new PersonaBean();
        bean2.nickName = "CC";
        PersonaBean bean3 = new PersonaBean();
        bean3.nickName = "E";
        PersonaBean bean4 = new PersonaBean();
        bean4.nickName = "G";
        PersonaBean bean6 = new PersonaBean();
        bean6.nickName = "CC";
        PersonaBean bean7 = new PersonaBean();
        bean7.nickName = "CC";
        PersonaBean bean8 = new PersonaBean();
        bean8.nickName = "中國";
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean3);
        list.add(bean3);
        list.add(bean3);
        list.add(bean4);
        list.add(bean4);
        list.add(bean4);
        list.add(bean4);
        list.add(bean4);
        list.add(bean4);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        adapter = new LetterAdapter(getActivity(), list);
        recyclerAll.setAdapter(adapter);
        bladeView.setOnItemClickListener(new BladeView.OnItemClickListener() {

            @Override
            public void onItemClick(String s) {
                if (s != null) {
                    if (mIndexer == null) {
                        return;
                    }
                    int section = ALL_CHARACTER.indexOf(s);
                    int position = mIndexer.getPositionForSection(section);
                    if (position != -1) {
                        Log.i("POS==", position + "");
                        recyclerAll.smoothScrollToPosition(position);
                    } else {

                    }
                }

            }
        });
        getFriendData();
    }

    @Override
    public void onRefresh() {

    }

    /**
     * 获取好友数据
     */
    private void getFriendData() {

        //获取首字母
        for (PersonaBean cityInfo : list) {
            if (cityInfo.nickName != null && cityInfo.nickName.trim().length() > 0) {
                String firstLetter = GroupUtils.getInstance().getFirstLetter(cityInfo.nickName);
                if (firstLetter == null)
                    cityInfo.nameTag = "}";
                cityInfo.nameTag = firstLetter;
            } else {
                cityInfo.nameTag = "}";
            }
            if (TextUtils.isEmpty(cityInfo.nickName)) {
                cityInfo.nameTag = "{";
            }
        }
        //排序
        Collections.sort(list, new Comparator<PersonaBean>() {
            @Override
            public int compare(PersonaBean lhs, PersonaBean rhs) {
                return lhs.nameTag.compareTo(rhs.nameTag);
            }
        });
        counts = new int[sections.length];
        for (PersonaBean item : list) { // 计算首字母。
            String firstCharacter = item.nameTag;
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            if (index == -1) {
                index = sections.length - 1;
                item.nameTag = sections[sections.length - 1];
            }
            counts[index]++;
        }
//        removeNull();
//        bladeView.setData(sections);
        mIndexer = new MySectionIndexer(sections, counts);
        adapter.setData(mIndexer);
        adapter.notifyDataSetChanged();
        postion = manager.findFirstVisibleItemPosition();
        if (postion == -1) {
            postion = 0;
        }
    }
}
