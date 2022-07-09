package factory;

import common.Texture;
import entity.Obstacle;
import physics.Hitbox;

import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-03
 *
 * 障碍物工厂类
 */
public class ObstacleFactory {

    /**
     * 障碍物编号：空气墙 - 0
     */
    public static final int AIR_WALL = 0;

    /**
     * 障碍物编号：石头 - 1
     */
    public static final int STONE = 1;

    /**
     * 障碍物编号：混凝土 - 2
     */
    public static final int CONCRETE = 2;

    /**
     * 无限血量
     * 不会有人无限血量真的写了个if吧不会吧不会吧
     */
    private static final int INFINITE_HP = 10000;

    /**
     * 石头血量
     */
    private static final int STONE_HP = 2;

    /**
     * 混凝土血量
     */
    private static final int CONCRETE_HP = 3;

    /**
     * 获取障碍物
     * @param type 障碍物类型
     * @param x 障碍物x轴坐标
     * @param y 障碍物y轴坐标
     * @param lx 障碍物x轴方向长度
     * @param ly 障碍物y轴方向长度
     * @return 障碍物
     */
    public static Obstacle getObstacle(int type, int x, int y, int lx, int ly) {
        try {
            if(type == AIR_WALL) {
                if(lx == 0 && ly == 0) {
                    System.out.println("空气墙碰撞箱参数异常!");
                    return null;
                }
                return new Obstacle(
                        new Hitbox(x, y, lx, ly),
                        null,
                        INFINITE_HP
                );
            } else if(type == STONE) {
                return new Obstacle(
                        new Hitbox(x, y, 50),
                        new Texture("./pics/stone.png", 100, 100, 50, 50),
                        STONE_HP
                );
            } else if(type == CONCRETE) {
                return new Obstacle(
                        new Hitbox(x, y, 50),
                        new Texture("./pics/concrete.png", 200, 200, 132, 100),
                        CONCRETE_HP
                );
            }
        } catch (IOException e) {
            System.out.println("障碍物生成异常!");
            e.getStackTrace();
        }
        return null;
    }

    public static Obstacle getObstacle(int type, int x, int y) {
        return getObstacle(type, x, y, 0, 0);
    }
}
