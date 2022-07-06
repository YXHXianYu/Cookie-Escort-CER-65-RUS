package entity;

import common.Texture;
import physics.Hitbox;

/**
 * @author YXH_XianYu
 * Created On 2022-06-30
 *
 * 子弹类
 * 该类用于实现子弹
 */
public class Bullet extends Entity {
    /**
     * 子弹的伤害
     */
    private int damage;

    /**
     * 发送者编号
     * （若碰撞至发射者，则直接跳过）
     */
    private int senderID;

    /**
     * 构造一个发送者编号的子弹
     * @param hitbox 碰撞箱
     * @param texture 贴图
     * @param damage 伤害
     * @param senderID 发送者编号
     */
    public Bullet(Hitbox hitbox, Texture texture, int damage, int senderID) {
        super(hitbox, texture, 1); // 子弹的血量=1，只记录是否存在
        this.damage = damage;
        this.senderID = senderID;
    }

    /**
     * 构造一个不含发送者编号的子弹
     * @param hitbox 碰撞箱
     * @param texture 贴图
     * @param damage 伤害
     */
    public Bullet(Hitbox hitbox, Texture texture, int damage) {
        this(hitbox, texture, damage, -1);
    }

    /**
     * deep copy constructor
     */
    public Bullet(Bullet another) {
        this(new Hitbox(another.getHitbox()), new Texture(another.getTexture()), another.damage, -1);
    }

    /**
     * 获取子弹伤害
     * @return 子弹伤害
     */
    public int getDamage() {
        return damage;
    }

    /**
     * 设置子弹伤害
     * @param damage 子弹伤害
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * 设置发送者ID
     * @param senderID 发送者ID
     */
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    /**
     * 获取发送者ID
     * @return 发送者ID
     */
    public int getSenderID() {
        return senderID;
    }
}
