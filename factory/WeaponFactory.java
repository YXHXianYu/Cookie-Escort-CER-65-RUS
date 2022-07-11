package factory;

import common.Texture;
import entity.Bullet;
import manager.GameManager;
import physics.Hitbox;
import weapon.Pistol;
import weapon.Weapon;

import java.io.File;
import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-02
 *
 * 武器工厂类
 */
public class WeaponFactory {
    /**
     * 武器编号：手枪 - 0
     */
    public static final int PISTOL = 0;

    /**
     * 武器编号：左轮 - 1
     */
    public static final int REVOLVER = 1;

    /**
     * 武器编号：喷子 - 2
     */
    public static final int SHOTGUN = 2;

    /**
     * 武器编号：步枪(冲锋枪) - 4
     */
    public static final int RIFLE = 3;

    /**
     * 武器编号：狙击枪 - 0
     */
    public static final int SNIPER = 4;

    /**
     * 获取武器原型的一份复制
     * @param type 武器编号(常量)
     * @return 武器原型的一份复制
     */
    public static Weapon getWeapon(int type) {
        try {
            if(type == PISTOL) {
                Bullet bullet = BulletFactory.getBullet(BulletFactory.PISTOL);
                Texture texture = new Texture("./pics/weapon/pistol.png", 0, 0, 0, 0);
                Weapon weapon = new Pistol(
                        bullet, 1440 / GameManager.getInstance().getFps(), 1, 14400 / GameManager.getInstance().getFps(),
                        texture.getCutTexture(0, 0, 150, 120, 100, 60),
                        texture.getCutTexture(0, 121, 150, 120, 100, 60),
                        texture.getCutTexture(0, 241, 150, 120, 100, 60),
                        texture.getCutTexture(0, 361, 150, 120, 100, 60),
                        BulletFactory.PISTOL
                );
                weapon.setTag("Pistol");
                return weapon;
            } else if(type == REVOLVER) {

            }
        } catch (IOException e) {
            System.out.println("武器生成时异常!");
            e.getStackTrace();
        }
        return null;
    }

}
