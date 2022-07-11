package network.deprecatedPack;

import network.pack.Message;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class JoinRoomBack extends Message implements java.io.Serializable {
    /**
     * 0: 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 1: 失败
     */
    public static final int FAIL = 1;
    /**
     * 构造方法
     */
    public JoinRoomBack(int type) {
        super(type);
    }
}
