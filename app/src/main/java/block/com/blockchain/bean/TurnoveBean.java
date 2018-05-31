package block.com.blockchain.bean;

/**
 * Created by ts on 2018/5/31.
 */

public class TurnoveBean extends BaseBean {

    private String order_number;

    private double num;

    private String remark;

    private String create_time;

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_number() {
        return this.order_number;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getNum() {
        return this.num;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return this.create_time;
    }
}
