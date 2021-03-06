package network;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 */
public interface NetworkConstants {
    /**
     * 初始化：成功
     */
    int INIT_SUCCESS = 0;

    /**
     * 初始化：未知的IP
     */
    int INIT_UNKNOWN_HOST_EXCEPTION = 1;

    /**
     * 初始化：连接错误
     */
    int INIT_IO_EXCEPTION = 2;

    /**
     * 服务器等待中
     */
    int SERVER_WAITING = 0;

    /**
     * 服务器进行游戏中
     */
    int SERVER_PLAYING = 1;
}
