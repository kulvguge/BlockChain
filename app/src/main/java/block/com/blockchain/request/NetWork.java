package block.com.blockchain.request;

import org.reactivestreams.Subscriber;

import block.com.blockchain.BuildConfig;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ts on 2018/5/17.
 */

public class NetWork {
    private static RequestApi requestApi;

    private static OkHttpClient okHttpClient = getHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static RequestApi getRequestApi() {
        if (requestApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(HttpConstant.HTTPHOST)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            requestApi = retrofit.create(RequestApi.class);
        }
        return requestApi;
    }

    public static RequestApi getTokenApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HttpConstant.TOKENHTTP)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        RequestApi requestApiT = retrofit.create(RequestApi.class);

        return requestApiT;
    }

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Flowable observable, Subscriber observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);//错误重连
        //调试模式打印Log日志
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        OkHttpClient client = builder.build();
        return client;
    }

}
