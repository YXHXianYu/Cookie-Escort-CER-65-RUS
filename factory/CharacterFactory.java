package factory;

import common.MoveAnimation;
import common.Texture;
import entity.Character;
import entity.Controller;
import manager.GameManager;
import physics.Hitbox;

import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-03
 *
 * 角色工厂类
 */
public class CharacterFactory {
    /**
     * 角色编号：测试角色 - 0
     */
    public static final int TEST_CHARACTER = 0;

    /**
     * 角色编号：Number10 - 1
     */
    public static final int NUMBER_10 = 1;

    /**
     * 角色编号：Number17 - 2
     */
    public static final int NUMBER_17 = 2;

    /**
     * 角色编号：ROBOT - 3
     */
    public static final int ROBOT = 3;

    /**
     * 获取角色
     * @param type 类型
     * @return 对应类型角色
     */
    public static Character getCharacter(int type, int x, int y, int hp) {
        try {
            if(type == TEST_CHARACTER) {
                Texture texture_char1_rest = new Texture("pics/char1/char1_rest.png", 150, 120, 100, 60);
                Texture[][] textures_char_moveAnimation = new Texture[4][12];
                Texture texture_tmp;
                texture_tmp = new Texture("pics/char1/char1_down.png", 300, 600, 0, 0);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[0][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[0][5 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 1; i++) textures_char_moveAnimation[0][10 + i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/char1/char1_up.png", 300, 600, 0, 0);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[1][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[1][5 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 1; i++) textures_char_moveAnimation[1][10 + i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/char1/char1_left.png", 300, 600, 0, 0);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[2][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[2][5 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 1; i++) textures_char_moveAnimation[2][10 + i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/char1/char1_right.png", 300, 600, 0, 0);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[3][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 4; i++) textures_char_moveAnimation[3][5 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 1; i++) textures_char_moveAnimation[3][10 + i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                return new Character(
                        new Hitbox(x, y, 0, 0, 50), texture_char1_rest, hp, hp, 720 / GameManager.getInstance().getFps(),
                        null,
                        new MoveAnimation(textures_char_moveAnimation)
                );
            } else if(type == NUMBER_10) {
                Texture texture_rest = new Texture("pics/Number10/rest.png", 150, 120, 100, 60);
                Texture[][] textures_moveAnimation = new Texture[4][12];
                Texture texture_tmp;
                texture_tmp = new Texture("pics/Number10/down.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number10/up.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number10/left.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number10/right.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                return new Character(
                        new Hitbox(x, y, 0, 0, 50), texture_rest, hp, hp, 720 / GameManager.getInstance().getFps(),
                        WeaponFactory.getWeapon(WeaponFactory.PISTOL),
                        new MoveAnimation(textures_moveAnimation)
                );
            } else if(type == NUMBER_17) {
                Texture texture_rest = new Texture("pics/Number17/rest.png", 150, 120, 100, 60);
                Texture[][] textures_moveAnimation = new Texture[4][12];
                Texture texture_tmp;
                texture_tmp = new Texture("pics/Number17/down.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number17/up.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number17/left.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Number17/right.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                return new Character(
                        new Hitbox(x, y, 0, 0, 50), texture_rest, hp, hp, 720 / GameManager.getInstance().getFps(),
                        WeaponFactory.getWeapon(WeaponFactory.AX50),
                        new MoveAnimation(textures_moveAnimation)
                );
            } else if(type == ROBOT) {
                Texture texture_rest = new Texture("pics/Bot/rest.png", 150, 120, 100, 60);
                Texture[][] textures_moveAnimation = new Texture[4][12];
                Texture texture_tmp;
                texture_tmp = new Texture("pics/Bot/down.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[0][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Bot/up.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[1][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Bot/left.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[2][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                texture_tmp = new Texture("pics/Bot/right.png", 300, 600, 0, 0);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][i] = texture_tmp.getCutTexture(0, i * 120, 150, 120, 100, 60);
                for(int i = 0; i <= 5; i++) textures_moveAnimation[3][6 + i] = texture_tmp.getCutTexture(150, i * 120, 150, 120, 100, 60);
                return new Character(
                        new Hitbox(x, y, 0, 0, 50), texture_rest, hp, hp, 240 / GameManager.getInstance().getFps(),
                        null,
                        new MoveAnimation(textures_moveAnimation)
                );
            }
        } catch (IOException e) {
            System.out.println("新建角色时文件读取异常!");
            e.getStackTrace();
        }
        System.out.println("新建角色失败!");
        return null;
    }

    /**
     * 生产一个附带控制器的角色
     * @param type 角色类型
     * @param controller 控制器对象
     * @return 角色
     */
    public static Character getCharacter(int type, int x, int y, int hp, Controller controller) {
        Character character = getCharacter(type, x, y, hp);
        if(character == null) return null;
        character.setController(controller);
        return character;
    }


}
