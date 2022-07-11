package entity;

import common.MoveAnimation;
import common.Texture;
import network.ClientManager;
import physics.Hitbox;
import manager.GameManager;
import tool.MyTool;
import weapon.Weapon;

import java.awt.image.BufferedImage;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 角色类
 * 该类实现了一种能主动移动、能攻击的实体（玩家、怪物）
 */
public class Character extends Entity {

    /**
     * 最大血量
     */
    private int maxHp;

    /**
     * 攻击方式
     */
    private Weapon weapon;

    /**
     * 角色的移动动画
     */
    private MoveAnimation moveAnimation;

    /**
     * 角色的移动方向（由控制器控制）
     * 值域[0, 8]，0为静止，1为正下方，1~8按顺时针顺序排列。
     */
    private int direct;

    /**
     * 角色的最大移动速度
     */
    private int velocity;

    /**
     * 角色控制器
     */
    protected Controller controller;

    /**
     * 瞄准位置(x)
     */
    private int aimX;

    /**
     * 瞄准位置(y)
     */
    private int aimY;

    /**
     * 射击CD计数器
     */
    private int attackCnt;

    /**
     * 剩余冲刺次数
     * 冲刺：以目前5倍速度向鼠标所指位置冲刺
     */
    private int rushTimes = 0;

    /**
     * 冲刺计时器
     */
    private int rushCnt = 0;

    /**
     * 冲刺时间长度
     */
    private final int rushCntLength = 10;

    /**
     * 构造一个带有控制器的角色
     * @param hitbox 碰撞箱
     * @param texture 贴图
     * @param hp 血量
     * @param maxHp 最大血量
     * @param weapon 武器（攻击方式）
     * @param moveAnimation 移动动画
     * @param controller 控制器
     */
    public Character(Hitbox hitbox, Texture texture, int hp, int maxHp, int velocity, Weapon weapon, MoveAnimation moveAnimation, Controller controller) {
        super(hitbox, texture, hp);
        this.maxHp = maxHp;
        this.velocity = velocity;
        this.weapon = weapon;
        this.moveAnimation = moveAnimation;
        this.controller = controller;
    }

    /**
     * 构造一个无控制器的角色
     * @param hitbox 碰撞箱
     * @param texture 贴图
     * @param hp 血量
     * @param maxHp 最大血量
     * @param weapon 武器（攻击方式）
     * @param moveAnimation 移动动画
     */
    public Character(Hitbox hitbox, Texture texture, int hp, int maxHp, int velocity, Weapon weapon, MoveAnimation moveAnimation) {
        this(hitbox, texture, hp, maxHp, velocity, weapon, moveAnimation, null);
    }

    /**
     * 控制角色移动
     * @param direct 移动方向
     * @throws IllegalArgumentException 参数
     */
    public void move(int direct) throws IllegalArgumentException {
        if(direct < 0 || direct > 8)
            throw new IllegalArgumentException("move() argument exception.");
        this.direct = direct;
    }

    /**
     * 角色控制
     */
    public void play() {

        // controller
        if(controller != null)
            controller.control(this);

        // move flush
        if(rushCnt <= 1) {
            float a;
            if(rushCnt == 0) {
                a = (float)(3600.0 / GameManager.getInstance().getFps() / GameManager.getInstance().getFps());
            } else {
                rushCnt = 0;
                a = (float)114514; // inf
            }
            int v2 = (int)(velocity / Math.sqrt(2));
            switch (direct) {
                case 0: {getHitbox().setTargetVelocity(0, 0, a); break;}

                case 1: {getHitbox().setTargetVelocity(velocity, 0, a); break;}
                case 2: {getHitbox().setTargetVelocity(v2, -v2, a); break;}
                case 3: {getHitbox().setTargetVelocity(0, -velocity, a); break;}
                case 4: {getHitbox().setTargetVelocity(-v2, -v2, a); break;}

                case 5: {getHitbox().setTargetVelocity(-velocity, 0, a); break;}
                case 6: {getHitbox().setTargetVelocity(-v2, v2, a); break;}
                case 7: {getHitbox().setTargetVelocity(0, velocity, a); break;}
                case 8: {getHitbox().setTargetVelocity(v2, v2, a); break;}
            }
        } else {
            rushCnt--;
        }

    }

    /**
     * 角色攻击
     * 不需要持续控制。
     */
    public void attack() {
        if(weapon.attack(getID(), getHitbox().getX(), getHitbox().getY(), aimX, aimY))
            MyTool.playSound(weapon.getShootSoundEffect());
    }

    /**
     * 角色瞄准
     * @param aim_x 目标x轴坐标
     * @param aim_y 目标y轴坐标
     */
    public void aim(int aim_x, int aim_y) {
        this.aimX = aim_x;
        this.aimY = aim_y;
    }

    /**
     * 设置武器
     * @param weapon 武器
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * 设置控制器
     * @param controller 控制器
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * 获取当前角色贴图
     * 贴图方向根据速度决定，而不根据按键决定！
     * @return 贴图
     */
    public Texture getCurrentTexture() {
        Texture character;
        int vx = getHitbox().getVx();
        int vy = getHitbox().getVy();
        if(vx == 0 && vy == 0) character = getTexture();
        else if(vx > 0) character = moveAnimation.play(0, GameManager.getInstance().getTimeStamp());
        else if(vx < 0) character = moveAnimation.play(1, GameManager.getInstance().getTimeStamp());
        else if(vy < 0) character = moveAnimation.play(2, GameManager.getInstance().getTimeStamp());
        else character = moveAnimation.play(3, GameManager.getInstance().getTimeStamp());

        if(weapon != null) {
            BufferedImage image;
            float dy = ((ClientManager.getInstance().getControl() == null) ? aimY : ClientManager.getInstance().getControl().getAimY()) - getHitbox().getY();
            float dx = ((ClientManager.getInstance().getControl() == null) ? aimX : ClientManager.getInstance().getControl().getAimX()) - getHitbox().getX();
            int index = 0;
            if(dx == 0) { // weapon in the front of character
                if(dy <= 0)
                    return new Texture(MyTool.combineTwoBufferedImage(character.getImage(), weapon.getTexture(index).getImage()), 150, 120, 100, 60);
                else
                    return new Texture(MyTool.combineTwoBufferedImage(character.getImage(), weapon.getTexture(index + 1).getImage()), 150, 120, 100, 60);
            } else {
                int deg = 90;
                if(dx * dy > 0) deg = (int)(90 - Math.atan(dy / dx) * 180 / Math.PI);
                else deg = -(int)(90 - Math.atan(-dy / dx) * 180 / Math.PI);

                if(dy <= 0)
                    image = MyTool.rotate(weapon.getTexture(index).getImage(), deg);
                else
                    image = MyTool.rotate(weapon.getTexture(index + 1).getImage(), deg);

                if(dx > 0) { // weapon in the front of character
                    return new Texture(MyTool.combineTwoBufferedImage(character.getImage(), image), 150, 120, 100, 60);
                } else if(dx < 0) { // weapon in the back of character
                    return new Texture(MyTool.combineTwoBufferedImage(image, character.getImage()), 150, 120, 100, 60);
                }
            }
        }
        return character;
    }

    /**
     * 获取冲刺剩余次数
     * @return 冲刺剩余次数
     */
    public int getRushTimes() {
        return rushTimes;
    }

    /**
     * 增加冲刺剩余次数
     * @param v 增加量
     */
    public void addRushTimes(int v) {
        this.rushTimes += v;
    }

    /**
     * 设置冲刺剩余次数
     * @param rushTimes 冲刺剩余次数
     */
    public void setRushTimes(int rushTimes) {
        this.rushTimes = rushTimes;
    }

    public void rush() {
        if(rushCnt > 0) return;
        if(this.rushTimes <= 0) return;
        this.rushTimes -= 1;

        System.out.println("Rushed");
        rushCnt = rushCntLength;
        int dx = aimX - getHitbox().getX();
        int dy = aimY - getHitbox().getY();
        double len = Math.sqrt(dx * dx + dy * dy);
        getHitbox().setVelocity(MyTool.ceilToInt(5.0 * velocity * dx / len), MyTool.ceilToInt(5.0 * velocity * dy / len));
    }
}
