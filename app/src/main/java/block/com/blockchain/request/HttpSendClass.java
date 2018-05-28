package block.com.blockchain.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import block.com.blockchain.utils.SPUtils;

/**
 * Created by Administrator on 2018/4/23.
 */

public class HttpSendClass {
    private static FinalHttp finalHttp = new FinalHttp();
    private static HttpSendClass send = new HttpSendClass();

    private static FinalHttp getFinalHttp() {
        return finalHttp;
    }

    public static HttpSendClass getInstance() {
        return send;
    }

    public <T> void post(AjaxParams params, String url, final AjaxCallBack<T>
            sendReturnDataListener) {
        getFinalHttp().post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Type type = sendReturnDataListener.getClass().getGenericSuperclass();
                ParameterizedType parameterized = (ParameterizedType) type;
                Type genType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
                if (genType.toString().contains("java.lang.String")) {
                    sendReturnDataListener.onSuccess((T) s);
                } else {
                    try {
                        T t = new Gson().fromJson(s, genType);
                        sendReturnDataListener.onSuccess(t);
                    } catch (Exception e) {
                        Log.i("json转换异常", e.toString());
                        sendReturnDataListener.onFailure(e, "转换异常");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
                sendReturnDataListener.onFailure(t, strMsg);
            }
        });
    }

    public <T> void get(AjaxParams params, String url, final AjaxCallBack<T>
            sendReturnDataListener) {

        getFinalHttp().get(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Type type = sendReturnDataListener.getClass().getGenericSuperclass();
                ParameterizedType parameterized = (ParameterizedType) type;
                Type genType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
                if (genType.toString().contains("java.lang.String")) {
                    sendReturnDataListener.onSuccess((T) s);
                } else {
                    try {
                        T t = new Gson().fromJson(s, genType);
                        sendReturnDataListener.onSuccess(t);
                    } catch (Exception e) {
                        Log.i("json转换异常", e.toString());
                        sendReturnDataListener.onFailure(e, "转换异常");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
                sendReturnDataListener.onFailure(t, strMsg);
            }
        });
    }

    public <T> void getWithToken(AjaxParams params, String url, final AjaxCallBack<T>
            sendReturnDataListener) {
        String AUTH = (String) SPUtils.getFromApp(HttpConstant.UserInfo.AUTH, "");
        getFinalHttp().addHeader("Authorization", "Bearer " + AUTH);
        getFinalHttp().get(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.i("string_response", s);
                Type type = sendReturnDataListener.getClass().getGenericSuperclass();
                ParameterizedType parameterized = (ParameterizedType) type;
                Type genType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
                if (genType.toString().contains("java.lang.String")) {
                    sendReturnDataListener.onSuccess((T) s);
                } else {
                    try {
                        T t = new Gson().fromJson(s, genType);
                        sendReturnDataListener.onSuccess(t);
                    } catch (Exception e) {
                        Log.i("json转换异常", e.toString());
                        sendReturnDataListener.onFailure(e, "转换异常");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
                sendReturnDataListener.onFailure(t, strMsg);
            }
        });
    }

    public <T> void postWithToken(AjaxParams params, String url, final AjaxCallBack<T>
            sendReturnDataListener) {
        String AUTH = (String) SPUtils.getFromApp(HttpConstant.UserInfo.AUTH, "");
        getFinalHttp().addHeader("Authorization", "Bearer " + AUTH);
        getFinalHttp().post(url, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Type type = sendReturnDataListener.getClass().getGenericSuperclass();
                ParameterizedType parameterized = (ParameterizedType) type;
                Type genType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
                if (genType.toString().contains("java.lang.String")) {
                    sendReturnDataListener.onSuccess((T) s);
                } else {
                    try {
                        T t = new Gson().fromJson(s, genType);
                        sendReturnDataListener.onSuccess(t);
                    } catch (Exception e) {
                        Log.i("json转换异常", e.toString());
                        sendReturnDataListener.onFailure(e, "转换异常");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
                sendReturnDataListener.onFailure(t, strMsg);
            }
        });
    }
}
