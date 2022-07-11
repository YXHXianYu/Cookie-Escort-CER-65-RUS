package network;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 */
public interface SerializeConstants {
    /**
     * 心跳
     * C -> S
     *
     * 本信息无其他值
     */
    int HEARTBEAT = 0;

    /**
     * 版本检查
     * S -> C
     *
     * 服务端向客户端发送版本信息，如果版本不同，客户端自动退出
     * （通常是版本过低）
     */
    int VERSION_CHECK = 1;

    /**
     * 登录
     * C -> S
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int LOGIN = 2;

    /**
     * 登录回报
     * S -> C
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int LOGIN_BACK = 3;

    /**
     * 请求大厅信息
     * C -> S
     *
     * 本信息无其他值
     * 客户端每隔一定时间自己发一次，服务端收到后会把最新的大厅信息发回去
     *
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int REQUEST_LOBBY = 4;

    /**
     * 大厅信息
     * S -> C
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int LOBBY_INFORMATION = 5;

    /**
     * 创建房间
     * C -> S
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int CREATE_ROOM = 6;

    /**
     * 加入房间
     * C -> S
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int JOIN_ROOM = 7;

    /**
     * 创建房间回报
     * S -> C
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int CREATE_ROOM_BACK = 8;

    /**
     * 加入房间回报
     * S -> C
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int JOIN_ROOM_BACK = 9;

    /**
     * 控制
     * C -> S
     */
    int CONTROL = 10;

    /**
     * 纹理
     * S -> C
     * @deprecated 通信方式更换，弃用纹理包
     */
    @Deprecated
    int TEXTURE = 11;

    /**
     * 加入游戏
     * C -> S
     */
    int JOIN = 12;

    /**
     * 让客户端退出 或 客户端告知服务端自己要退出
     */
    int CLIENT_EXIT = 13;

    /**
     * 启动游戏
     */
    int START_GAME = 14;

    /**
     * 实体信息包
     */
    int ENTITY_MESSAGES = 15;
}
