package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 回报
 */
public abstract class Message implements java.io.Serializable {
    /**
     * 信息内容
     */
    private int type;

    /**
     * 构造方法
     */
    public Message(int type) {
        this.type = type;
    }

    /**
     * 获取类型
     */
    public int getType() {
        return type;
    }
}
