package weapon;

import common.Texture;
import entity.Bullet;
import factory.BulletFactory;
import factory.entityFactoryMessage.BulletFactoryMessage;
import manager.EntityManager;
import tool.MyTool;

/**
 * @author YXH_XianYu
 * Created On 2022-07-14
 *
 * 狙击枪类
 */
public class AX50 extends Weapon {
    /**
     * 构造方法
     * @param bullet 子弹
     * @param velocity 速度
     * @param damage 伤害
     * @param interval 射击CD
     * @param textures 纹理
     * @param shootSoundEffectType 音效
     */
    public AX50(Bullet bullet, int velocity, int damage, int interval, Texture[] textures, int shootSoundEffectType) {
        super(bullet, velocity, damage, interval, textures, shootSoundEffectType);
    }

    /**
     * 攻击方法
     * @param senderID 攻击者ID
     * @param x 攻击者x轴坐标
     * @param y 攻击者y轴坐标
     * @param aimX 瞄准坐标x
     * @param aimY 瞄准坐标y
     * @return 是否成功攻击
     */
    @Override
    public boolean attack(int senderID, int x, int y, int aimX, int aimY) {
        if(!super.attack(senderID, x, y, aimX, aimY)) return false;
        Bullet newBullet = super.getNewBullet();
        int dx = aimX - x;
        int dy = aimY - y;
        int len = (int)Math.sqrt(dx * dx + dy * dy);

        //System.out.println("dx=" + dx + ", dy=" + dy + ", len=" + len);
        newBullet.getHitbox().setVelocity(
                MyTool.ceilToInt(1.0 * super.getVelocity() * dx / len),
                MyTool.ceilToInt(1.0 * super.getVelocity() * dy / len));

        newBullet.setDamage(super.getDamage());
        newBullet.getHitbox().setCoordinate(x, y);
        newBullet.setSenderID(senderID);
        EntityManager.getInstance().add(newBullet, new BulletFactoryMessage(BulletFactory.PISTOL));
        return true;
    }
}
