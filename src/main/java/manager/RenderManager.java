package manager;

import common.Texture;
import entity.Entity;
import entity.Character;
import factory.MapFactory;
import network.Client;
import network.pack.Textures;
import tool.MyTool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author YXH_XianYu
 * Created On 2022-07-01
 *
 * 渲染管理器
 * 渲染游戏画面(采用Swing)
 * 该类继承自JFrame，重写了paint(Graphics)方法
 *
 * 渲染管理器包含了菜单的逻辑
 */
public class RenderManager extends JFrame {
    /**
     * 单例模式
     */
    private static RenderManager Instance = null;

    /**
     * 单例模式
     * @return 实例
     */
    public static RenderManager getInstance() {
        if(Instance == null)
            Instance = new RenderManager();
        return Instance;
    }

    /**
     * 背景纹理
     */
    private common.Texture background;

    /**
     * 背景纹理缓存
     */
    private BufferedImage backgroundAfterCutImage;

    /**
     * 线性减淡图层(Bloom)
     */
    private BufferedImage linearDodgePicture;

    /**
     * 初始窗口大小
     * 高度
     */
    private final int WINDOW_X = 900;

    /**
     * 初始窗口大小
     * 宽度
     */
    private final int WINDOW_Y = 1600;

    /**
     * 记录上一轮游戏帧的窗口大小，保证缓存文件的及时刷新。
     * 窗口宽度
     */
    private int lastWidth;

    /**
     * 记录上一轮游戏帧的窗口大小，保证缓存文件的及时刷新。
     * 窗口高度
     */
    private int lastHeight;

    /**
     * 菜单状态
     * 0 Game
     * 1 主菜单(单人、多人、退出)
     * 2 单人(开始游戏)
     * 3 多人(连接至服务器)
     * 4 大厅(创建、加入房间)
     * 5 房间
     */
    private int menuState;

    public static int MAIN_MENU = 1;

    public static int SINGLE_PLAYER_GAME = 2;

    public static int MULTIPLAYER_GAME = 3;

    public static int LOGIN_MENU = 4;

    public static int LOBBY_MENU = 5;

    /**
     * 记录菜单是否已被渲染
     */
    private boolean isRendered;

    /**
     * 初始化渲染管理器
     */
    private RenderManager() {
        super("Cookie Scout: CER-65 RUS");
        //TODO add a icon of window

        setLayout(null);
        setBounds(300, 0, WINDOW_Y, WINDOW_X);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuState = MAIN_MENU;

        try {
            background = new Texture("./pics/background.png", 1080, 1920, 540, 960);
            linearDodgePicture = ImageIO.read(new File("./pics/bloom.png"));
        } catch (IOException e) {e.getStackTrace();}

        setVisible(true);
    }

    private void reset() {
        isRendered = false;
        getContentPane().removeAll();//or remove(JComponent)
        revalidate();
        repaint();
    }

    /**
     * 渲染菜单 并 执行菜单功能
     *
     * 实际上也包含了菜单功能
     */
    public void renderMenu() {
        if(menuState == MAIN_MENU) {
            if(isRendered) return;
            isRendered = true;

            JButton single = new JButton("单人竞技场");
            single.setBounds(200, 200, 200, 50);
            single.setFocusable(false);
            single.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuState = SINGLE_PLAYER_GAME;
                    reset();
                }
            });

            JButton multi = new JButton("多人竞技场");
            multi.setBounds(500, 200, 200, 50);
            multi.setFocusable(false);
            multi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuState = LOGIN_MENU;
                    reset();
                }
            });

            JButton exit = new JButton("离开CER-65 RUS");
            exit.setBounds(800, 200, 200, 50);
            exit.setFocusable(false);
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            add(single);
            add(multi);
            add(exit);

            repaint();
        } else if(menuState == SINGLE_PLAYER_GAME) {
            if(!isRendered) {
                isRendered = true;
                MapFactory.setMapIntoEntityManager(MapFactory.DEFAULT_MAP);
            }

            EntityManager.getInstance().play();
            renderGame();

        } else if(menuState == LOGIN_MENU) {
            if(isRendered) return;
            isRendered = true;

            // instantiate
            JButton registerButton = new JButton("Register");
            JButton loginButton = new JButton("Login");
            JButton guestButton = new JButton("Guest");
            JButton backButton = new JButton("Back");
            JButton confirmButton = new JButton("Confirm");
            JTextField accountName = new JTextField();
            JTextField password = new JTextField();

            // function
            // MyTool.setButtonTransparent(registerButton, true);

            // position
            registerButton.setBounds(100, 100, 200, 50);
            loginButton.setBounds(400, 100, 200, 50);
            guestButton.setBounds(700, 100, 200, 50);
            accountName.setBounds(200, 300, 400, 50);
            password.setBounds(200, 500, 400, 50);
            backButton.setBounds(100, 700, 200, 50);
            confirmButton.setBounds(700, 700, 200, 50);

            // add
            add(registerButton);
            add(loginButton);
            add(guestButton);
            add(accountName);
            add(password);
            add(backButton);
            add(confirmButton);

            // repaint
            repaint();
        } else if(menuState == LOBBY_MENU) {
            if(isRendered) {
                // TODO 发送请求
                // TODO 接收大厅信息
                // TODO 显示
            }
            isRendered = true;

            // instantiate
            JLabel createRoom = new JLabel("Create Room");
            JTextField roomName = new JTextField();

            // function

            // position
            createRoom.setBounds(100, 100, 200, 50);
            roomName.setBounds(100, 400, 200, 50);

            // add
            add(createRoom);
            add(roomName);

            // repaint
            repaint();
        }
    }


    /**
     * 获取当前纹理包（发送给客户端）
     * @return 纹理包
     */
    public Textures getCurrentTexturePack() {
        Textures texturesPack = new Textures(EntityManager.getInstance().getEntitySize());
        for(int i = 0; i < EntityManager.getInstance().getEntitySize(); i++) {
            Entity entity = EntityManager.getInstance().getEntity(i);
            common.Texture texture;
            if(entity instanceof Character) {
                texture = ((Character)entity).getCurrentTexture();
            } else {
                texture = entity.getTexture();
            }
            if(texture == null) continue; // 空气墙等实体没有贴图，直接跳过渲染
            texturesPack.setTexture(i, texture);
            texturesPack.setEntityX(i, entity.getHitbox().getX());
            texturesPack.setEntityY(i, entity.getHitbox().getY());
        }
        return texturesPack;
    }


    /**
     * 渲染游戏画面
     *
     * 渲染顺序：背景层、实体层、前景层、特效层、UI层
     * Swing叠放顺序是反的，最早叠放在最上层，就离谱。
     *
     * 利用了双缓冲，避免游戏掉帧时的闪屏现象
     */
    public void renderGame() {
        Graphics graphics = this.getGraphics();

        // 画布
        Image offScreenImage = this.createImage(getWidth(), getHeight());
        Graphics g = offScreenImage.getGraphics();

        boolean windowSizeChanged;
        if(lastWidth != getWidth() || lastHeight != getHeight()) {
            windowSizeChanged = true;
            lastWidth = getWidth();
            lastHeight = getHeight();
        } else {
            windowSizeChanged = false;
        }

        float scale;
        if(getWidth() <= 1152 || getHeight() <= 648) scale = 0.6f;
        else if(getWidth() <= 1584 || getHeight() <= 864) scale = 0.8f;
        else scale = 1f;

        if(background.setScale(scale) || windowSizeChanged || backgroundAfterCutImage == null)
            backgroundAfterCutImage = background.getCutImage(background.getLx() / 2 - getHeight() / 2, background.getLy() / 2 - getWidth() / 2, getHeight(), getWidth());
        g.drawImage(backgroundAfterCutImage, 0, 0, null);

        for(int i = 0; i < EntityManager.getInstance().getEntitySize(); i++) {
            Entity entity = EntityManager.getInstance().getEntity(i);
            common.Texture texture;
            if(entity instanceof Character) {
                texture = ((Character)entity).getCurrentTexture();
            } else {
                texture = entity.getTexture();
            }
            if(texture == null) continue; // 空气墙等实体没有贴图，直接跳过渲染
            texture.setScale(scale);
            g.drawImage(texture.getImage(), (int)(entity.getHitbox().getY()*scale) - texture.getDy() + getWidth() / 2, (int)(entity.getHitbox().getX()*scale) - texture.getDx() + getHeight() / 2, null);
        }

        //if(linearDodgePicture != null)
        //    offScreenImage = MyTool.linearDodge(((BufferedImage)offScreenImage), linearDodgePicture);

        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    /**
     * 根据纹理包渲染游戏画面，并自动返回客户端接收到的ping值
     * @param texturesPack 纹理包
     * @return ping值
     */
    public long renderGame(Textures texturesPack) {
        Graphics graphics = this.getGraphics();

        // 画布
        Image offScreenImage = this.createImage(getWidth(), getHeight());
        Graphics g = offScreenImage.getGraphics();

        boolean windowSizeChanged;
        if(lastWidth != getWidth() || lastHeight != getHeight()) {
            windowSizeChanged = true;
            lastWidth = getWidth();
            lastHeight = getHeight();
        } else {
            windowSizeChanged = false;
        }

        float scale;
        if(getWidth() <= 1152 || getHeight() <= 648) scale = 0.6f;
        else if(getWidth() <= 1584 || getHeight() <= 864) scale = 0.8f;
        else scale = 1f;

        if(background.setScale(scale) || windowSizeChanged || backgroundAfterCutImage == null)
            backgroundAfterCutImage = background.getCutImage(background.getLx() / 2 - getHeight() / 2, background.getLy() / 2 - getWidth() / 2, getHeight(), getWidth());
        g.drawImage(backgroundAfterCutImage, 0, 0, null);

        for(int i = 0; i < texturesPack.getN(); i++) {
            common.Texture texture = texturesPack.getTexture(i);
            if(texture == null) continue; // 纹理包内也会有空纹理
            texture.setScale(scale);
            g.drawImage(texture.getImage(), (int)(texturesPack.getEntityY(i) * scale) - texture.getDy() + getWidth() / 2, (int)(texturesPack.getEntityX(i) * scale) - texture.getDx() + getHeight() / 2, null);
        }

        graphics.drawImage(offScreenImage, 0, 0, null);

        return System.currentTimeMillis() - texturesPack.getSendTimeMillis();
    }

    /**
     * 获取菜单状态
     */
    public int getMenuState() {
        return menuState;
    }

    /**
     * 设置菜单状态 并 充值菜单
     */
    public void setMenuState(int menuState) {
        this.menuState = menuState;
    }

    /**
     * ----- 已废弃 -----
     * 游戏画面容器
     */
    // private JPanel gamePanel;

    /**
     * ----- 已废弃 -----
     * 渲染游戏画面
     * 输出Panel大小为窗体大小
     *
     * 渲染顺序：背景层、实体层、前景层、特效层、UI层
     * Swing叠放顺序是反的，最早叠放在最上层，就离谱。
     */
    /*
    private JPanel renderGamePanel() {
        gamePanel.removeAll();
        gamePanel.setBounds(0, 0, getWidth(), getHeight());
        gamePanel.setLayout(null);
        gamePanel.setBackground(Color.gray);

        float scale;
        if(getWidth() <= 1584 || getHeight() <= 864) scale = 0.8f;
        else scale = 1f;

        // EntityManager.getInstance().sort()
        for(int i = 0; i < EntityManager.getInstance().getEntitySize(); i++) {
            Entity entity = EntityManager.getInstance().getEntity(i);
            JLabel pic;
            if(entity instanceof Character) {
                pic = new JLabel(((Character)entity).getCurrentTexture().getImage());
                pic.setBounds(entity.getHitbox().getY() - entity.getTexture().getDy() + getWidth() / 2,
                        entity.getHitbox().getX() - entity.getTexture().getDx() + getHeight() / 2,
                        entity.getTexture().getLy(),
                        entity.getTexture().getLx());
            } else {
                pic = new JLabel(entity.getTexture().getImage());
                pic.setBounds(entity.getHitbox().getY() - entity.getTexture().getDy() + getWidth() / 2,
                        entity.getHitbox().getX() - entity.getTexture().getDx() + getHeight() / 2,
                        entity.getTexture().getLy(),
                        entity.getTexture().getLx());
            }
            gamePanel.add(pic);
        }

        //JLabel jBackground = new JLabel(background.getCutImage(background.getDx() - lx / 2, background.getDy() - ly /  2, lx, ly));
        JLabel jBackground = new JLabel(background.getImage());
        jBackground.setBounds(0, 0, getWidth(), getHeight());
        gamePanel.add(jBackground);

        return gamePanel;
    }
    */
}
