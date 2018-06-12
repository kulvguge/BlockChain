package block.com.blockchain.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
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
import block.com.blockchain.utils.JsonUtils;
import block.com.blockchain.utils.PhoneUtils;
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
    @BindView(R.id.parent_layout)
    LinearLayout parent_layout;

    LetterAdapter adapter;
    private List<UserBean> list = new ArrayList<>();
    private int[] counts;
    private MySectionIndexer mIndexer;
    private String ALL_CHARACTER = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String[] sections = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public LinearLayoutManager manager;
    private int postion = -1;
    private final int CONTACTS = 121;
    private boolean isRequest = false;
    private List<UserBean> listContacts;
    private List<UserBean> listRequest = new ArrayList<>();

    @Override
    public int getResView() {
        return R.layout.fragment_friend;
    }

    @Override
    public void init() {
        parent_layout.setPadding(0, HttpConstant.PhoneInfo.STATUS_HEIGHT, 0, 0);
        toolbar.inflateMenu(R.menu.friend_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Intent intent1 = new Intent();
                        intent1.setClass(getActivity(), SearchActivity.class);
                        intent1.putExtra("info", (Serializable) list);
                        getActivity().startActivityForResult(intent1, MainActivity.SEARCH);

                        break;
                    case R.id.action_msg_center:
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MessageCenterActivity.class);
                        getActivity().startActivity(intent);

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
        checkPermission();
    }

    public void requestFriendInfo() {
        AjaxParams params = new AjaxParams();
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.FRIEND_LIST, new
                AjaxCallBack<ResultInfo<FriendData>>() {
                    @Override
                    public void onSuccess(ResultInfo<FriendData> s) {
                        super.onSuccess(s);

                        if (s.status.equals("success")) {
                            list.clear();
                            List<UserBean> listTemp = s.data.getData();
                            if (listTemp != null)
                                listRequest.addAll(listTemp);
                            getContacts();
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
     * 去重
     *
     * @param lis1
     * @param lis2
     */
    private void removeHas(List<UserBean> lis1, List<UserBean> lis2) {
        for (UserBean userBean1 : lis1) {
            for (int i = 0; i < lis2.size(); i++) {
                UserBean userBean2 = lis2.get(i);
                if (userBean1.getNickname() != null && userBean1.getNickname().equals(userBean2.getNickname())) {
                    lis2.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * 获取好友数据
     */
    private void getFriendData() {

        //获取首字母
        for (UserBean cityInfo : list) {
            if (cityInfo.getNickname() != null && cityInfo.getNickname().trim().length() > 0) {
                String firstLetter = GroupUtils.getInstance().getFirstLetter(cityInfo.getNickname());
                if (firstLetter == null) {
                    cityInfo.nameTag = "#";
                } else {
                    cityInfo.nameTag = firstLetter;
                }
            } else {
                cityInfo.nameTag = "#";
            }
            if (TextUtils.isEmpty(cityInfo.getNickname())) {
                cityInfo.nameTag = "#";
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
                index = 0;
                item.nameTag = "#";
            }
            counts[index]++;
        }
        mIndexer = new MySectionIndexer(sections, counts);
        adapter.setData(mIndexer);
        adapter.notifyDataSetChanged();
        postion = manager.findFirstVisibleItemPosition();
        if (postion == -1) {
            postion = 0;
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 && PackageManager.PERMISSION_GRANTED != ContextCompat
                .checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)) {
            FriendFragment.this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS);
            return;
        } else {
            listContacts = PhoneUtils.getContactsInfo(getActivity());
            list.addAll(listContacts);
            getFriendData();
            requestFriendInfo();
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

    /**
     * 拉取通讯录接口
     */
    private void getContacts() {
        AjaxParams params = new AjaxParams();
        String phone = (String) SPUtils.getFromApp(HttpConstant.UserInfo.USER_PHONE, "");
        params.put("mobile", phone);
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.CONTACTS_DOWN, new
                AjaxCallBack<ResultInfo<List<UserBean>>>() {

                    @Override
                    public void onSuccess(ResultInfo<List<UserBean>> s) {
                        super.onSuccess(s);
                        isRequest = false;
                        if (s.status.equals("success")) {
                            List<UserBean> listTemp = s.data;
                            if (listTemp != null) {
                                listRequest.addAll(listTemp);
                                removeHas(list, listRequest);
                                list.addAll(listRequest);
                                getFriendData();
                            } else {
                                Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
                            }
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

    /**
     * 上传通讯录接口
     */

    private void uploadContacts() {
        if (listContacts == null || listContacts.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.has_upload), Toast.LENGTH_SHORT).show();
            return;
        }
        AjaxParams params = new AjaxParams();
        String phone = (String) SPUtils.getFromApp(HttpConstant.UserInfo.USER_PHONE, "");
        params.put("mobile", phone);
        params.put("mobile_list", JsonUtils.getJSONArrayByList(listContacts).toString());
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.CONTACTS_UP, new
                AjaxCallBack<ResultInfo<UserBean>>() {

                    @Override
                    public void onSuccess(ResultInfo<UserBean> s) {
                        super.onSuccess(s);
                        isRequest = false;
                        if (s.status.equals("success")) {
                            Toast.makeText(getActivity(), s.message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CONTACTS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            listContacts = PhoneUtils.getContactsInfo(getActivity());
            list.addAll(listContacts);
            adapter.notifyDataSetChanged();
            requestFriendInfo();
        } else if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            requestFriendInfo();
        }
    }
}
