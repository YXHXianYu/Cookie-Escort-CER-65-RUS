package factory.entityFactoryMessage;

/**
 * @author YXH_XianYu
 * Created On 2022-07-10
 */
public abstract class FactoryMessage implements java.io.Serializable {
    /**
     * ID
     */
    private int ID;

    /**
     * 类型
     */
    private int type;

    /**
     * 构造方法
     */
    public FactoryMessage(int ID, int type) {
        this.ID = ID;
        this.type = type;
    }

    /**
     * 构造方法2
     */
    public FactoryMessage(int type) {
        this(0, type);
    }

    /**
     * 获取ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 设置ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 获取类型
     */
    public int getType() {
        return type;
    }
}
