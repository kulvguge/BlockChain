package block.com.blockchain.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import block.com.blockchain.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ts on 2018/5/28.
 */

public class BasicEditInfoView extends LinearLayout {
    @BindView(R.id.left_msg)
    TextView leftMsg;
    @BindView(R.id.right_msg)
    EditText rightMsg;

    public BasicEditInfoView(Context context) {
        super(context);
    }

    public BasicEditInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BasicEditInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet set) {
        View view = LayoutInflater.from(context).inflate(R.layout.basic_edit_view, null);
        ButterKnife.bind(this, view);
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

    public void setRightMsg(String msg) {
        rightMsg.setText(msg);
    }

    public void setRightMsg(@StringRes int msg) {
        rightMsg.setText(msg);
    }
}
