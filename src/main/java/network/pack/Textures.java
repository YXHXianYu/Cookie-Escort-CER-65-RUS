package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-04
 *
 * 纹理包
 *
 * 用作服务端向客户端的通信
 */
public class Textures {
    /**
     * 纹理包大小
     */
    private int n;

    /**
     * 图像数组
     */
    private common.Texture[] textures;

    /**
     * 实体所在x轴坐标
     */
    private int[] entityX;

    /**
     * 实体所在y轴坐标
     */
    private int[] entityY;

    /**
     * 服务端包发送时的时间戳
     */
    private long sendTimeMillis;

    /**
     * 构造纹理包
     * @param n 纹理包大小
     */
    public Textures(int n) {
        this.n = n;
        textures = new common.Texture[n];
        entityX = new int[n];
        entityY = new int[n];
    }

    /**
     * 获取纹理个数
     * @return 纹理个数
     */
    public int getN() {
        return n;
    }

    /**
     * 设置纹理个数
     * @param n 纹理个数
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * 获取纹理
     * @param index 纹理下标
     * @return 纹理
     */
    public common.Texture getTexture(int index) {
        return textures[index];
    }

    /**
     * 设置纹理
     * @param index 下表
     * @param texture 纹理
     */
    public void setTexture(int index, common.Texture texture) {
        textures[index] = texture;
    }

    /**
     * 获取实体x轴坐标
     * @param index 下标
     * @return 实体x轴坐标
     */
    public int getEntityX(int index) {
        return entityX[index];
    }

    /**
     * 设置实体x轴坐标
     * @param index 下表
     * @param value 坐标值
     */
    public void setEntityX(int index, int value) {
        entityX[index] = value;
    }

    /**
     * 获取实体y轴坐标
     * @param index 下标
     * @return y轴坐标
     */
    public int getEntityY(int index) {
        return entityY[index];
    }

    /**
     * 设置实体y轴坐标
     * @param index 下表
     * @param value 坐标值
     */
    public void setEntityY(int index, int value) {
        entityY[index] = value;
    }

    /**
     * 获取发送时间戳
     * @return 发送时间戳
     */
    public long getSendTimeMillis() {
        return sendTimeMillis;
    }

    /**
     * 设置发送时间戳
     * @param sendTimeMillis 发送时间戳
     */
    public void setSendTimeMillis(long sendTimeMillis) {
        this.sendTimeMillis = sendTimeMillis;
    }
}
