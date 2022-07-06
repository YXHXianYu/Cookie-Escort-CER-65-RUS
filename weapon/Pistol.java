package weapon;

import common.Texture;
import entity.Bullet;
import manager.EntityManager;
import tool.MyTool;

import java.io.File;

/**
 * @author YXH_XianYu
 * Created On 2022-07-03
 */
public class Pistol extends Weapon {

    public Pistol(Bullet bullet, int velocity, int damage, int interval, Texture t0, Texture t1, Texture t2, Texture t3, File shootSoundEffect) {
        super(bullet, velocity, damage, interval, t0, t1, t2, t3, shootSoundEffect);
    }

    @Override
    public void attack(int senderID, int x, int y, int aimX, int aimY) {
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
        EntityManager.getInstance().add(newBullet);
    }
}
