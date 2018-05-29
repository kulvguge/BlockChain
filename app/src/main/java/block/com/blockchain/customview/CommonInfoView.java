package block.com.blockchain.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import block.com.blockchain.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/12.
 */

public class CommonInfoView extends LinearLayout {
    private String text = "";
    private @DrawableRes
    int rIcon = R.mipmap.ic_launcher;
    private @DrawableRes
    int lIcon = R.mipmap.ic_launcher;
    @BindView(R.id.left_icon)
    ImageView ivLIcon;
    @BindView(R.id.right_icon)
    ImageView ivRIcon;
    @BindView(R.id.center_text)
    TextView tvText;

    public CommonInfoView(Context context) {
        super(context);
    }

    public CommonInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CommonInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_info_view, null, false);
        ButterKnife.bind(this, view);
        TypedArray ta = context.obtainStyledAttributes(set, R.styleable.CommonInfoView);
        lIcon = ta.getResourceId(R.styleable.CommonInfoView_l_icon, R.mipmap.ic_launcher);
        rIcon = ta.getResourceId(R.styleable.CommonInfoView_r_icon, R.mipmap.ic_launcher);
        text = ta.getString(R.styleable.CommonInfoView_c_txt);
        ivLIcon.setBackgroundResource(lIcon);
        ivRIcon.setBackgroundResource(rIcon);
        tvText.setText(text);
        addView(view);
    }

    public void setCenterText(String text) {
        tvText.setText(text);
    }
}
