package block.com.blockchain.request;

import android.util.Log;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ts on 2018/5/23.
 */

public class LoggingInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        //=========发送===========
        Request request = chain.request();
        HttpUrl requestUrl=request.url();
        //打印发送信息
        Log.e("发送==requestUrl=",requestUrl.toString());
        Response response = chain.proceed(chain.request());
        //打印Log用,因為body().String只能調用一次
        Response response1=chain.proceed(chain.request());
        String content = response1.body().string();
        response1=null;
        //打印接收信息
        Log.e("接收=responseUrl=",content);
        return response;
    }
}
