package block.com.blockchain.request;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import block.com.blockchain.utils.SPUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by ts on 2018/5/23.
 */

public class LoggingInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        //=========发送===========
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Connection","keep-alive")
                .addHeader("Accept","*/*")
                .addHeader("X-HB-Client-Type","ayb-android")
                .addHeader("Content-Type","multipart/form-data; boundary=7db372eb000e2");
        HttpUrl requestUrl = request.url();
        if (request.method().equals("POST")) {
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String paramsStr = buffer.readString(charset);
            Log.e("发送==requestUrl=", requestUrl.toString());
            Log.e("发送==requestUrl_body=", paramsStr);
        } else if (request.method().equals("GET")) {
            Log.e("发送==requestUrl=", requestUrl.toString());
        }

        String AUTH = (String) SPUtils.getFromApp(HttpConstant.UserInfo.AUTH, "");
        if (!TextUtils.isEmpty(AUTH)) {
            //打印发送信息
            Log.e("发送==requestUrl=Header", "(" + AUTH + ")");
            builder.header("Authorization", "Bearer " + AUTH);
            request = builder.build();
        }
        Response response = chain.proceed(request);
        //   打印Log用,因為body().String只能調用一次
        Response response1 = chain.proceed(request);
        String content = response1.body().string();
        response1 = null;
        //打印接收信息
        Log.e("接收=responseUrl=", content);
        return response;
    }
}
