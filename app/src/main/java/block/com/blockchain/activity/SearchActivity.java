package block.com.blockchain.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import block.com.blockchain.R;
import block.com.blockchain.adapter.SearchAdapter;
import block.com.blockchain.bean.FriendData;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import block.com.blockchain.callback.OnItemClickListener;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.DialogUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/31.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.no_data)
    TextView no_data;
    private boolean changed = false;
    private boolean isRequest = false;
    private List<UserBean> list;
    private List<UserBean> searchList = new ArrayList<>();
    SearchAdapter adapter;

    @Override
    public void init() {
        super.init();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        list = (List<UserBean>) getIntent().getSerializableExtra("info");
        adapter = new SearchAdapter(this, searchList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSearch.setLayoutManager(manager);
        recyclerSearch.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changed)
                    setResult(RESULT_OK);
                finish();
            }
        });
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if (TextUtils.isEmpty(edit)) {
                    cancel.setVisibility(View.GONE);
                } else {
                    cancel.setVisibility(View.VISIBLE);
                }
                searchList.clear();
                filterSearchData(edit);
                adapter.notifyDataSetChanged();
                if (searchList.size() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                    recyclerSearch.setVisibility(View.GONE);
                } else {
                    no_data.setVisibility(View.GONE);
                    recyclerSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<UserBean>() {
            @Override
            public void onclik(UserBean personaBean) {
                Intent intent = new Intent(SearchActivity.this, PersonalActivity.class);
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

    /**
     * @param获取符合的数据
     */
    private void filterSearchData(String s) {
        for (UserBean userBean : list) {
            if (userBean.getNickname() != null && userBean.getNickname().contains(s)) {
                searchList.add(userBean);
            }
        }
    }

    /**
     * 删除弹框
     */
    private void showView(String title, String count, String left, String right, final UserBean userBean) {

        DialogUtil.showPromptDialog(SearchActivity.this, title, count, left, null, right,
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
                            searchList.remove(userBean);
                            list.remove(userBean);
                            adapter.notifyDataSetChanged();
                            changed = true;
                        } else {
                            Toast.makeText(SearchActivity.this, s.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, String strMsg) {
                        super.onFailure(t, strMsg);
                        isRequest = false;
                        Toast.makeText(SearchActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick({R.id.cancel, R.id.recycler_search, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                edit_search.setText("");
                break;
            case R.id.recycler_search:
                break;
            case R.id.back:
                setResult(RESULT_OK);
                finish();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && changed) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);

    }
}
