package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-12
 */
public class Ping implements java.io.Serializable {
    /**
     * ping（延迟）
     */
    private long ping;

    /**
     * 构造方法
     */
    public Ping(long ping) {
        this.ping = ping;
    }

    /**
     * 获取ping值
     */
    public long getPing() {
        return ping;
    }
}
