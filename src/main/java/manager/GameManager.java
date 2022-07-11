package manager;

import factory.MapFactory;
import network.Server;
import network.pack.Textures;

import javax.sound.sampled.AudioSystem;

/**
 * @author YXH_XianYu
 * Created On 2022-06-28
 *
 * 游戏管理类
 * 该类用于管理整个游戏
 *
 * 采用单例模式
 */
public class GameManager {
    /**
     * 单例模式，实例
     */
    private static GameManager Instance = null;

    /**
     * 单例模式，获取实例
     * @return 实例
     */
    public static GameManager getInstance() {
        if(Instance == null)
            Instance = new GameManager();
        return Instance;
    }

    /**
     * 游戏时间戳（游戏帧的编号）
     */
    private int timeStamp;

    /**
     * 大版本号
     */
    private final int version1 = 0;

    /**
     * 小版本号
     */
    private final int version2 = 1;

    /**
     * 游戏帧数（存在误差）
     */
    private final int fps = 120;

    /**
     * 是否锁帧数
     */
    private final boolean fixedFps = true;

    /**
     * 游戏帧数校准
     */
    private final int delta_bias = 0;

    /**
     * 运行平台是否是客户端
     */
    private boolean isClient;

    /**
     * 构造方法
     */
    private GameManager() {
        timeStamp = 0;
    }

    /**
     * 获取时间戳
     * @return 时间戳
     */
    public int getTimeStamp() {
        return timeStamp;
    }

    /**
     * 获取游戏帧数
     * @return 游戏帧数
     */
    public int getFps() {
        return fps;
    }

    /**
     * 平台是否是客户端
     */
    public boolean isClient() {
        return isClient;
    }

    /**
     * 初始化（客户端）
     */
    private void init() {
        // audio
        try {
            AudioSystem.getClip();
        } catch (javax.sound.sampled.LineUnavailableException e) {
            e.getStackTrace();
        }
    }

    /**
     * 游戏主循环（客户端）
     */
    public void play() {

        // render
        RenderManager.getInstance().init();
        init();
        isClient = true;

        long lastTime = -1;
        long lastSecondTime = -1;
        int cnt = 0;
        while(true) {
            if(lastTime == -1) {
                lastTime = System.currentTimeMillis();
                lastSecondTime = System.currentTimeMillis();
            }
            // 时间戳+1
            timeStamp++;

            // 调用其他管理器
            RenderManager.getInstance().renderMenu();

            // 帧数限制
            long time = System.currentTimeMillis();
            if(fixedFps) {
                lastTime += 1000 / fps;
                long delta = lastTime - time;
                if(delta <= delta_bias) {
                    if(delta < delta_bias) {
                        // System.out.println("游戏帧数过低");
                        // 发现有时候因为窗口拖动会产生跳帧，无伤大雅
                    }
                } else {
                    try {
                        Thread.sleep(delta - delta_bias);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
            cnt++;
            if(System.currentTimeMillis() - lastSecondTime >= 1000) {
                System.out.println("fps = " + cnt);
                lastSecondTime = System.currentTimeMillis();
                cnt = 0;
            }
        }
    }

    /**
     * 服务器版 - 游戏主循环
     */
    public void playServer() {

        init();
        isClient = false;
        MapFactory.setMapIntoEntityManager(MapFactory.SERVER_CER65RUS_LAB3);

        long lastTime = -1;
        long lastSecondTime = -1;

        int cnt = 0;
        while(true) {
            if(lastTime == -1) {
                lastTime = System.currentTimeMillis();
                lastSecondTime = System.currentTimeMillis();
            }
            // 时间戳+1
            timeStamp++;

            // 调用其他管理器
            EntityManager.getInstance().play();
            Server.getInstance().broadcastEntityMessages(EntityManager.getInstance().getEntityMessages());

            // 帧数限制
            long time = System.currentTimeMillis();
            if(fixedFps) {
                lastTime += 1000 / fps;
                long delta = lastTime - time;
                if(delta <= delta_bias) {
                    if(delta < delta_bias) {
                        // System.out.println("游戏帧数过低");
                        // 发现有时候因为窗口拖动会产生跳帧，无伤大雅
                    }
                } else {
                    try {
                        Thread.sleep(delta - delta_bias);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
            cnt++;
            if(System.currentTimeMillis() - lastSecondTime >= 1000) {
                System.out.println("fps = " + cnt);
                lastSecondTime = System.currentTimeMillis();
                cnt = 0;
            }
        }
    }
}
