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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import block.com.blockchain.R;
import block.com.blockchain.bean.BlockBean;
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
    List<BlockBean> list = new ArrayList<>();

    @Override
    public int getResView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @OnClick(R.id.first)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first:
                Toast.makeText(getActivity(), "FIRST", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void getData() {
        for (int i = 0; i < 8; i++) {
            BlockBean blockBean = new BlockBean();
            blockBean.value = i * 10;
            list.add(blockBean);
        }
        Random random = new Random();
        //x轴上位置
        int baseX = 0;
        //x轴上位置
        int baseY = 0;
        int height = ScreenUtils.getScreenDispaly(getActivity())[1] - DensityUtil.dip2px(getActivity(), 50);
        int width = ScreenUtils.getScreenDispaly(getActivity())[0];
        //平均距离
        int averageArea = width / list.size();
        //波动范围
        int waveArea = 6;
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setTag(i);
            Drawable fish = getFish(i);
            textView.setGravity(Gravity.CENTER);
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, fish, null, null);
            textView.setText(list.get(i).value + "");
            RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            baseX = i * averageArea + averageArea / 2 + random.nextInt(waveArea);
            if(width-baseX<100){
                baseX=width-100;
            }
            baseY = height / 2 + random.nextInt(height / 4);
            marginLayoutParams.leftMargin = baseX;
            marginLayoutParams.topMargin = baseY;
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    view.setVisibility(View.INVISIBLE);
//                    constraintLayout.removeView(view);
//                    initPhotoDialog();
//                }
//            });
//            textView.setOnClickListener(this);
            cc.addView(textView, marginLayoutParams);
            cc(textView, i * 200 + 100);
        }
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
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setRepeatCount(1000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

}
