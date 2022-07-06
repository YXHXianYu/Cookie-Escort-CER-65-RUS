package manager;

import common.Texture;
import entity.Bullet;
import entity.Character;
import entity.Entity;
import physics.Hitbox;

/**
 * @author YXH_XianYu
 * Created On 2022-06-30
 */
public class TestEntityManager {
    public static void main(String[] args) {

        Character character1 = new Character(new Hitbox(0, 0, 1, 1, 1), null, 1, 1, 1, null, null);
        Character character2 = new Character(new Hitbox(5, 5, 0, 0, 1), null, 1, 1, 1, null,null);
        Character character3 =  new Character(new Hitbox(10, 10, -1, -1, 1), null, 1, 1, 1, null, null);
        Bullet bullet = new Bullet(new Hitbox(5, 0, 0, 1, 1), null, 1);

        EntityManager.getInstance().add(character1);
        EntityManager.getInstance().add(character2);
        EntityManager.getInstance().add(character3);
        EntityManager.getInstance().add(bullet);

        for(int i = 1; i <= 10; i++) {
            EntityManager.getInstance().play();
            System.out.println(EntityManager.getInstance());
        }

    }
}
