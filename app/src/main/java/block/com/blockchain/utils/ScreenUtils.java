package block.com.blockchain.utils;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import block.com.blockchain.app.MyApp;

public class ScreenUtils {

	@SuppressWarnings("deprecation")
	public static int[] getScreenDispaly(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = wm.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	private static int getStatusBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = MyApp.mContext.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}
	/**
	 * @Title: viewPoints
	 * @Description: 要等UI控件都加载完了才能获取到对应的值，因此不能在onCreate、onStart、onResume等方法中调用
	 * @param views
	 * @return
	 * @return: String
	 */
	public static String viewPoints(View... views) {
		int size = views.length;
		JSONArray array = new JSONArray();
		for (int i = 0; i < size; i++) {
			array.put(viewPoint(views[i]));
		}
		return array.toString();
	}

	private static JSONObject viewPoint(View view) {
		JSONObject json = new JSONObject();
		int[] location = new int[2];
		view.getLocationInWindow(location);
		try {
			json.put("x", location[0] + view.getWidth() / 2);
			json.put("y", location[1] + view.getHeight() / 2 - getStatusBarHeight());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
