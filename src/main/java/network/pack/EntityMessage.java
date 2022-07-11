package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-10
 *
 * 实体信息类
 */
public class EntityMessage implements java.io.Serializable {
    /**
     * ID
     */
    private int id;

    /**
     * 血量
     */
    private int hp;

    /**
     * x轴坐标
     */
    private int x;

    /**
     * y轴坐标
     */
    private int y;

    /**
     * x轴速度
     */
    private int vx;

    /**
     * y轴速度
     */
    private int vy;

    /**
     * x轴长度
     */
    private int lx;

    /**
     * y轴长度
     */
    private int ly;

    /**
     * 实体信息构造方法
     * @param id 实体ID
     * @param hp 血量
     * @param x x轴坐标
     * @param y y轴坐标
     * @param vx x轴速度
     * @param vy y周速度
     * @param lx x轴长度
     * @param ly y轴长度
     */
    public EntityMessage(int id, int hp, int x, int y, int vx, int vy, int lx, int ly) {
        this.id = id;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.lx = lx;
        this.ly = ly;
    }

    /**
     * 获取ID
     */
    public int getId() {
        return id;
    }

    /**
     * 获取血量
     */
    public int getHp() {
        return hp;
    }

    /**
     * 获取x轴坐标
     */
    public int getX() {
        return x;
    }

    /**
     * 获取y轴坐标
     */
    public int getY() {
        return y;
    }

    /**
     * 获取x轴速度
     */
    public int getVx() {
        return vx;
    }

    /**
     * 获取y轴速度
     */
    public int getVy() {
        return vy;
    }

    /**
     * 获取x轴长度
     */
    public int getLx() {
        return lx;
    }

    /**
     * 获取y轴长度
     */
    public int getLy() {
        return ly;
    }
}
