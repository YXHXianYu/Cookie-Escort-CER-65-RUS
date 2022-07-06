package tool;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

/**
 * @author YXH_XianYu
 * Created On 2022-07-02
 *
 * 工具类
 */
public class MyTool {
    /**
     * 方向表格，将(x, y)输入转换为方向编号
     */
    public static final int[][] directTable = new int[][] {
            {4, 5, 6,},
            {3, 0, 7,},
            {2, 1, 8,},
    };

    /**
     * 向上取整并输出int类型变量
     * （和Math.ceil()类似，不过输出类型不同）
     */
    public static int ceilToInt(double x) {
        return (int)Math.ceil(x);
    }

    /**
     * 输出音频
     * @param file 音频文件
     */
    public static void playSound(File file) {
        try {
            Clip clip = AudioSystem.getClip();
            // getAudioInputStream() also accepts a File or InputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            System.out.println("音频播放错误");
            e.getStackTrace();
        }
    }

    /**
     * 将一个按钮变为全透明
     * @param button 按钮
     */
    public static void setButtonTransparent(JButton button, boolean focusPainted) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(focusPainted);
    }

    /**
     * 顺时针旋转图片
     * （注意图片裁剪问题，旋转后图片可能会超出原有边界）
     * @param imgOld 旧图片
     * @param deg 旋转角度
     * @return 新图片
     */
    public static BufferedImage rotate(BufferedImage imgOld, int deg){                                                 //parameter same as method above
        BufferedImage imgNew = new BufferedImage(imgOld.getWidth(), imgOld.getHeight(), imgOld.getType());              //create new buffered image
        Graphics2D g = (Graphics2D) imgNew.getGraphics();                                                               //create new graphics
        g.rotate(Math.toRadians(deg), imgOld.getWidth()/2, imgOld.getHeight()/2);                                    //configure rotation
        g.drawImage(imgOld, 0, 0, null);                                                                                //draw rotated image
        return imgNew;                                                                                                  //return rotated image
    }

    /**
     * 缩放图片
     * @param imgOld 旧图片
     * @param scale 缩放比例
     * @return 新图片
     */
    public static BufferedImage scale(BufferedImage imgOld, float scale) {
        BufferedImage before = imgOld;
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(before, after);
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /**
     * copy a buffered image
     * @param img the image to be copied
     * @return the copied image
     */
    public static BufferedImage copyBufferedImage(BufferedImage img) {
        if(img == null) return null;
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /**
     * 获取一个新的BufferedImage
     * 新的BufferedImage由A和B叠放而成
     * A在下，B在上
     * @param A BufferedImage A
     * @param B BufferedImage B
     * @return BufferedImage A+B
     */
    public static BufferedImage combineTwoBufferedImage(BufferedImage A, BufferedImage B) {
        BufferedImage C = copyBufferedImage(A);
        C.getGraphics().drawImage(B, 0, 0, null);
        return C;
    }

}
