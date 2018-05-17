package block.com.blockchain.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import block.com.blockchain.R;

@SuppressLint("AppCompatCustomView")
public class TimeButton extends TextView {
    private long length = 60 * 1000;// 倒计时长度，默认一分钟
    private String textAfter = "获取验证码(";
    private String textAfter1 = ")";
    private String textBefore = "获取验证码";
    private Timer timer;
    private TimerTask timerTask;
    private long time;
    private Context context;

    /**
     * 0:短信；1：语音
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(textAfter + time / 1000 + textAfter1);
            time -= 1000;
            if (time < 0) {

                TimeButton.this.setText(textBefore);
                TimeButton.this.setBackgroundResource(R.drawable.btn_common_round_stroke_blue);
                clearTimer();
            }
        }
    };

    private void initTimer() {
        time = length;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x01);
            }
        };
    }

    public void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setText(textBefore);
        this.setBackgroundResource(R.drawable.btn_common_round_stroke_blue);
    }


    public void begin() {
        initTimer();
        this.time = Math.abs(time);
        timer.schedule(timerTask, 0, 1000);
        this.setText(textAfter + time + textAfter1);

        this.setBackgroundResource(R.drawable.btn_common_round_stroke_blue);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (timerTask == null) {
                    begin();
                    break;
                } else {
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        TimeButton.this.setText(textBefore);
        TimeButton.this.setBackgroundResource(R.drawable.btn_common_round_stroke_blue);
        clearTimer();
    }
}
