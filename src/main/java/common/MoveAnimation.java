package common;

import common.Texture;
import manager.GameManager;
import org.w3c.dom.Text;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 移动动画类
 * 该类用作移动动画的储存和计算。
 * 这个类使用起来特别方便，只需要用到构造方法和play()方法，其他细节判断全部封装起来了。
 *
 * 这个游戏中，玩家可以主动控制人物朝八个方向移动。
 * 但其实，我们只需要四种移动动画便满足游戏表达的需要。
 *
 * 每种移动动画有10动画帧，每1动画帧占4游戏帧
 *
 * 由移动到静止、静止到移动，都会重置播放帧数cnt
 * 在移动中转变方向，也会重置播放帧数cnt
 *
 * 编号从0~3分别表示：下上左右（注意“下”是第一个）
 */
public class MoveAnimation {
    /**
     * 记录当前动画播放的动画帧
     * 取值范围 [0, 3]
     * cnt，即count
     */
    private int cnt;

    private final int cntLength = 4;

    /**
     * 记录当前动画播放的游戏帧
     * 取值范围 [0, cnt2Length - 1]
     *
     * 这种命名非常直观！
     * 如果你理解了动画播放逻辑就会认可这种命名方式的。
     */
    private int cnt2;

    /**
     * 动画帧长度
     */
    private final int cnt2Length = (int)(GameManager.getInstance().getFps() / 4.0);

    /**
     * 记录上一次角色移动的方向
     * 取值范围 [0, 3]
     */
    private int lastDirect;

    /**
     * 记录上一次请求移动动画的时间戳
     * 这样可以将动画播放封装到类里，不需要外部判断。
     * 外部类直接使用就好了，真是方便啊！
     */
    private int lastTimeStamp;

    /**
     * 记录动画
     * 其中，数组大小为4*4
     */
    private Texture[][] textures;

    /**
     * 构造一个角色移动动画
     * @param textures 动画信息
     */
    public MoveAnimation(Texture[][] textures) {
        this.cnt = 0;
        this.textures = textures;
    }

    /**
     * 输入角色移动的方向和当前的时间戳，输出角色移动动画对应的贴图
     * @param direct 移动方向
     * @param timeStamp 时间戳
     * @return 角色贴图
     */
    public Texture play(int direct, int timeStamp) {
        if(lastDirect != direct || lastTimeStamp + 1 != timeStamp) // 重置动画
            cnt = cnt2 = 0;
        else { // 判断是否要播放下一帧动画
            cnt = (cnt + (cnt2 == (cnt2Length - 1) ? 1 : 0)) % cntLength; // 看不懂的话把式子展开就好了。这样写简洁且优雅。
            cnt2 = (cnt2 + 1) % cnt2Length;
        }
        lastDirect = direct;
        lastTimeStamp = timeStamp;
        return textures[direct][cnt];
    }
}
