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
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("X-HB-Client-Type", "ayb-android")
                .addHeader("Content-Type", "multipart/form-data; boundary=7db372eb000e2");
        HttpUrl requestUrl = request.url();
        String AUTH ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYzZjRiNjRhNjcxZGMxOTM0NDEzYzg5MDc4OTM4MzI0YjkxZjk2N2FjYTg5ZjJkZWFhNWM3Yzk0YWIyNzY4NmFjOGMwMTc0MWNkZjRjNDFjIn0.eyJhdWQiOiIyIiwianRpIjoiNjNmNGI2NGE2NzFkYzE5MzQ0MTNjODkwNzg5MzgzMjRiOTFmOTY3YWNhODlmMmRlYWE1YzdjOTRhYjI3Njg2YWM4YzAxNzQxY2RmNGM0MWMiLCJpYXQiOjE1MjcyMjgyNjUsIm5iZiI6MTUyNzIyODI2NSwiZXhwIjoxNTU4NzY0MjY1LCJzdWIiOiIzOCIsInNjb3BlcyI6WyIqIl19.W9fHYdCH7XafJZNLXTTtR-c-Z6i4QoOYIrb_b5nW5qxYPepsl1mBkYuxUgg2ZSK6lTLz33YCsKBkL3e31Q3c1iNuNLiB3scgnyG2EOkuPxU6C2_qRn2zNFRTnW1q1kTw1rswCjsMHPPZ8Gp_xHYht0KgG7ttrhdL4YUVAqBrRFMVB6PI_YplRG9I7vQ2ydYk2S6Uih2N47dETBL5LdXhCJChl32QsOOxZ9_qu2czA37PnavQR-OyHhCvQD1ro1TPC1lAvGbJrygRP-z5sI-G3HdJVRQx_MeKOCz4TeZqPmyTXl37smwElnBFGo0lW5tprWpJmZhAv77qZJZh2Mf6YtWesWbXBdr49nzCC7XtzfcTxYPEpKTT9KMtpPyJFSPmXCCkUvgQPnl1ZlAW67BBP08r3EzBb1aA_AS8b3DS594NqLP4fc67mXoC7DAapW0HYpTJkthnUvVB4xZBp527iYASLk4nbBJYSgrx4WHZ9pZRkEAvOJTCDcQZRcNxWjcLypRQz2p5779oOdSX7Vtsti05xpw0Ps8O4Rcz_Zr700lQzfTlcJrh1S9gMgWTaTc2G9A4TzkEj_A2hAlVXHyqtFKV7AJM3Y_nUMV-z7zswD-puQ74wMLqJKjqSIniZHPjTlB0YKbulJ9LoMgf844wZhbZEZM6Vq-yKwAuu0EQeQg";
        if (!TextUtils.isEmpty(AUTH)) {
            //打印发送信息
            Log.e("发送==requestUrl=Header", "(" + AUTH + ")");
            builder.addHeader("Authorization", "Bearer " + AUTH);
            request = builder.build();
        }
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
            Log.e("发送==requestUrl_body=", paramsStr);

        } else if (request.method().equals("GET")) {
            Log.e("发送==requestUrl=", requestUrl.toString());
            Log.e("发送==requestUrl_header=", request.header("Authorization"));
        }
        return chain.proceed(request);
    }
}
