package block.com.blockchain.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import block.com.blockchain.bean.StrBean;

/**
 * Created by ts on 2017/8/9.
 */

public class GroupUtils {
    private static String zhongwen = "[\u4E00-\u9FA5]";
    private static String pinyin = "[a-zA-Z0-9]";
    private static String cnletternum = "[(a-zA-Z0-9)\u4E00-\u9FA5]";
    private static Pattern pattern1;
    private StringBuffer buffer = new StringBuffer();
    List<StrBean> list = new ArrayList<StrBean>();
    private static CharaterParse parse;
    private static GroupUtils groupUtils;

    private GroupUtils() {

    }

    public static GroupUtils getInstance() {
        if (groupUtils == null) {
            groupUtils = new GroupUtils();
            pattern1 = Pattern.compile(cnletternum);
            parse = new CharaterParse();
        }
        return groupUtils;
    }

    public String nameToList(String text1) {
        buffer.setLength(0);
        list.clear();
        Matcher matcher1 = pattern1.matcher(text1);
        while (matcher1.find()) {
            matcher1.group();
            matcher1.start();
            matcher1.end();
            StrBean strBean = new StrBean();
            strBean.end = matcher1.end();
            if (list.size() == 0) {
                strBean.start = 0;
            } else {
                strBean.start = list.get(list.size() - 1).end;
            }
            if (matcher1.group().matches(pinyin)) {
                strBean.type = 0;
            } else if (matcher1.group().matches(zhongwen)) {
                String string1 = parse.getSellingWithPolyphone(matcher1.group());
                strBean.firstWord = string1.substring(0, 1);
                strBean.type = 1;
            }
            list.add(strBean);

        }
        int i = 0;
        while (list.size() > i) {
            if (list.get(i).type == 1) {
                String string = parse.getSellingWithPolyphone(text1.substring(list.get(i).start, list.get(i).end));

                if (i == 0) {
                    list.get(i).newStart = 0;
                } else {
                    list.get(i).newStart = buffer.length();
                }
                buffer.append(string);
                list.get(i).newEnd = buffer.length();
            } else {
                if (i == 0) {
                    list.get(i).newStart = 0;
                } else {
                    list.get(i).newStart = buffer.length();
                }
                buffer.append(text1.substring(list.get(i).start, list.get(i).end));
                list.get(i).newEnd = buffer.length();
            }
            i++;
        }
        Gson gson = new Gson();
        String gsonStr = gson.toJson(list);

        return gsonStr;
    }

    public List<StrBean> strToList(String gsonStr) {
        if (TextUtils.isEmpty(gsonStr)) {
            return new ArrayList<StrBean>();
        }
        Gson gson = new Gson();
        List<StrBean> list = gson.fromJson(gsonStr, new TypeToken<List<StrBean>>() {
        }.getType());
        return list;
    }

    public  String getFirstLetter(String string) {
        Log.i("GroupUtils__",string);
        if (TextUtils.isEmpty(string))
            return null;
        String childStr = string.substring(0, 1);
        if (childStr.matches(zhongwen)) {
            String str = parse.getSellingWithPolyphone(childStr);
            if (TextUtils.isEmpty(str))
                return null;
            return str.substring(0, 1).toUpperCase();
        } else if (childStr.matches(pinyin)) {
            Log.i("GroupUtils____",childStr+"");
            return childStr.toUpperCase();
        } else {
            return null;
        }
    }
}
