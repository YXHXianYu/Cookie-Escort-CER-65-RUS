package factory;

import common.Texture;
import entity.Foreground;
import entity.Obstacle;
import org.apache.poi.ss.formula.functions.T;
import physics.Hitbox;

import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-13
 */
public class ForegroundFactory {

    /**
     * 障碍物编号：空气墙 - 0
     */
    public static final int MAP_FOREGROUND = 0;


    /**
     * 获取前景
     * @param type 前景类型
     * @param x 前景x轴坐标
     * @param y 前景y轴坐标
     * @return 前景
     */
    public static Foreground getForeground(int type, int x, int y) {
        try {
            if(type == MAP_FOREGROUND) {
                return new Foreground(
                        new Hitbox(x, y, 0, 0),
                        new Texture("pics/hook.png", 1080, 1920, 540, 960),
                        100000000
                );
            }
        } catch (IOException e) {
            System.out.println("障碍物生成异常!");
            e.getStackTrace();
        }
        return null;
    }
}
