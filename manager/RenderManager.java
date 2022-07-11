package manager;

import common.Texture;
import entity.Entity;
import entity.Character;
import factory.MapFactory;
import network.ClientManager;
import network.NetworkConstants;
import network.SerializeConstants;
import network.pack.Control;
import network.pack.Join;
import network.pack.Textures;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
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
 * 因为渲染管理器继承了JFrame
 * 所以菜单处理等功能全部包含在渲染管理器内
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
     * 缩放
     */
    private float scale = 1.0f;

    /**
     * 菜单状态
     * 1 主菜单(单人、多人、退出)
     * 2 单人
     * 3 多人
     * 4 登录
     * 5 大厅(创建、加入房间)
     */
    private int menuState;



    /**
     * 记录菜单是否已被渲染
     */
    private boolean isRendered;

    private RenderManager() {
        super("Cookie Scout: CER-65 RUS");
        //TODO add a icon of window

        menuState = RenderManagerConstants.MAIN_MENU;
    }

    /**
     * 初始化渲染管理器
     */
    public void init() {
        setLayout(null);
        setBounds(300, 0, WINDOW_Y, WINDOW_X);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            background = new Texture("pics/background.png", 1080, 1920, 540, 960);
            linearDodgePicture = ImageIO.read(this.getClass().getClassLoader().getResource("pics/bloom.png"));
        } catch (IOException e) {e.printStackTrace();}

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
        if(menuState == RenderManagerConstants.MAIN_MENU) {
            if(isRendered) return;
            isRendered = true;

            JButton single = new JButton("单人竞技场");
            single.setBounds(200, 200, 200, 50);
            single.setFocusable(false);
            single.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setMenuState(RenderManagerConstants.SINGLE_PLAYER_GAME);
                }
            });

            JButton multi = new JButton("多人竞技场");
            multi.setBounds(500, 200, 200, 50);
            multi.setFocusable(false);
            multi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // GUI
                    setMenuState(RenderManagerConstants.JOIN_MENU);
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
        } else if(menuState == RenderManagerConstants.SINGLE_PLAYER_GAME) {
            if(!isRendered) {
                isRendered = true;
                requestFocus();
                MapFactory.setMapIntoEntityManager(MapFactory.CER65RUS_LAB3);
            }

            EntityManager.getInstance().play();
            renderGame();
        } else if(menuState == RenderManagerConstants.LOGIN_MENU) {
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
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setMenuState(RenderManagerConstants.MAIN_MENU);
                }
            });

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
        } else if(menuState == RenderManagerConstants.LOBBY_MENU) {
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
        } else if(menuState == RenderManagerConstants.JOIN_MENU) {
            if(isRendered) return;
            isRendered = true;

            // instantiate
            JButton backButton = new JButton("Back");
            JButton joinButton = new JButton("Join");
            JLabel ipLabel = new JLabel("IP: ");
            JLabel portLabel = new JLabel("Port: ");
            JLabel nameLabel = new JLabel("Name: ");
            JTextField nameTextField = new JTextField();
            JTextField ipTextField = new JTextField();
            JTextField portTextField = new JTextField();
            JLabel tips = new JLabel();

            // function
            // MyTool.setButtonTransparent(registerButton, true);
            nameTextField.setText("Guest");
            ipTextField.setText("127.0.0.1");
            portTextField.setText("11451");

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientManager.getInstance().sendMessage(SerializeConstants.CLIENT_EXIT, null);
                    ClientManager.getInstance().interrupt();
                    setMenuState(RenderManagerConstants.MAIN_MENU);
                }
            });
            joinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    network.pack.Join join;
                    if(nameTextField.getText() == null || nameTextField.getText().equals(""))
                        join = new Join("Guest");
                    else
                        join = new Join(nameTextField.getText());

                    String ip = ipTextField.getText();

                    int port;
                    try {
                        port = Integer.parseInt(portTextField.getText());
                    } catch (NumberFormatException exception) {
                        tips.setText("端口格式错误：端口不是一个整数");
                        System.out.println("端口格式错误：端口不是一个整数");
                        return;
                    }
                    if(port < 1024 || port > 65535) {
                        tips.setText("端口格式错误：端口数值不合法");
                        System.out.println("端口格式错误：端口数值不合法");
                        return;
                    }

                    int ret = ClientManager.getInstance().init(ip, port, join);
                    if(ret == NetworkConstants.INIT_UNKNOWN_HOST_EXCEPTION) {
                        tips.setText("连接错误：未知地址");
                        System.out.println("连接错误：未知地址");
                        return;
                    } else if(ret == NetworkConstants.INIT_IO_EXCEPTION) {
                        tips.setText("连接错误：未知原因");
                        System.out.println("连接错误：未知原因");
                        return;
                    }

                    System.out.println("连接成功");
                    // 如果链接成功的话，才会切换菜单
                    // WAIT_MENU是等待另一个玩家加入的菜单
                    setMenuState(RenderManagerConstants.WAIT_MENU);
                }
            });

            // position

            nameLabel.setBounds(200, 100, 100, 50);
            nameTextField.setBounds(300, 100, 300, 50);

            ipLabel.setBounds(200, 200, 100, 50);
            ipTextField.setBounds(300, 200, 300, 50);

            portLabel.setBounds(200, 300, 100, 50);
            portTextField.setBounds(300, 300, 300, 50);

            tips.setBounds(250, 400, 200, 50);

            backButton.setBounds(100, 500, 200, 50);
            joinButton.setBounds(700, 500, 200, 50);

            // add
            add(nameLabel);
            add(nameTextField);
            add(ipLabel);
            add(ipTextField);
            add(portLabel);
            add(portTextField);
            add(backButton);
            add(joinButton);

            // repaint
            repaint();
        } else if(menuState == RenderManagerConstants.WAIT_MENU) {
            if(isRendered) return;
            isRendered = true;

            JTextField tipTextField = new JTextField("等待中...");

            tipTextField.setBounds(300, 300, 200, 50);

            add(tipTextField);

            repaint();
        } else if(menuState == RenderManagerConstants.MULTIPLAYER_GAME) {
            if(!isRendered) {
                isRendered = true;
                requestFocus();
                ClientManager.getInstance().initController();
                MapFactory.setMapIntoEntityManager(MapFactory.CLIENT_CER65RUS_LAB3);
            }

            // control
            Control control = ClientManager.getInstance().getControl();
            ClientManager.getInstance().sendMessage(SerializeConstants.CONTROL, control);
            // render
            renderGame();
        }
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

        if(getWidth() <= 1152 || getHeight() <= 648) scale = 0.4f;
        else if(getWidth() <= 1584 || getHeight() <= 864) scale = 0.6f;
        else scale = 0.8f;

        if(background.setScale(scale) || windowSizeChanged || backgroundAfterCutImage == null)
            backgroundAfterCutImage = background.getCutImage(background.getLx() / 2 - getHeight() / 2, background.getLy() / 2 - getWidth() / 2, getHeight(), getWidth());
        g.drawImage(backgroundAfterCutImage, 0, 0, null);

        // 渲染排序
        EntityManager.getInstance().sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return o1.getHitbox().getX() - o2.getHitbox().getX();
            }
        });

        for(int i = 0; i < EntityManager.getInstance().getEntitySize(); i++) {
            Entity entity = EntityManager.getInstance().getEntity(i);
            Texture texture;
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

        setBackground(new Color(38, 40, 74));
        graphics.drawImage(offScreenImage, 0, 0, null);
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
        reset();
    }

    /**
     * 获取缩放比例
     */
    public float getScale() {
        return scale;
    }

    /**
     * 设置背景
     * @param background
     */
    public void setBackground(Texture background) {
        this.background = background;
        this.backgroundAfterCutImage = null;
    }

    /**
     * 获取当前纹理包（发送给客户端）
     * @return 纹理包
     * @deprecated 通信方式更换，弃用纹理包
     */
    @Deprecated
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
     * 根据纹理包渲染游戏画面，并自动返回客户端接收到的ping值
     * @param texturesPack 纹理包
     * @return ping值
     * @deprecated 通信方式更换，弃用纹理包
     */
    @Deprecated
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

        for(int i = 0; texturesPack != null && i < texturesPack.getN(); i++) {
            common.Texture texture = texturesPack.getTexture(i);
            if(texture == null) continue; // 纹理包内也会有空纹理
            texture.setScale(scale);
            g.drawImage(texture.getImage(), (int)(texturesPack.getEntityY(i) * scale) - texture.getDy() + getWidth() / 2, (int)(texturesPack.getEntityX(i) * scale) - texture.getDx() + getHeight() / 2, null);
        }

        graphics.drawImage(offScreenImage, 0, 0, null);

        return 0; // System.currentTimeMillis() - texturesPack.getSendTimeMillis();
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
