package entity;

import common.Texture;
import physics.Hitbox;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 障碍物类
 * 该类用于实现一种无法移动的实体（障碍物）
 */
public class Obstacle extends Entity {
    /**
     * 构造一个障碍物
     * @param hitbox 碰撞箱
     * @param texture 贴图
     * @param hp 障碍物血量
     */
    public Obstacle(Hitbox hitbox, Texture texture, int hp) {
        super(hitbox, texture, hp);
    }
}
