package block.com.blockchain.request;

/**
 * Created by TS on 2018/5/28.
 */

public class SenUrlClass {
    public final static String LOGIN = HttpConstant.HTTPHOST + "api/login";
    public final static String REGISTER = HttpConstant.HTTPHOST + "api/register";
    public final static String CODE_REGISTER = HttpConstant.HTTPHOST + "api/sms";
    public final static String TOKEN = HttpConstant.HTTPHOST + "oauth/token";
    public final static String USER_INFO = HttpConstant.HTTPHOST + "api/user/info";
    public final static String MOTIFY_USER = HttpConstant.HTTPHOST + "api/user/edit";
    public final static String FRIEND_DEL = HttpConstant.HTTPHOST + "api/friend/del";
    public final static String IS_SESSION = HttpConstant.HTTPHOST + "api/is_session";
    public final static String APPLY_I = HttpConstant.HTTPHOST + "api/friend/get_my_application";
    public final static String APPLY_OTHER = HttpConstant.HTTPHOST + "api/friend/get_other_application";
    public final static String FRIEND_REFUSE = HttpConstant.HTTPHOST + "api/friend/refused";
    public final static String FRIEND_PERMIT= HttpConstant.HTTPHOST + "api/friend/add";
    public final static String FRIEND_LIST =  HttpConstant.HTTPHOST +"api/friend/get";
    public final static String POWER_LIST =  HttpConstant.HTTPHOST +"api/pool/count";
    public final static String POWER_COLLECTION =  HttpConstant.HTTPHOST +"api/pool/collect";
    public final static String TRADE_TURNOVER =  HttpConstant.HTTPHOST +"api/user/integeallist";
    public final static String CONTACTS_DOWN =  HttpConstant.HTTPHOST +"api/phone_list/download";
    public final static String CONTACTS_UP=  HttpConstant.HTTPHOST +"api/phone_list/upload";
}
