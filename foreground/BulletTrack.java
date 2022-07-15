package foreground;

import manager.RenderManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-13
 *
 * 子弹线
 * @deprecated 前景层产能不足，无法实现
 */
@Deprecated
public class BulletTrack implements ForegroundObject {
    /**
     * 起始点x轴坐标
     */
    private final int startX;
    /**
     * 起始点y轴坐标
     */
    private final int startY;
    /**
     * 终止点x轴坐标
     */
    private final int endX;
    /**
     * 终止点y轴坐标
     */
    private final int endY;

    /**
     * 枪线
     */
    private Image bulletTrack;

    /**
     * 颜色
     */
    private final Color color;

    /**
     * 透明度不变阶段计数器
     */
    private int cnt;

    /**
     * 透明度不变阶段计数器
     */
    private final int cntLim;


    /**
     * 构造方法
     */
    public BulletTrack(int startX, int startY, int endX, int endY, Color color, int cntLim) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.cnt = this.cntLim = cntLim;

        try {
            bulletTrack = ImageIO.read(RenderManager.class.getClassLoader().getResource("pics/bullet_track.png"));
        } catch (IOException e) {
            e.printStackTrace();
            cnt = 0;
            return;
        }

    }
}
