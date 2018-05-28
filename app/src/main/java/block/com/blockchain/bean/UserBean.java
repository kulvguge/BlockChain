package block.com.blockchain.bean;

/**
 * Created by ts on 2018/5/17.
 */

public class UserBean extends BaseBean {
    private int id;

    private String account;

    private String pic_url;

    private String nickname;

    private String mobile;

    private String self_sign;

    private String birthday;

    private int integral;

    private int lock;

    private String invite_code;

    private int sex;

    private String real_name;

    private String enterprise;

    private String id_card;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return noEmpty(this.account);
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_url() {
        return noEmpty(this.pic_url);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return noEmpty(this.nickname);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return noEmpty(this.mobile);
    }

    public void setSelf_sign(String self_sign) {
        this.self_sign = self_sign;
    }

    public String getSelf_sign() {
        return noEmpty(this.self_sign);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return noEmpty(this.birthday);
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getIntegral() {
        return this.integral;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public int getLock() {
        return this.lock;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getInvite_code() {
        return noEmpty(this.invite_code);
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return this.sex;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return noEmpty(this.real_name);
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getEnterprise() {
        return noEmpty(this.enterprise);
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card() {
        return noEmpty(this.id_card);
    }

    private String noEmpty(String str) {
        if (str == null)
            return "";
        return str;
    }
}
