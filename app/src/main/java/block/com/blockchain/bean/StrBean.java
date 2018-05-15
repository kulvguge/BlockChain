package block.com.blockchain.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/8.
 */

public class StrBean implements Serializable {
    public int start;//字符串开始下标
    public int end;//字符串结束下标
    public int type;//0表示数字与字母，1表示中文
    public int newStart;//转为纯数字字母后的开始下标
    public int newEnd;//转为纯数字字母后的结束下标
    public String firstWord;//
}
