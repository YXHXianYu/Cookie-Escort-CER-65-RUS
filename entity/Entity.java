package entity;

import common.Texture;
import physics.Hitbox;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 实体类
 * 该类用于储存一个实体（能移动的、会产生碰撞的物体）
 *
 * 如果血量小等于0，则血量无法再被增加。
 * 但可以通过setHp方法来直接设置血量，从而重新激活实体。
 * （备用功能。正常来说，血量小等于0，会直接清除该实体，并不会提供重新激活的可能。）
 */
public class Entity {
    /**
     * 实体编号（由EntityManager给出）
     */
    private int ID;

    /**
     * 实体Tag
     */
    private String tag;

    /**
     * 碰撞箱（记录位置、速度、碰撞箱）
     */
    private Hitbox hitbox;

    /**
     * 贴图
     */
    private Texture texture;

    /**
     * 血量
     */
    private int hp;

    /**
     * 构造一个基本的实体
     * @param hitbox 碰撞箱
     * @param texture 贴图
     */
    public Entity(Hitbox hitbox, Texture texture, int hp) {
        this.hitbox = hitbox;
        this.texture = texture;
        this.hp = hp;
    }

    public Entity(Entity another) {
        this(new Hitbox(another.getHitbox()), (another.getTexture() != null)?(new Texture(another.getTexture())):(null), another.getHp());
    }

    /**
     * 设置角色编号
     * @param ID 编号
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 获取角色编号
     * @return 角色编号
     */
    public int getID() {
        return ID;
    }

    /**
     * 获取Tag
     * @return Tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置Tag
     * @param tag Tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取碰撞箱
     * @return 碰撞箱
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * 获取贴图
     * @return 贴图
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * 获取障碍物当前血量
     * @return 血量
     */
    public int getHp() {
        return hp;
    }

    /**
     * 设置障碍物血量
     * @param hp 目标血量
     */
    public void setHp(int hp) { // this methods may rebuild obstacle.
        this.hp = hp;
    }

    /**
     * 增加障碍物的血量（只会在障碍物血量大于0时发挥作用）
     * @param delta 增加值
     */
    public void addHp(int delta) {
        if(!isExist()) return;
        hp += delta;
    }

    /**
     * 减少障碍物的血量（只会在障碍物血量大于0时发挥作用）
     * @param delta 减少值
     */
    public void subHp(int delta) {
        if(!isExist()) return;
        hp -= delta;
    }

    /**
     * 判断障碍物是否存在（血量是否大于0）
     * @return 若存在，返回true；否则，返回false。
     */
    public boolean isExist() {
        return hp > 0;
    }
}
