package network.server;

import java.net.Socket;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 服务器 - 玩家类
 *
 * 该类必须保证Socket值不为空，一个对象对应目前连接的一个客户端
 */
public class Player {
    /**
     * socket
     */
    private Socket socket;

    /**
     * 用户名
     */
    private String accountName;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private int authority; // 0guest 1account 2op

    /**
     * 构造方法
     * @param socket socket
     */
    public Player(Socket socket) {
        this.socket = socket;
    }

    /**
     * 获取socket
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置用户名
     * @param accountName 用户名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取密码
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取权限
     * @return 权限
     */
    public int getAuthority() {
        return authority;
    }

    /**
     * 设置权限
     * @param authority 权限
     */
    public void setAuthority(int authority) {
        this.authority = authority;
    }
}
