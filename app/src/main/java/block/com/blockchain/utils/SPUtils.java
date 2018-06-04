package block.com.blockchain.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import block.com.blockchain.app.MyApp;
/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float,
 * Long类型的参数 同样调用getParam就能获取到保存在手机里面的数据
 *
 * @author xiaanming
 *
 */
public class SPUtils {
	/**
	 * 保存在手机里面的文件名
	 */
	private static final String APP_COMMON_FILE_NAME = "block";
	private static final String APP_COMMON_FILE_NAME_IM = "blockim";
	private static final String ACCOUNT_FILE_NAME = "AccountCommon";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 *
	 * * @param key
	 * @param object
	 */
	public static boolean saveToApp(String key, Object object) {
		SharedPreferences sp = MyApp.mContext.getSharedPreferences(APP_COMMON_FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		// SharedPreferencesCompat.apply(editor);
		return editor.commit();
	}

	public static boolean saveToAppChat(String key, Object object) {
		SharedPreferences sp = MyApp.mContext.getSharedPreferences(APP_COMMON_FILE_NAME_IM,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		// SharedPreferencesCompat.apply(editor);
		return editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 *
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object getFromApp(String key, Object defaultObject) {
		SharedPreferences sp = MyApp.mContext.getSharedPreferences(APP_COMMON_FILE_NAME,
				Context.MODE_PRIVATE);
		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}
		return null;
	}

	public static Object getFromChatApp(String key, Object defaultObject) {
		SharedPreferences sp = MyApp.mContext.getSharedPreferences(APP_COMMON_FILE_NAME_IM,
				Context.MODE_PRIVATE);
		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}
		return null;
	}






	/**
	 * 移除某个key值已经对应的值
	 *
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		// on a null object reference
		try {
			SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.remove(key);
			editor.commit();
		} catch (Exception e) {
		}
	}

	public static void removeAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();

		SharedPreferences spAccount = context.getSharedPreferences(ACCOUNT_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editorAccount = spAccount.edit();
		editorAccount.clear();
		editorAccount.commit();
	}

	/**
	 * 清除所有数据
	 *
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME, Context.MODE_PRIVATE);
		// sp.edit().clear().commit();
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 清除所有数据
	 *
	 * @param context
	 */
	public static void clearChart(Context context) {
		SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME_IM, Context.MODE_PRIVATE);
		// sp.edit().clear().commit();
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME_IM, Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 *
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(APP_COMMON_FILE_NAME, Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 *
	 * @author zhy
	 *
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 *
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 *
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}
	// 防止控件重复点击
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
