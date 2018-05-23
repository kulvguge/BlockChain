package block.com.blockchain.request;

import block.com.blockchain.bean.BaseBean;
import block.com.blockchain.bean.MessageBean;
import block.com.blockchain.bean.ResultInfo;
import block.com.blockchain.bean.UserBean;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ts on 2018/5/17.
 */

public interface RequestApi {
    /**
     * 获取验证码
     *
     * @param type   1：注册 2：登录 3：忘记密码
     * @param mobile 接收短信手机号
     * @return
     */
    @FormUrlEncoded
    @POST("sms")
    Flowable<ResultInfo<BaseBean>> getMsgCode(@Field("type") int type, @Field("mobile") String mobile);

    /**
     * 注册接口
     *
     * @param mobile           注册手机号
     * @param valid_code       验证码
     * @param invite_code      邀请码
     * @param pwd              密码
     * @param pwd_confirmation 重复密码
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Flowable<ResultInfo<UserBean>> register(@Field("mobile") String mobile, @Field("valid_code") String valid_code, @Field
            ("invite_code") String invite_code, @Field("pwd") String pwd, @Field("pwd_confirmation") String
                                             pwd_confirmation);

    /**
     * 密码登陆
     *
     * @param type
     * @param mobile
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Flowable<MessageBean> loginPwd(@Field("type") int type, @Field("mobile") String mobile, @Field("pwd") String pwd);

    /**
     * 验证码登录
     *
     * @param type
     * @param mobile
     * @param valid_code
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<MessageBean> loginCode(@Field("type") int type, @Field("mobile") String mobile, @Field("valid_code")
            String valid_code);

    @GET("loginout")
    Observable<MessageBean> loginOut();

    /**
     * 忘记密码
     *
     * @param mobile           手机号
     * @param valid_code       验证码
     * @param pwd              密码
     * @param pwd_confirmation 重复密码
     * @return
     */
    @FormUrlEncoded
    @POST("forget")
    Observable<MessageBean> forgetPsd(@Field("mobile") String mobile, @Field("valid_code") String valid_code, @Field
            ("pwd") String pwd, @Field("pwd_confirmation") String pwd_confirmation);

    /**
     * 用户信息查询
     *
     * @param type
     * @param mobile
     * @param Authorization
     * @return
     */
    @GET("user/info")
    Observable<MessageBean> queryUserInfo(@Field("type") int type, @Field("mobile") String mobile, @Field
            ("Authorization") String Authorization);

    /**
     * 修改用户资料
     *
     * @param userBean
     * @return
     */
    @POST("user/edit")
    Observable<MessageBean> motifyUseInfo(@Body UserBean userBean);

}