package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 登陆包
 */
public class Login {
    /**
     * 登录类型
     */
    private int type;

    /**
     * 用户名
     */
    private String accountName;

    /**
     * 密码
     */
    private String password;

    /**
     * 构造方法
     */
    public Login(int type, String accountName, String password) {
        this.type = type;
        this.accountName = accountName;
        this.password = password;
    }

    /**
     * 获取账号类型
     */
    public int getType() {
        return type;
    }

    /**
     * 获取登录账号名字
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 获取登陆密码
     */
    public String getPassword() {
        return password;
    }
}
