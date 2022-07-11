package weapon;

import common.Texture;
import entity.Bullet;
import factory.BulletFactory;
import factory.entityFactoryMessage.BulletFactoryMessage;
import manager.EntityManager;
import tool.MyTool;

import java.io.File;

/**
 * @author YXH_XianYu
 * Created On 2022-07-03
 */
public class Pistol extends Weapon {

    public Pistol(Bullet bullet, int velocity, int damage, int interval, Texture t0, Texture t1, Texture t2, Texture t3, int shootSoundEffectType) {
        super(bullet, velocity, damage, interval, t0, t1, t2, t3, shootSoundEffectType);
    }

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