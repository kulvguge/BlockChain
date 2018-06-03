package block.com.blockchain.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import block.com.blockchain.customview.CustomProgressDialog;
import block.com.blockchain.request.HttpConstant;

/**
 * Created by ts on 2018/5/12.
 */

public class BaseActivity extends FragmentActivity {
    CustomProgressDialog progressDialog;
    public static int heightPx = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

    }

    public void startProgressDialog() {
        // new Handler(Looper.getMainLooper()).post(new Runnable() {
        // @Override
        // public void run() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(BaseActivity.this);
            progressDialog.setMessage("加载中...");
        }
        try {
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            int divierId =progressDialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = progressDialog.findViewById(divierId);
            if (divider != null) {
                divider.setBackgroundColor(Color.TRANSPARENT);
            }
            progressDialog.show();

        } catch (Exception e) {
        }

    }

    public void stopProgressDialog() {
        // new Handler(Looper.getMainLooper()).post(new Runnable() {
        // @Override
        // public void run() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            progressDialog = null;
        }
        // }
        // });

    }

    public void noStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                HttpConstant.PhoneInfo.STATUS_HEIGHT = getResources().getDimensionPixelSize(resourceId);
            }
        }
    }
}
