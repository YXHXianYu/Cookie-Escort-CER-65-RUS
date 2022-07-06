package network.pack;

import java.io.Serializable;

/**
 * @author YXH_XianYu
 * Created On 2022-07-04
 *
 * 操作包类
 *
 * 服务器进行逻辑处理，客户端进行渲染画面。
 * 玩家操作序列从客户端发给服务端；
 * 逻辑处理的结果从服务端发给客户端；
 */
public class Control implements Serializable {
    /**
     * 瞄准位置的x轴
     */
    private int aimX;

    /**
     * 瞄准位置的y轴
     */
    private int aimY;

    /**
     * 攻击指令
     */
    private boolean attack;

    /**
     * 冲刺指令
     */
    private boolean rush;

    /**
     * 移动方向
     */
    private int moveDirect;

    /**
     * 获取瞄准位置x轴
     * @return 瞄准位置x轴
     */
    public int getAimX() {
        return aimX;
    }

    /**
     * 设置瞄准位置x轴
     * @param aimX 瞄准位置x轴
     */
    public void setAimX(int aimX) {
        this.aimX = aimX;
    }

    /**
     * 获取瞄准位置y轴
     * @return 瞄准位置y轴
     */
    public int getAimY() {
        return aimY;
    }

    /**
     * 设置瞄准位置y轴
     * @param aimY 瞄准位置y轴
     */
    public void setAimY(int aimY) {
        this.aimY = aimY;
    }

    /**
     * 获取是否攻击
     * @return 是否攻击
     */
    public boolean isAttack() {
        return attack;
    }

    /**
     * 设置是否攻击状态
     * @param attack 是否攻击
     */
    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    /**
     * 获取是否冲刺
     * @return 是否冲刺
     */
    public boolean isRush() {
        return rush;
    }

    /**
     * 设置是否冲刺
     * @param rush 是否冲刺
     */
    public void setRush(boolean rush) {
        this.rush = rush;
    }

    /**
     * 获取移动方向
     * @return 移动方向
     */
    public int getMoveDirect() {
        return moveDirect;
    }

    /**
     * 设置移动方向
     * @param moveDirect 移动方向
     */
    public void setMoveDirect(int moveDirect) {
        this.moveDirect = moveDirect;
    }
}
