package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 协议类
 * 用来储存协议编号
 */
public class Protocol {

    /**
     * 心跳
     * C -> S
     *
     * 本信息无其他值
     */
    public final int HEARTBEAT = 0;

    /**
     * 版本检查
     * S -> C
     *
     * 服务端向客户端发送版本信息，如果版本不同，客户端自动退出
     * （通常是版本过低）
     */
    public final int VERSION_CHECK = 1;

    /**
     * 登录
     * C -> S
     */
    public final int LOGIN = 2;

    /**
     * 登录回报
     * S -> C
     */
    public final int LOGIN_BACK = 3;

    /**
     * 请求大厅信息
     * C -> S
     *
     * 本信息无其他值
     * 客户端每隔一定时间自己发一次，服务端收到后会把最新的大厅信息发回去
     */
    public final int REQUEST_LOBBY = 4;

    /**
     * 大厅信息
     * S -> C
     */
    public final int LOBBY_INFORMATION = 5;

    /**
     * 创建房间
     * C -> S
     */
    public final int CREATE_ROOM = 6;

    /**
     * 加入房间
     * C -> S
     */
    public final int JOIN_ROOM = 7;

    /**
     * 创建房间回报
     * S -> C
     */
    public final int CREATE_ROOM_BACK = 8;

    /**
     * 加入房间回报
     * S -> C
     */
    public final int JOIN_ROOM_BACK = 9;

    /**
     * 控制
     * C -> S
     */
    public final int CONTROL = 10;

    /**
     * 纹理
     * S -> C
     */
    public final int TEXTURE = 11;
}
