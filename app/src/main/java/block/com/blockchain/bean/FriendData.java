package block.com.blockchain.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class FriendData extends BaseBean {
    private int current_page;

    private List<UserBean> data;

    private String first_page_url;

    private String from;

    private String next_page_url;

    private String path;

    private int per_page;

    private String prev_page_url;

    private String to;

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCurrent_page() {
        return this.current_page;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    public List<UserBean> getData() {
        return this.data;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public String getFirst_page_url() {
        return this.first_page_url;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return this.from;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getNext_page_url() {
        return this.next_page_url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPer_page() {
        return this.per_page;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public String getPrev_page_url() {
        return this.prev_page_url;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return this.to;
    }
}
