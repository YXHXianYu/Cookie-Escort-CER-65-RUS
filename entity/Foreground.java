package entity;

import common.Texture;
import physics.Hitbox;

/**
 * @author YXH_XianYu
 * Created On 2022-07-13
 *
 * 前景层
 * （本实体只支持在单人游戏中发挥作用）
 * （别问，问就是产能不足）
 */
public class Foreground extends Entity {
    /**
     * 前景构造方法
     * @param hitbox
     * @param texture
     * @param hp
     */
    public Foreground(Hitbox hitbox, Texture texture, int hp) {
        super(hitbox, texture, hp);
    }
}
