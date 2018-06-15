package block.com.blockchain.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class IsNetwork {
	private  static String TAG="IsNetWork";
	/**
	 * 没有连接网络
	 */
	public static final int NETWORK_NONE = -1;
	/**
	 * 移动网络
	 */
	public static final int NETWORK_MOBILE = 0;
	/**
	 * 无线网络
	 */
	public static final int NETWORK_WIFI = 1;

	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	// /** 检查是否有网络 */
	// public static boolean isNetworkAvailable(Context context) {
	// NetworkInfo info = getNetworkInfo(context);
	// if (info != null) {
	// return info.isAvailable();
	// }
	// return false;
	// }

	/** 检查是否是WIFI */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)
				return true;
		}
		return false;
	}

	/** 检查是否是移动网络 */
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}
		return false;
	}

	public static int getNetWorkState(Context context) {
		// 得到连接管理器对象
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

			if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
				return NETWORK_WIFI;
			} else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
				return NETWORK_MOBILE;
			}
		} else {
			return NETWORK_NONE;
		}
		return NETWORK_NONE;
	}
	private static NetworkInfo getNetworkInfo(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
//	public static void netStatusShow(String string, Context context){
//
//		// 获取WindowManager
//		WindowManager mWindowManager = (WindowManager) mContext
//				.getSystemService(Context.WINDOW_SERVICE);
//
//		View view= LayoutInflater.from(context).inflate(R.layout.pop_net_state,null,false);
//		final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//
//		// 类型
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			params.type = WindowManager.LayoutParams.TYPE_TOAST;
//		} else {
//			params.type = WindowManager.LayoutParams.TYPE_PHONE;
//		}
//		// WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//
//		// 设置flag
//
//		int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
//		// | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//		// 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
//		params.flags = flags;
//
//		params.format = PixelFormat.TRANSPARENT;
//		params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//		params.height =  FrameLayout.LayoutParams.WRAP_CONTENT;
//
//		params.gravity= Gravity.TOP;
//		mWindowManager.addView(view,params);
//		netAnimation(mWindowManager,view,context);
//	}
	private static void netAnimation(final WindowManager manager, final View view, final Context context){
		Handler h=new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
			  manager.removeView(view);
			}
		},3000);
	}

}
