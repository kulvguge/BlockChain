package block.com.blockchain.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.activity.MainActivity;
import block.com.blockchain.activity.MessageCenterActivity;
import block.com.blockchain.activity.PersonalActivity;
import block.com.blockchain.activity.SearchActivity;
import block.com.blockchain.bean.FriendData;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.callback.OnItemClickListener;
import block.com.blockchain.mainpage.LetterAdapter;
import block.com.blockchain.request.HttpConstant;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.DialogUtil;
import block.com.blockchain.utils.GroupUtils;
import block.com.blockchain.utils.SPUtils;
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
    private List<UserBean> list = new ArrayList<>();
    private int[] counts;
    private MySectionIndexer mIndexer;
    private String ALL_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String[] sections = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public LinearLayoutManager manager;
    private int postion = -1;
    private boolean isRequest = false;

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
                        Intent intent1 = new Intent();
                        intent1.setClass(getActivity(), SearchActivity.class);
                        intent1.putExtra("info", (Serializable) list);
                        startActivityForResult(intent1, MainActivity.SEARCH);
                        getActivity().overridePendingTransition(0, 0);
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

        adapter.setOnItemClickListener(new OnItemClickListener<UserBean>() {
            @Override
            public void onclik(UserBean personaBean) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("moblie", personaBean.getMobile());
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemClickListener<UserBean>() {
            @Override
            public void onclik(UserBean userBean) {
                showView("提示", "是否删除好友?", "确定", "取消", userBean);
            }
        });
    }

    @Override
    public void onRefresh() {
        AjaxParams params = new AjaxParams();
        String moblie = (String) SPUtils.getFromApp(HttpConstant.UserInfo.USER_PHONE, "");
        params.put("mobile", moblie);
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.FRIEND_LIST, new
                AjaxCallBack<ResultInfo<FriendData>>() {
                    @Override
                    public void onSuccess(ResultInfo<FriendData> s) {
                        super.onSuccess(s);

                        if (s.status.equals("success")) {
                            list.clear();
                            List<UserBean> listTemp = s.data.getData();
                            if (listTemp != null)
                                list.addAll(listTemp);
                            getFriendData();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取好友数据
     */
    private void getFriendData() {

        //获取首字母
        for (UserBean cityInfo : list) {
            if (cityInfo.getNickname() != null && cityInfo.getNickname().trim().length() > 0) {
                String firstLetter = GroupUtils.getInstance().getFirstLetter(cityInfo.getNickname());
                if (firstLetter == null)
                    cityInfo.nameTag = "}";
                cityInfo.nameTag = firstLetter;
            } else {
                cityInfo.nameTag = "}";
            }
            if (TextUtils.isEmpty(cityInfo.getNickname())) {
                cityInfo.nameTag = "{";
            }
        }
        //排序
        Collections.sort(list, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean lhs, UserBean rhs) {
                return lhs.nameTag.compareTo(rhs.nameTag);
            }
        });
        counts = new int[sections.length];
        for (UserBean item : list) { // 计算首字母。
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

    /**
     * 删除弹框
     */
    private void showView(String title, String count, String left, String right, final UserBean userBean) {

        DialogUtil.showPromptDialog(getActivity(), title, count, left, null, right,
                new DialogUtil.OnMenuClick() {

                    @Override
                    public void onRightMenuClick() {
                    }

                    @Override
                    public void onLeftMenuClick() {
                        delFriends(userBean);
                    }

                    @Override
                    public void onCenterMenuClick() {


                    }
                }, "");
    }

    /**
     * 删除好友
     */
    private void delFriends(final UserBean userBean) {
        if (isRequest)
            return;
        isRequest = true;
        AjaxParams params = new AjaxParams();
        params.put("mobile", userBean.getMobile());
        HttpSendClass.getInstance().postWithToken(params, SenUrlClass.FRIEND_DEL, new
                AjaxCallBack<ResultInfo<FriendData>>() {

                    @Override
                    public void onSuccess(ResultInfo<FriendData> s) {
                        super.onSuccess(s);
                        isRequest = false;
                        if (s.status.equals("success")) {
                            list.remove(userBean);
                            getFriendData();
                        } else {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        isRequest = false;
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
