package common;

import tool.MyTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 纹理类
 * 该类用于储存游戏中所有的图像信息。
 *
 * 该类会记录图像本身、图像的x轴长度和y轴长度、图像中心点相对图像左上角的偏移量。
 *
 * 为了实现自适应分辨率，所以Texture缓存了当前scale下的贴图等信息，加速运算。
 * （如果不缓存的话，因为每次都要裁剪大量贴图，所以游戏会变得巨卡）
 */
public class Texture {
    /**
     * 图像
     */
    private BufferedImage image;
    private BufferedImage scaledImage;

    /**
     * x轴方向的长度（高、height）
     */
    private int lx;
    private int scaledLx;

    /**
     * y轴方向的长度（宽、width）
     */
    private int ly;
    private int scaledLy;

    /**
     * x轴方向偏移量
     */
    private int dx;
    private int scaledDx;

    /**
     * y轴方向偏移量
     */
    private int dy;
    private int scaledDy;

    /**
     * 整体缩放比例（默认=-1）
     */
    private float scale;

    /**
     * 构造一个纹理
     * @param image 纹理图像
     * @param lx 纹理x轴方向长度
     * @param ly 纹理y轴方向长度
     * @param dx 纹理x轴方向偏移量
     * @param dy 纹理y轴方向偏移量
     */
    public Texture(BufferedImage image, int lx, int ly, int dx, int dy) {
        this.image = image;
        this.lx = lx;
        this.ly = ly;
        this.dx = dx;
        this.dy = dy;
        this.scale = -1;
        setScale(1.0f);
    }

    /**
     * 通过文件路径构造一个纹理
     * @param imagePath 纹理图像路径
     * @param lx 纹理x轴方向长度
     * @param ly 纹理y轴方向长度
     * @param dx 纹理x轴方向偏移量
     * @param dy 纹理y轴方向偏移量
     * @throws IOException 读取图片异常
     */
    public Texture(String imagePath, int lx, int ly, int dx, int dy) throws IOException {
        this.image = ImageIO.read(new File(imagePath));
        this.lx = lx;
        this.ly = ly;
        this.dx = dx;
        this.dy = dy;
        this.scale = -1;
        setScale(1.0f);
    }

    /**
     * deep copy constructor
     * @param another another constructor
     */
    public Texture(Texture another) {
        // 原值拷贝，不拷贝缩放后的内容
        this(MyTool.copyBufferedImage(another.image), another.lx, another.ly, another.dx, another.dy);
        this.setScale(another.scale);
    }

    /**
     * 设置缩放比例（强制刷新比例）
     * @param scale 比例
     */
    public void flushScale(float scale) {
        this.scale = scale;
        scaledLx = (int)(scale * lx);
        scaledLy = (int)(scale * ly);
        scaledDx = (int)(scale * dx);
        scaledDy = (int)(scale * dy);
        scaledImage = MyTool.scale(image, scale);
    }

    /**
     * 设置缩放比例
     * @param scale 比例
     * @return 如果新的比例不同，纹理被更新，则返回true；否则返回false
     */
    public boolean setScale(float scale) {
        if(this.scale == scale) return false;
        flushScale(scale);
        return true;
    }

    /**
     * 获取纹理图像（无视缩放比例）
     */
    public BufferedImage getRawImage() {
        return image;
    }

    /**
     * 获取纹理图像（考虑缩放比例）
     */
    public BufferedImage getImage() {
        if(scale < 0)
            return image;
        else {
            return scaledImage;
        }
    }

    /**
     * （旧方法）
     * TODO 将裁剪方案替换为BufferedImage方案，并比较效率
     * 返回裁剪后的贴图
     * 注意：此方法运算量消耗巨大，不可以每个游戏帧都调用此方法。
     * @param x 游戏x轴
     * @param y 游戏y轴
     * @param lx x轴长度
     * @param ly y轴长度
     * @return 裁剪后的图片
     */
    public BufferedImage getCutImage(int x, int y, int lx, int ly) {
        // 注意，参数是游戏xy轴，方法内是swing xy轴
        CropImageFilter filter = new CropImageFilter(y, x, ly, lx);
        return MyTool.toBufferedImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(this.getImage().getSource(), filter)));
    }

    /**
     * （旧方法）
     * TODO 将裁剪方案替换为BufferedImage方案，并比较效率
     * 返回裁剪后的纹理（主要用于预处理精灵图）
     * 注意：尚未测试过是否可以在缩放的情况下正确裁剪纹理！！！
     * 注意：此方法运算量消耗巨大，不可以每个游戏帧都调用此方法。
     * @param x 游戏x轴
     * @param y 游戏y轴
     * @param lx x轴长度 & 裁剪后x轴长度
     * @param ly y轴长度 & 裁剪后x轴长度
     * @param dx 裁剪后x轴偏移量
     * @param dy 裁剪后y轴偏移量
     * @return 裁剪后的纹理
     */
    public Texture getCutTexture(int x, int y, int lx, int ly, int dx, int dy) {
        CropImageFilter filter = new CropImageFilter(y, x, ly, lx);
        return new Texture(MyTool.toBufferedImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(this.getImage().getSource(), filter))), lx, ly, dx, dy);
    }

    /**
     * 获取x轴方向长度
     */
    public int getLx() {
        return scale < 0 ? lx : scaledLx;
    }

    /**
     * 获取y轴方向长度
     */
    public int getLy() {
        return scale < 0 ? ly : scaledLy;
    }

    /**
     * 获取x轴偏移量
     */
    public int getDx() {
        return scale < 0 ? dx : scaledDx;
    }

    /**
     * 获取y轴偏移量
     */
    public int getDy() {
        return scale < 0 ? dy : scaledDy;
    }
}
