package network.deprecatedPack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 加入房间
 */
public class JoinRoom implements java.io.Serializable {
    /**
     * 房间名
     */
    private String name;

    /**
     * 房间密码
     */
    private String password;

    /**
     * 构造方法
     */
    public JoinRoom(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * 获取房间名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取用户输入的密码
     */
    public String getPassword() {
        return password;
    }
}
