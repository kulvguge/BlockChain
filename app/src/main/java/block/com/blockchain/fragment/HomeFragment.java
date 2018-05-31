package block.com.blockchain.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import block.com.blockchain.R;
import block.com.blockchain.bean.PowerBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.request.HttpSendClass;
import block.com.blockchain.request.SenUrlClass;
import block.com.blockchain.utils.DensityUtil;
import block.com.blockchain.utils.ScreenUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TS on 2018/5/12.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.first)
    ImageView iv;
    @BindView(R.id.parent)
    RelativeLayout cc;
    List<PowerBean> list = new ArrayList<>();
    List<PowerBean> listTemp = new ArrayList<>();
    private int maxSize = 8;

    @Override
    public int getResView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onRefresh() {
        getPowerList();
    }

    @OnClick(R.id.first)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first:
                break;
        }
    }

    public void getData() {
        if (list.size() == 0) {
            return;
        }
        Random random = new Random();
        //x轴上位置
        int baseX = 0;
        //x轴上位置
        int baseY = 0;
        int height = ScreenUtils.getScreenDispaly(getActivity())[1] - DensityUtil.dip2px(getActivity(), 50);
        int width = ScreenUtils.getScreenDispaly(getActivity())[0];
        //平均距离
        int size = 0;
        if (list.size() > maxSize) {
            size = maxSize;
            listTemp.clear();
            for (int i = 0; i < maxSize; i++) {
                listTemp.add(list.get(i));
            }
            list.removeAll(listTemp);
        } else {
            size = list.size();
            listTemp.clear();
            listTemp.addAll(list);
            list.clear();
        }

        int averageArea = width / size;
        //波动范围
        int waveArea = 6;
        for (int i = 0; i < listTemp.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setTag(listTemp.get(i));
            Drawable fish = getFish(i);
            textView.setGravity(Gravity.CENTER);
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, fish, null, null);
            textView.setText(listTemp.get(i).count + "");
            RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            baseX = i * averageArea + averageArea / 2 + random.nextInt(waveArea);
            if (width - baseX < 200) {
                baseX = width - 200;
            }
            baseY = height / 2 + random.nextInt(height / 4);
            marginLayoutParams.leftMargin = baseX;
            marginLayoutParams.topMargin = baseY;
            cc.addView(textView, marginLayoutParams);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PowerBean powerBean = (PowerBean) view.getTag();
                    collection(powerBean, view);
                }
            });
            cc(textView, i);
        }
    }

    /**
     * 获取能量列表
     */
    private void getPowerList() {
        AjaxParams params = new AjaxParams();
        HttpSendClass.getInstance().getWithToken(params, SenUrlClass.POWER_LIST, new
                AjaxCallBack<ResultInfo<List<PowerBean>>>() {
                    @Override
                    public void onSuccess(ResultInfo<List<PowerBean>> s) {
                        super.onSuccess(s);
                        if (s.status.equals("success")) {
                            List<PowerBean> powerBeanList = s.data;
                            if (powerBeanList != null) {
                                list.addAll(powerBeanList);
                                getData();
                            }

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
     * 搜集能量
     */
    private void collection(final PowerBean bean, final View textView) {
        AjaxParams params = new AjaxParams();
        params.put("sort", bean.sort + "");
        params.put("count", bean.count + "");
        HttpSendClass.getInstance().postWithToken(params, SenUrlClass.POWER_COLLECTION, new
                AjaxCallBack<ResultInfo<List<PowerBean>>>() {
                    @Override
                    public void onSuccess(ResultInfo<List<PowerBean>> s) {
                        super.onSuccess(s);
                        if (s.status.equals("success")) {
                            listTemp.remove(bean);
                            cc.removeView(textView);
                            if (listTemp.size() == 0) {
                                getData();
                            }
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
     * 生成魚的規則
     */
    private Drawable getFish(int i) {
        switch (i % 3) {
            case 0:
                return getActivity().getResources().getDrawable(R.mipmap.fish_1);
            case 2:
                return getActivity().getResources().getDrawable(R.mipmap.fish_2);

            case 1:
                return getActivity().getResources().getDrawable(R.mipmap.fish_3);
            default:
        }
        return getActivity().getResources().getDrawable(R.mipmap.fish_1);
    }

    private void cc(View view, int i) {
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", 0f, -40);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolder);
        objectAnimator.setDuration(1000 + i * 100);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

}
