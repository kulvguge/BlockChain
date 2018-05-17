package block.com.blockchain.bean;

/**
 * Created by ts on 2018/5/16.
 */

public class MessageBean extends BaseBean {
    public String url;
    public String date;
    public String nickName;
    public int sex;
    public int status;

    public MessageBean(String nickName) {
        this.nickName = nickName;
    }
}
