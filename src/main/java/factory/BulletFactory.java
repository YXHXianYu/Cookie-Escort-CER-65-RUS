package factory;

import common.Texture;
import entity.Bullet;
import manager.GameManager;
import org.jetbrains.annotations.Nullable;
import physics.Hitbox;
import tool.MyTool;
import weapon.Pistol;
import weapon.Weapon;

import java.io.File;
import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-10
 */
public class BulletFactory {
    /**
     * 子弹编号：手枪 - 0
     */
    public static final int PISTOL = 0;

    /**
     * 获取子弹对象
     * @param type 类型
     * @return 子弹
     */
    public static @Nullable Bullet getBullet(int type) {
        try {
            if(type == PISTOL) {
                return new Bullet(new Hitbox(0, 0, 0, 0, 5), new Texture("./pics/weapon/pistol_bullet.png", 10, 10, 5, 5), 1);
            }
        } catch (IOException e) {
            System.out.println("武器生成时异常!");
            e.getStackTrace();
        }
        return null;
    }

    /**
     * 获取音效
     */
    public static File getSoundEffect(int type) {
        if(type == PISTOL) {
            return new File("./sound/pistol.wav");
        } else {
            return null;
        }
    }

}
