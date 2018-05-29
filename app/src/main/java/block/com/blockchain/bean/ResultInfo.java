package block.com.blockchain.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ts on 2018/5/17.
 */

public class ResultInfo<T> {
    public T data;
    @SerializedName("msg")
    public String message;
    public String status;
}
