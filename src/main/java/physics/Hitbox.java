package physics;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 碰撞箱类
 * 该类用于储存位置、速度信息，并可以检测两个物体是否碰撞。
 *
 * 这个简单2D物理引擎中，所有碰撞箱都是矩形。
 *
 * 添加了小数速度vxf和vyf，这两个数值决定vx和vy。
 * 在每次更改速度时要确保同时修改vxf和vyf。
 * 在setVelocityTarget()中，vxf和vyf决定了vx和vy的数值
 */
public class Hitbox {
    /**
     * x轴坐标（行为x轴，列为y轴）
     */
    private int x;

    /**
     * y轴坐标
     */
    private int y;

    /**
     * x轴速度(velocity x)
     */
    private int vx;

    /**
     * y轴速度(velocity y)
     */
    private int vy;

    /**
     * 矩形Hitbox x轴方向长度的一半
     */
    private int lx;

    /**
     * 矩形Hitbox y轴方向长度的一半
     */
    private int ly;

    /**
     * 高精度速度 vx
     * （服务于小数加速度）
     */
    private float vxf;

    /**
     * 高精度速度 vy
     * （服务于小数加速度）
     */
    private float vyf;

    /**
     * 构造一个存在初始速度的Hitbox
     * @param x x轴坐标
     * @param y y轴坐标
     * @param vx 初始x轴速度
     * @param vy 初始y轴速度
     * @param lx Hitbox x轴方向长度的一半
     * @param ly Hitbox y轴方向长度的一半
     */
    public Hitbox(int x, int y, int vx, int vy, int lx, int ly) {
        this.x = x;
        this.y = y;
        this.vxf = this.vx = vx;
        this.vyf = this.vy = vy;
        this.lx = lx;
        this.ly = ly;
    }

    /**
     * 构造一个存在初始速度的、正方形的Hitbox
     * @param x x轴坐标
     * @param y y轴坐标
     * @param vx 初始x轴速度
     * @param vy 初始y轴速度
     * @param d Hitbox边长的一半
     */
    public Hitbox(int x, int y, int vx, int vy, int d) {
        this(x, y, vx, vy, d, d);
    }

    /**
     * 构造一个初始速度为0的Hitbox
     * @param x x轴坐标
     * @param y y轴坐标
     * @param lx Hitbox x轴方向长度的一半
     * @param ly Hitbox y轴方向长度的一半
     */
    public Hitbox(int x, int y, int lx, int ly) {
        this(x, y, 0, 0, lx, ly);
    }

    /**
     * 构造一个初始速度为0的Hitbox
     * @param x x轴坐标
     * @param y y轴坐标
     * @param d Hitbox边长的一半
     */
    public Hitbox(int x, int y, int d) {
        this(x, y, 0, 0, d, d);
    }

    /**
     * deep copy constructor
     * @param another another Object
     */
    public Hitbox(Hitbox another) {
        this(another.getX(), another.getY(), another.getVx(), another.getVy(), another.getLx(), another.getLy());
    }

    /**
     * 获取x轴坐标
     * @return x轴坐标
     */
    public int getX() {
        return x;
    }

    /**
     * 获取y轴坐标
     * @return y轴坐标
     */
    public int getY() {
        return y;
    }

    /**
     * 获取x轴速度
     * @return x轴速度
     */
    public int getVx() {
        return vx;
    }

    /**
     * 获取y轴速度
     * @return y轴速度
     */
    public int getVy() {
        return vy;
    }

    /**
     * 获取hitbox边长的一半
     * @return hitbox边长的一半
     */
    public int getD() {
        if(lx == ly) return lx;
        else return -1;
    }

    /**
     * 获取hitbox x长
     * @return hitbox x长
     */
    public int getLx() {
        return lx;
    }

    /**
     * 获取hitbox y长
     * @return hitbox y长
     */
    public int getLy() {
        return ly;
    }

    /**
     * 设置hitbox的坐标
     * @param x x轴坐标
     * @param y y轴坐标
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 设置hitbox的速度
     * @param vx x轴速度
     * @param vy y轴速度
     */
    public void setVelocity(int vx, int vy) {
        this.vxf = this.vx = vx;
        this.vyf = this.vy = vy;
    }

    /**
     * 通过加速度实现调整Hitbox的速度
     * 事实上这套加速度体系可以实现类似CSGO中急停的效果，按反向按键可以加快位移
     * @param vx 目标x轴速度
     * @param vy 目标y轴速度
     * @param a 加速度
     */
    public void setTargetVelocity(int vx, int vy, float a) {
        // vxf
        if(this.vxf * vx < 0) this.vxf = 0;
        if(this.vxf > vx) this.vxf = Math.max(this.vxf - a, vx);
        else if(this.vxf < vx) this.vxf = Math.min(this.vxf + a, vx);
        // vyf
        if(this.vyf * vy < 0) this.vyf = 0;
        if(this.vyf > vy) this.vyf = Math.max(this.vyf - a, vy);
        else if(this.vyf < vy) this.vyf = Math.min(this.vyf + a, vy);
        // vx&vy
        this.vx = (int)vxf;
        this.vy = (int)vyf;
    }

    /**
     * 判断该Hitbox是否和另一个Hitbox相碰撞
     * @param another 另一个Hitbox
     * @return 碰撞返回true; 相离返回false
     */
    public boolean isHit(Hitbox another) {
        if(Math.abs(this.x - another.getX()) >= this.lx + another.getLx()) return false;
        if(Math.abs(this.y - another.getY()) >= this.ly + another.getLy()) return false;
        return true;
    }

    /**
     * 判断该Hitbox是否在移动
     * @return 若vx == vy == 0，则返回true；否则返回false
     */
    public boolean isMoving() {
        return (vxf == 0 && vyf == 0);
    }

    /**
     * 获取下一帧的Hitbox
     * @return 下一帧的Hitbox
     */
    public Hitbox nextFrameHitbox() {
        return new Hitbox(x + vx, y + vy, vx, vy, lx, ly);
    }
}