package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class CreateRoomBack extends Message {
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
    public CreateRoomBack(int type) {
        super(type);
    }
}
