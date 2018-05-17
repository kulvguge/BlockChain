package block.com.blockchain.callback;

public class ObjectCallBack {

    private CallBack callBack;

    public boolean isClick = false;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void click() {
        callBack.doSomeThing(this);
    }
}
