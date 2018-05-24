package block.com.blockchain.request;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

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


        if (!TextUtils.isEmpty(HttpConstant.Authorization)) {
            //打印发送信息
            Log.e("发送==requestUrl=Header", "(" + HttpConstant.Authorization + ")");
            Request.Builder builder = request.newBuilder().addHeader("Authorization", HttpConstant.Authorization);
            request = builder.build();
        }
        //打印发送信息

        Response response = chain.proceed(request);
        //打印Log用,因為body().String只能調用一次
        Response response1 = chain.proceed(request);
        String content = response1.body().string();
        response1 = null;
        //打印接收信息
        Log.e("接收=responseUrl=", content);
        return response;
    }
}
