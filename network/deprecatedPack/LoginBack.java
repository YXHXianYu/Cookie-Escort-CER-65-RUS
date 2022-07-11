package network.deprecatedPack;

import network.pack.Message;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 登录回报
 */
public class LoginBack extends Message implements java.io.Serializable {
    /**
     * 0:成功
     */
    public static final int SUCCESS = 0;
    /**
     * 1:注册失败，重名
     */
    public static final int FAIL_DUPLICATE_NAME = 1;
    /**
     * 2:登陆失败，账号不存在
     */
    public static final int FAIL_NOT_FOUND_ACCOUNT = 2;
    /**
     * 3:登录失败，密码错误
     */
    public static final int FAIL_WRONG_PASSWORD = 3;
    /**
     * 4:登陆失败，账号已被登录
     */
    public static final int FAIL_ACCOUNT_HAS_LOGIN = 4;
    /**
     * 构造方法
     */
    public LoginBack(int type) {
        super(type);
    }
}
