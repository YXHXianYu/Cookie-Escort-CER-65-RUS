package network;

import entity.Controller;
import factory.ControllerFactory;
import manager.EntityManager;
import manager.RenderManager;
import manager.RenderManagerConstants;
import network.pack.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 *
 * 客户端管理器
 */
public class ClientManager {
    /**
     * 单例模式
     */
    private static ClientManager Instance = null;

    /**
     * 单例模式
     * @return 实例
     */
    public static ClientManager getInstance() {
        if(Instance == null)
            Instance = new ClientManager();
        return Instance;
    }

    /**
     * 服务器IP
     */
    private String IP;

    /**
     * 服务器端口
     */
    private int port;

    /**
     * Socket
     */
    private Socket socket;

    /**
     * 序列化输入流
     */
    private ObjectInputStream inputObject;

    /**
     * 序列化输出流
     */
    private ObjectOutputStream outputObject;

    /**
     * 输入流线程锁
     */
    private final Object inputLock = new Object();

    /**
     * 输出流线程锁
     */
    private final Object outputLock = new Object();


    /**
     * 客户端的监听输入线程
     */
    private ThreadInClient thread;

    /**
     * 纹理包
     */
    private Textures textures;

    /**
     * 客户端控制器
     */
    private Controller controller;

    /**
     * 初始化方法
     */
    private ClientManager() {
    }

    /**
     * 初始化客户端管理器
     * @param IP 服务器IP
     * @param port 服务器端口
     * @param join Join信息包
     * @return 连接状态
     */
    public int init(String IP, int port, Join join) {
        try {
            socket = new Socket(IP, port);
            // BE CAREFUL! ObjectOutputStream must be created before ObjectInputStream !
            // And you must flush the stream.
            // refer to https://stackoverflow.com/questions/14110986/new-objectinputstream-blocks
            outputObject = new ObjectOutputStream(socket.getOutputStream());
            outputObject.flush();
            inputObject = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException exception) {
            return NetworkConstants.INIT_UNKNOWN_HOST_EXCEPTION;
        } catch (IOException e) {
            return NetworkConstants.INIT_IO_EXCEPTION;
        }

        // send join information
        try {
            outputObject.writeObject(SerializeConstants.JOIN);
            outputObject.flush();
            outputObject.writeObject(join);
            outputObject.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return NetworkConstants.INIT_IO_EXCEPTION;
        }

        // start thread
        thread = new ThreadInClient();
        thread.start();

        return NetworkConstants.INIT_SUCCESS;
    }

    /**
     * 初始化控制器
     */
    public void initController() {
        // controller
        controller = (Controller) ControllerFactory.getController(ControllerFactory.LOCAL_PLAYER_CONTROLLER);
    }

    /**
     * 客户端的监听输入线程
     *
     * 线程创建完成后，要确保所有输入输出均在线程内进行，避免奇奇怪怪的错误。
     * 如果在线程外执行，一定要加synchronized
     */
    private class ThreadInClient extends Thread {
        @Override
        public void run() {
            synchronized (inputLock) {
                try {
                    int type;
                    while(true) {
                        type = -1;
                        while(type == -1) {
                            Thread.sleep(1);
                            type = (Integer) inputObject.readObject();
                        }

                        if(type != 15)
                           System.out.println("Client received: " + type);

                        switch (type) {
                            case SerializeConstants.HEARTBEAT: {
                                Ping ping = (Ping) inputObject.readObject();
                                System.out.println("Ping = " + ping.getPing());
                                synchronized (outputLock) {
                                    outputObject.writeObject(SerializeConstants.HEARTBEAT);
                                    outputObject.flush();
                                }
                                break;
                            }
                            case SerializeConstants.VERSION_CHECK: {
                                VersionCheck versionCheck = (VersionCheck) inputObject.readObject();
                                // TODO Version Check
                                break;
                            }
                            case SerializeConstants.CLIENT_EXIT: {
                                System.out.println("收到退出指令");
                                break;
                            }
                            case SerializeConstants.START_GAME: {
                                RenderManager.getInstance().setMenuState(RenderManagerConstants.MULTIPLAYER_GAME);
                                break;
                            }
                            //case SerializeConstants.TEXTURE: {
                            //    textures = (Textures) inputObject.readObject();
                            //}
                            case SerializeConstants.ENTITY_MESSAGES: {
                                EntityMessages entityMessages = (EntityMessages) inputObject.readObject();
                                EntityManager.getInstance().useEntityMessages(entityMessages);
                                break;
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
                    return;
                }
            }

        }
    }
    /**
     * 向服务端发送一条信息
     * 注意：每次发送的Object引用需要不同！否则ObjectStream会输出旧的值
     * @param type 信息类型
     * @param object 信息值
     */
    public void sendMessage(int type, Object object) {
        synchronized (outputLock) {
            try {
                outputObject.writeObject(type);
                outputObject.flush();
                if(object != null) {
                    //if(object instanceof Control) System.out.println("Control: " + ((Control) object).getAimX() + ", " + ((Control) object).getAimY());
                    outputObject.writeObject(object);
                    outputObject.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("检测到有客户端断开连接，服务器已关闭");
                System.exit(-1);
            } catch (NullPointerException e) {
                return;
            }
        }
    }

    public void interrupt() {
        try {
            thread.interrupt();
        } catch (NullPointerException e) {
        }
    }

    /**
     * 获取纹理包
     * @deprecated 通信方式更换，弃用纹理包
     */
    @Deprecated
    public Textures getTextures() {
        return textures;
    }

    /**
     * 获取控制包
     */
    public Control getControl() {
        if(controller == null) return null;
        controller.control(null);
        Control newControl = new Control(controller.getControl());
        return newControl;
    }
}
