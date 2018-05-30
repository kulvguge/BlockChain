package block.com.blockchain.bean;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by ts on 2018/5/29.
 * 资料是否发生改变监听类
 */

public class MotifyUserBean extends UserBean {
    private boolean has_url;
    private boolean has_nick;
    private boolean has_name;
    private boolean has_phone;
    private boolean has_sex;
    private boolean has_birth;
    private boolean has_work;
    private boolean has_sign;

    public boolean isHas_url() {
        return has_url;
    }

    public void setHas_url(boolean has_url) {
        this.has_url = has_url;
    }

    public boolean isHas_nick() {
        return has_nick;
    }

    public void setHas_nick(boolean has_nick) {
        this.has_nick = has_nick;
    }

    public boolean isHas_name() {
        return has_name;
    }

    public void setHas_name(boolean has_name) {
        this.has_name = has_name;
    }

    public boolean isHas_phone() {
        return has_phone;
    }

    public void setHas_phone(boolean has_phone) {
        this.has_phone = has_phone;
    }

    public boolean isHas_sex() {
        return has_sex;
    }

    public void setHas_sex(boolean has_sex) {
        this.has_sex = has_sex;
    }

    public boolean isHas_birth() {
        return has_birth;
    }

    public void setHas_birth(boolean has_birth) {
        this.has_birth = has_birth;
    }

    public boolean isHas_work() {
        return has_work;
    }

    public void setHas_work(boolean has_work) {
        this.has_work = has_work;
    }

    public boolean isHas_sign() {
        return has_sign;
    }

    public void setHas_sign(boolean has_sign) {
        this.has_sign = has_sign;
    }

    public AjaxParams getMotifyParams() {
        AjaxParams params = new AjaxParams();
        boolean changed = false;
        try {
            if (has_url) {
                params.put("file", new File(getPic_url()));
                changed = true;
            }

            if (has_birth) {
                params.put("birthday", getBirthday());
                changed = true;
            }

            if (has_name) {
                params.put("real_name", getReal_name());
                changed = true;
            }

            if (has_nick) {
                params.put("nick_name", getNickname());
                changed = true;
            }

            if (has_sex) {
                params.put("sex", getSex() + "");
                changed = true;
            }

            //后台目前没有这个修改功能
            if (has_phone) {
                params.put("moblie", getMobile());
                changed = true;
            }

            if (has_work) {
                params.put("enterprise", getEnterprise());
                changed = true;
            }

            if (has_sign) {
                params.put("self_sign", getSelf_sign());
                changed = true;
            }
            if (changed)
                return params;

            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
