package weapon;

import common.Texture;
import entity.Bullet;
import manager.GameManager;

import java.io.File;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 武器类
 * TODO 武器类
 */
public abstract class Weapon {
    /**
     * 武器Tag
     */
    private String Tag;

    /**
     * 武器发射的子弹（原型）
     */
    private Bullet bullet;

    /**
     * 武器发射的子弹速度（速率）
     */
    private int velocity;

    /**
     * 武器发射的子弹伤害
     */
    private int damage;

    /**
     * 武器射击间隔（射速）
     */
    private int interval;

    /**
     * 武器纹理
     * 0 Left
     * 1 Right
     * 2 Left Loading
     * 3 Right Loading
     */
    private Texture[] textures;

    /**
     * 射击音效編號
     */
    private int shootSoundEffectType;

    /**
     * 构造一把武器
     * @param bullet 子弹
     * @param velocity 速度
     * @param damage 伤害
     * @param t0 左向纹理
     * @param t1 右向纹理
     * @param t2 左向换弹中纹理
     * @param t3 右向换弹中纹理
     */
    public Weapon(Bullet bullet, int velocity, int damage, int interval, Texture t0, Texture t1, Texture t2, Texture t3, int shootSoundEffectType) {
        this.bullet = bullet;
        this.velocity = velocity;
        this.damage = damage;
        this.interval = interval;
        this.textures = new Texture[] {
                t0, t1, t2, t3,
        };
        this.shootSoundEffectType = shootSoundEffectType;
    }

    /**
     * deep copy constructor
     */
    public Weapon(Weapon another) {
        this(new Bullet(another.bullet), another.velocity, another.damage, another.interval, another.textures[0], another.textures[1], another.textures[2], another.textures[3], another.shootSoundEffectType);
    }

    /**
     * 获取Tag
     * @return Tag
     */
    public String getTag() {
        return Tag;
    }

    /**
     * 设置Tag
     * @param tag Tag
     */
    public void setTag(String tag) {
        Tag = tag;
    }

    /**
     * 获取武器的子弹
     * @return 子弹
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * 获取武器中子弹的一份拷贝
     * @return 子弹的一份拷贝
     */
    public Bullet getNewBullet() {
        return new Bullet(bullet);
    }

    /**
     * 获取武器发射子弹的速度
     * @return 发射子弹的速度
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * 获取武器的伤害
     * @return 伤害
     */
    public int getDamage() {
        return damage;
    }

    /**
     * 获取武器的射击间隔
     * @return 射击间隔
     */
    public int getInterval() {
        return interval;
    }

    /**
     * 上一次射击的帧数
     */
    private long lastShootTimeStamp;


    /**
     * 获取纹理
     * 0 Left
     * 1 Right
     * 2 Left Loading
     * 3 Right Loading
     * @param index 纹理编号
     * @return 对应纹理
     */
    public Texture getTexture(int index) {
        if(index < 0 || index > 1) return null;
        return textures[index + (GameManager.getInstance().getTimeStamp() - lastShootTimeStamp < interval ? 2 : 0)];
    }

    /**
     * 获取射击音效
     * @return 射击音效
     */
    public int getShootSoundEffectType() {
        return shootSoundEffectType;
    }

    /**
     * 抽象方法：攻击
     * @param senderID 攻击者ID
     * @param x 攻击者x轴坐标
     * @param y 攻击者y轴坐标
     * @param aimX 瞄准坐标x
     * @param aimY 瞄准坐标y
     * @return true if shoot
     */
    public boolean attack(int senderID, int x, int y, int aimX, int aimY) {
        if(GameManager.getInstance().getTimeStamp() - lastShootTimeStamp <= interval) return false;
        lastShootTimeStamp = GameManager.getInstance().getTimeStamp();
        return true;
    }
}
