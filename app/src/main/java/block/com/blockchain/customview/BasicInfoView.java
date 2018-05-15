package block.com.blockchain.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import block.com.blockchain.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/14.
 */

public class BasicInfoView extends LinearLayout {


    @BindView(R.id.left_msg)
    TextView leftMsg;
    @BindView(R.id.right_msg)
    TextView rightMsg;

    public BasicInfoView(Context context) {
        super(context);
    }

    public BasicInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BasicInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet set) {
        View view = LayoutInflater.from(context).inflate(R.layout.basic_view,null);
        ButterKnife.bind(this,view);
        TypedArray typedArray = context.obtainStyledAttributes(set,
                R.styleable.BasicInfoView);
        String ltext = typedArray.getString(R.styleable.BasicInfoView_ltext);
        String rtext = typedArray.getString(R.styleable.BasicInfoView_rtext);
        if (ltext != null)
            leftMsg.setText(ltext);
        if (rtext != null)
            rightMsg.setText(rtext);
        if (typedArray != null)
            typedArray.recycle();
        addView(view);
    }
}
