package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 *
 * 加入游戏包
 */
public class Join implements java.io.Serializable {
    /**
     * 用户名字
     */
    private String name;

    /**
     * 构造方法
     */
    public Join(String name) {
        this.name = name;
    }

    /**
     * 获取名字
     */
    public String getName() {
        return name;
    }
}
