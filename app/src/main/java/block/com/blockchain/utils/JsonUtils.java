package block.com.blockchain.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import block.com.blockchain.bean.UserBean;

/**
 * Created by Administrator on 2018/6/12.
 */

public class JsonUtils {
    /**
     * 根据List获取到对应的JSONArray
     *
     * @param list
     * @return
     */
    public static JSONArray getJSONArrayByList(List<UserBean> list) {
        JSONArray jsonArray = new JSONArray();
        if (list == null || list.isEmpty()) {
            return jsonArray;//nerver return null
        }
        try {
            for (UserBean o : list) {
                if (TextUtils.isEmpty(o.getReal_name()) || TextUtils.isEmpty(o.getMobile()))
                    continue;
                JSONObject ob = new JSONObject();
                ob.put("name", o.getReal_name());
                ob.put("mobile", o.getMobile());
                jsonArray.put(ob);
            }
            Log.i("o2o_img_jsonstring", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
