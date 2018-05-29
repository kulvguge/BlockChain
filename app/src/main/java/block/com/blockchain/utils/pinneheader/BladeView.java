package block.com.blockchain.utils.pinneheader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import block.com.blockchain.R;
import block.com.blockchain.utils.ScreenUtils;


public class BladeView extends View {
    private OnItemClickListener mOnItemClickListener;
    String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K",
            "L", "M", "N", "O", "PowerBean", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z"};
    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;
    private PopupWindow mPopupWindow;
    private TextView mPopupText;
    private Handler handler = new Handler();
    private Context context;

    public BladeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public BladeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BladeView(Context context) {
        super(context);
        this.context = context;
    }

    public void setData(String[] bb) {

        String[] aa = new String[1];
        aa[0] = "*";
        // 合并两个数组
        String[] dd = new String[aa.length + bb.length];
        System.arraycopy(aa, 0, dd, 0, aa.length);
        System.arraycopy(bb, 0, dd, aa.length, bb.length);
        b = dd;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#00FFFFFF"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;


        for (int i = 0; i < b.length; i++) {

            paint.setColor(Color.parseColor("#ff2f2f2f"));
            // paint.setTypeface(Typeface.DEFAULT_BOLD); //加粗
            paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.bladeview_fontsize));// 设置字体的大小
            paint.setFakeBoldText(true);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
            }

//            if (i == 0) {
//                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location_navigation);
//                float xPos = width / 2 - bmp.getWidth() / 2;
//                float yPos = 0;
//                canvas.drawBitmap(bmp, xPos, yPos, paint);
//            } else {
                float xPos = width / 2 - paint.measureText(b[i]) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(b[i], xPos, yPos, paint);
      //      }

            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) { // 让第一个字母响应点击事件

                        performItemClicked(c);
                        choose = c;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) { // 让.第一个字母响应点击事件
                        if (c == 0) {
                        } else {
                            performItemClicked(c);
                            choose = c;
                            invalidate();
                        }

                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                dismissPopup();
                invalidate();
                break;
        }
        return true;
    }

    int height = 80;

    private void showPopup(int item) {
        if (mPopupWindow == null) {
            handler.removeCallbacks(dismissRunnable);

            mPopupText = new TextView(getContext());
            mPopupText.setBackgroundColor(Color.GRAY);
            mPopupText.setTextColor(Color.WHITE);
            mPopupText.setTextSize(getResources().getDimensionPixelSize(R.dimen.bladeview_popup_fontsize));
            mPopupText.setGravity(Gravity.CENTER);
            height = getResources().getDimensionPixelSize(R.dimen.bladeview_popup_height);
            mPopupWindow = new PopupWindow(mPopupText, height, height);

        }

        String text = b[item];
//        if (item == 0) {
//            text = "#";
//        } else {
//            text = Character.toString((char) ('A' + item - 1));
//        }
        mPopupText.setText(text);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.update();
        } else {
            if (Build.VERSION.SDK_INT < 24) {
                mPopupWindow.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
            } else {
                mPopupWindow.showAtLocation(getRootView(), Gravity.NO_GRAVITY, ScreenUtils.getScreenDispaly(context)
                                [0] /
                                2 - height / 2,
                        ScreenUtils.getScreenDispaly(context)[1] / 2 - height / 2);
            }
        }
    }

    private void dismissPopup() {
        handler.postDelayed(dismissRunnable, 800);
    }

    Runnable dismissRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
        }
    };

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void performItemClicked(int item) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(b[item]);
            if (item != 0) {
                showPopup(item);
            }


        }
    }

    public interface OnItemClickListener {
        void onItemClick(String s);
    }

}
