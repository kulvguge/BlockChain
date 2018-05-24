package block.com.blockchain.bean;

/**
 * Created by ts on 2018/5/17.
 */

public class UserBean  extends BaseBean{
    private String nickname;

    private String mobile;
    private String url;
    private int sex;
    private String  id_card;
    private int  lock;
    private String self_sign;

    private String birthday;

    private int integral;

    private String real_name;

    private String enterprise;
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        if(nickname==null)
            return "";
        return this.nickname;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        if(mobile==null)
            return "";
        return this.mobile;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }
    public void setSelf_sign(String self_sign){
        this.self_sign = self_sign;
    }
    public String getSelf_sign(){
        if(self_sign==null)
            return "";
        return this.self_sign;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        if(birthday==null)
            return "";
        return this.birthday;
    }
    public void setIntegral(int integral){
        this.integral = integral;
    }
    public int getIntegral(){
        return this.integral;
    }

    public String getReal_name() {
        if(real_name==null)
            return "";
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getEnterprise() {
        if(enterprise==null)
            return "";
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }
}
