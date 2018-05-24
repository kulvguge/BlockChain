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

/*	// 自己成长树跳转判断
	public static void getUserGrowingTreeData(final Activity activity) {

		String userId = SPDBService.getUserId(activity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touserId", userId);
		FinalHttp http = new FinalHttp();
		http.post(HttpConstant.HTTP_TREE_GETUSERGROWINGTREE, AjaxParams.getSignParams(map), new AjaxCallBack<String>() {
			CustomProgressDialog progressDialog = null;

			@Override
			public void onStart() {
				super.onStart();
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog.createDialog(activity);
					progressDialog.setMessage("加载中...");
				}
				// 2016-4-22 by hdj: 偶尔Activity为空的时候异常捕获。
				try {
					if (progressDialog.isShowing()) {
						progressDialog.cancel();
					}
					progressDialog.show();
				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
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
				ToastUtils.TextToast(activity, "网络超时请检查网络连接!", 1000 * 2);

				 * ToolUtlis.startActivityAnimFromTabHost(activity,
				 * UnopenTreeActivity.class);

			}

			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
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

				Type type = new TypeToken<GrowingTreeInfo>() {
				}.getType();
				// //System.out.println("数据是:"+s);
				GrowingTreeInfo index = new Gson().fromJson(s, type);
				if (index.result == 1) {
					GrowingTreeInfo.Tree tree = index.tree;
					SharedPreferences sharedPreferences = activity.getSharedPreferences("treegrow",
							Activity.MODE_PRIVATE);
					if ("1".equals(sharedPreferences.getString(SPDBService.getUserId(activity) + "isTreeGrow", ""))) {
						System.out.println("保存进入开启成长树");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userInfo", SPDBService.getUserInfo(activity));
						ToolUtlis.startActivityAnimGeneral(activity, GrowingTreeActivity.class, map);
					} else {
						// //System.out.println("未保存进入开启成长树+");
						if (tree.totalScore == null) {
							ToolUtlis.startActivityAnimFromTabHost(activity, UnopenTreeActivity.class);
						} else if (tree.totalScore >= 60 && tree.treeflag == 2) {
							final SharedPreferences sp = activity.getSharedPreferences("treegrow",
									activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sp.edit();
							editor.putString(SPDBService.getUserId(activity) + "isTreeGrow", "1");
							editor.commit();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("userInfo", SPDBService.getUserInfo(activity));
							ToolUtlis.startActivityAnimGeneral(activity, GrowingTreeActivity.class, map);
						} else if (tree.totalScore >= 60 && tree.treeflag == 1) {
							// 成长树已开启 新手引导未完成跳入新手引导页面
							ToolUtlis.startActivityAnimFromTabHost(activity, GrowingNewTreeActivity.class);
						}

						else {
							ToolUtlis.startActivityAnimFromTabHost(activity, UnopenTreeActivity.class);
						}

					}
				} else {
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
					// ToastUtils.TextToast(activity, index.failureReason + "-"
					// + index.failureMessage, 1000 * 2);
					ToastUtils.TextToast(activity, "获取成长树状态失败，请稍候尝试", 1000 * 2);
				}

			}

		});

	}

	// 自己成长树跳转判断 ，清除顶部
	public static void getUserGrowingTreeDatas(final Activity activity) {

		String userId = SPDBService.getUserId(activity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touserId", userId);
		FinalHttp http = new FinalHttp();
		http.post(HttpConstant.HTTP_TREE_GETUSERGROWINGTREE, AjaxParams.getSignParams(map), new AjaxCallBack<String>() {
			CustomProgressDialog progressDialog = null;

			@Override
			public void onStart() {
				super.onStart();
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog.createDialog(activity);
					progressDialog.setMessage("加载中...");
				}
				// 2016-4-22 by hdj: 偶尔Activity为空的时候异常捕获。
				try {
					if (progressDialog.isShowing()) {
						progressDialog.cancel();
					}
					progressDialog.show();
				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
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
				ToastUtils.TextToast(activity, "网络超时请检查网络连接!", 1000 * 2);

				 * ToolUtlis.startActivityAnimFromTabHost(activity,
				 * UnopenTreeActivity.class);

			}

			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
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

				Type type = new TypeToken<GrowingTreeInfo>() {
				}.getType();
				// //System.out.println("数据是:"+s);
				GrowingTreeInfo index = new Gson().fromJson(s, type);
				if (index.result == 1) {
					GrowingTreeInfo.Tree tree = index.tree;
					SharedPreferences sharedPreferences = activity.getSharedPreferences("treegrow",
							Activity.MODE_PRIVATE);
					if ("1".equals(sharedPreferences.getString(SPDBService.getUserId(activity) + "isTreeGrow", ""))) {
						System.out.println("保存进入开启成长树");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userInfo", SPDBService.getUserInfo(activity));
						ToolUtlis.startActivityOffAnimGeneral(activity, GrowingTreeActivity.class, map);
					} else {
						// //System.out.println("未保存进入开启成长树+");
						if (tree.totalScore == null) {
							ToolUtlis.startActivityAnim(activity, UnopenTreeActivity.class);
						} else if (tree.totalScore >= 60 && tree.treeflag == 2) {
							final SharedPreferences sp = activity.getSharedPreferences("treegrow",
									activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sp.edit();
							editor.putString(SPDBService.getUserId(activity) + "isTreeGrow", "1");
							editor.commit();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("userInfo", SPDBService.getUserInfo(activity));
							ToolUtlis.startActivityOffAnimGeneral(activity, GrowingTreeActivity.class, map);
						} else if (tree.totalScore >= 60 && tree.treeflag == 1) {
							// 成长树已开启 新手引导未完成跳入新手引导页面
							ToolUtlis.startActivityAnim(activity, GrowingNewTreeActivity.class);
						}

						else {
							ToolUtlis.startActivityAnim(activity, UnopenTreeActivity.class);
						}

					}
				} else {
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
					// ToastUtils.TextToast(activity, index.failureReason + "-"
					// + index.failureMessage, 1000 * 2);
					ToastUtils.TextToast(activity, "获取成长树状态失败，请稍候尝试", 1000 * 2);
				}

			}

		});

	}

	// 好友及陌生人的好友成长树跳转
	public static void getUnopenGrowingTreeData(final Activity activity, final UserInfo uInfo) {

		String userId = SPDBService.getUserId(activity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touserId", userId);
		FinalHttp http = new FinalHttp();
		http.post(HttpConstant.HTTP_TREE_GETUSERGROWINGTREE, AjaxParams.getSignParams(map), new AjaxCallBack<String>() {
			CustomProgressDialog progressDialog = null;

			@Override
			public void onStart() {
				super.onStart();
				if (progressDialog == null) {
					progressDialog = CustomProgressDialog.createDialog(activity);
					progressDialog.setMessage("加载中...");
				}
				// 2016-4-22 by hdj: 偶尔Activity为空的时候异常捕获。
				try {
					if (progressDialog.isShowing()) {
						progressDialog.cancel();
					}
					progressDialog.show();
				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
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
				ToastUtils.TextToast(activity, "网络超时请检查网络连接!", 1000 * 2);

				 * ToolUtlis.startActivityAnimFromTabHost(activity,
				 * UnopenTreeActivity.class);

			}

			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
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

				Type type = new TypeToken<GrowingTreeInfo>() {
				}.getType();
				// //System.out.println("数据是:"+s);
				GrowingTreeInfo index = new Gson().fromJson(s, type);
				if (index.result == 1) {
					GrowingTreeInfo.Tree tree = index.tree;
					if (tree.treeflag == 0 || tree.treeflag == 1) {
						// ToastUtils.TextToast(activity,
						// "自己未开启，进入提示自己未开启成长树页面", 2000);
						// 自己未开启成长树 跳转 提示自己未开启成长树页面
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userInfo", uInfo);
						ToolUtlis.startActivityAnimGeneral(activity, GrowingNotTreeActivity.class, map);
						// ToastUtils.TextToast(activity,
						// "自己未开启成长树，跳转提示自己未开启成长树好友页面", 1000 * 2);
					} else {
						// 自己已开启 判断跳转相互关注成长树或者陌生人成长树
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("touserId", uInfo.getUserId());
						FinalHttp http = new FinalHttp();
						http.post(HttpConstant.HTTP_TREE_GETUSERGROWINGTREE, AjaxParams.getSignParams(map),
								new AjaxCallBack<String>() {
									// CustomProgressDialog progressDialog =
									// null;

									@Override
									public void onStart() {
										super.onStart();

									}

									@Override
									public void onFailure(Throwable t, int errorNo, String strMsg) {
										super.onFailure(t, errorNo, strMsg);
										ToastUtils.TextToast(activity, "网络超时请检查网络连接!", 1000 * 2);
									}

									@Override
									public void onSuccess(String s) {
										super.onSuccess(s);

										Type type = new TypeToken<GrowingTreeInfo>() {
										}.getType();

										GrowingTreeInfo index = new Gson().fromJson(s, type);
										if (index.result == 1) {
											GrowingTreeInfo.Tree tree = index.tree;

											if ("7".equals(tree.relation) || "4".equals(tree.relation)
													|| "5".equals(tree.relation)) {

												// ToastUtils.TextToast(activity,
												// "自己开启，进入粉丝，陌生人", 2000);
												Map<String, Object> map = new HashMap<String, Object>();
												map.put("userInfo", uInfo);
												ToolUtlis.startActivityAnimGeneral(activity,
														GrowingStrangerTreeActivity.class, map);

											} else {

												// ToastUtils.TextToast(activity,
												// "自己开启，相互关注成长树页面", 2000);
												Map<String, Object> map = new HashMap<String, Object>();
												map.put("userInfo", uInfo);
												ToolUtlis.startActivityAnimGeneral(activity,
														GrowingFriendTreeActivity.class, map);
											}

										} else {
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
											ToastUtils.TextToast(activity, "获取成长树状态失败，请稍后尝试", 1000 * 2);
										}
									}
								});

					}

				} else {
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
					ToastUtils.TextToast(activity, "获取成长树状态失败，请稍后尝试", 1000 * 2);

				}
			}
		});

	}

	public static void getUserGrowingOffTreeData(final Activity activity) {
		String userId = SPDBService.getUserId(activity);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touserId", userId);
		FinalHttp http = new FinalHttp();
		http.post(HttpConstant.HTTP_TREE_GETUSERGROWINGTREE, AjaxParams.getSignParams(map), new AjaxCallBack<String>() {

			@Override
			public void onStart() {
				super.onStart();

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);

				// ToastUtils.TextToast(activity, "网络超时请检查网络连接!", 1000 * 2);
				ToolUtlis.startActivityAnimFromTabHost(activity, UnopenTreeActivity.class);
			}

			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);

				Type type = new TypeToken<GrowingTreeInfo>() {
				}.getType();
				// //System.out.println("数据是:"+s);
				GrowingTreeInfo index = new Gson().fromJson(s, type);
				if (index.result == 1) {
					GrowingTreeInfo.Tree tree = index.tree;
					if (tree.totalScore == null) {
						ToolUtlis.startActivityAnimFromTabHost(activity, UnopenTreeActivity.class);
					} else if (tree.totalScore >= 60 && tree.treeflag == 2) {
						System.out.println("进入清除上部activity跳转");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userInfo", SPDBService.getUserInfo(activity));
						ToolUtlis.startActivityOffAnimGeneral(activity, GrowingTreeActivity.class, map);
					} else if (tree.totalScore >= 60 && tree.treeflag == 1) {
						// 成长树已开启 新手引导未完成跳入新手引导页面
						ToolUtlis.startActivityAnimFromTabHost(activity, GrowingNewTreeActivity.class);
					}

					else {
						ToolUtlis.startActivityAnimFromTabHost(activity, UnopenTreeActivity.class);
					}

				} else {
					ToastUtils.TextToast(activity, index.failureReason + "-" + index.failureMessage, 1000 * 2);

				}
			}
		});

	}
*/
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
