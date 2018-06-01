package block.com.blockchain.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import block.com.blockchain.R;
import block.com.blockchain.utils.TimeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/1.
 */

public class DialogActivity extends BaseActivity {
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.ensure)
    TextView ensure;
    @BindView(R.id.datePicker)
    DatePicker datePicker;
    private String date = "";
    private String tempDate = "";
    private Calendar calendar;

    @Override
    public void init() {
        super.init();
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        setData();
    }

    private void setData() {
        setDatePickerDividerColor(datePicker);
         calendar = Calendar.getInstance();
        date = getIntent().getStringExtra("date");
        int year;
        int mouth;
        int day;
        if (!TextUtils.isEmpty(date)) {
            Date dater = TimeUtils.formatTimeShort(date);
            if (dater != null)
                calendar.setTime(dater);
        }
        year = calendar.get(Calendar.YEAR);
        mouth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (mouth + 1 < 10) {
            date = year + "-0" + (mouth + 1);
        } else {
            date = year + "-" + (mouth + 1);
        }
        if (day < 10) {
            date = date + "-0" + day;
        } else {
            date = date + "-" + day;
        }
        datePicker.init(year, mouth, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (monthOfYear + 1 < 10) {
                    date = year + "-0" + (monthOfYear + 1);
                } else {
                    date = year + "-" + (monthOfYear + 1);
                }
                if (dayOfMonth < 10) {
                    date = date + "-0" + dayOfMonth;
                } else {
                    date = date + "-" + dayOfMonth;
                }
            }
        });
    }

    @OnClick({R.id.cancel, R.id.ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.ensure:
                Intent intent = new Intent();
                intent.putExtra("date", date);
                setResult(RESULT_OK, intent);
                break;
        }
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    private void setDatePickerDividerColor(DatePicker datePicker) {
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
            Field[] pickerFields = picker.getClass().getDeclaredFields();
            for (Field pf : pickerFields) {
//                Log.i("mDelegate_field:", pf.getName());
//                Log.i("mDelegate_field:", pf.getDeclaringClass().getSimpleName());
//                if (pf.getName().equals("mInputText")) {
//                    try {
//                        EditText editText = (EditText) pf.get(picker);
//                        editText.setTextColor(getResources().getColor(R.color.blue));
//                        pf.set(picker, editText);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(getResources().getColor(R.color.blue)));//设置分割线颜色
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
