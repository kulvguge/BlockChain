package block.com.blockchain.request;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/4/24.
 */

public class GsonAnalyze {

    static Gson goon = new Gson();

    static Gson getGoon() {
        return goon;
    }

    //解析
    public static <T> T toAnalyze(String dataStr, Class<T> t) {
        return getGoon().fromJson(dataStr, t);
    }

    public static <T> T toListAnalyze(String dataStr, Class<T> t) {
        T bean = getGoon().fromJson(dataStr, t);
        return bean;
    }


}
