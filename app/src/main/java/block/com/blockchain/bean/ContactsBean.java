package block.com.blockchain.bean;

import android.util.Log;

import block.com.blockchain.utils.PhoneUtils;

/**
 * Created by TS on 2018/6/8.
 */

public class ContactsBean extends BaseBean {
    private String name;
    private String number;
    private String sortKey;
    private int id;

    public ContactsBean(String name, String number, String sortKey, int id) {
        setName(name);
        setNumber(number);
        setSortKey(PhoneUtils.getSortkey(sortKey));
        setId(id);
        Log.i("ContactsBean", "name:" + name + "number:" + number + "sortKey:" + sortKey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
