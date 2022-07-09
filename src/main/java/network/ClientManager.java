package network;

import manager.RenderManager;
import manager.RenderManagerConstants;
import network.pack.Join;
import network.pack.Textures;
import network.pack.VersionCheck;

import java.io.*;
import java.net.Socket;
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
     * 输入（服务端发来的信息）
     */
    private BufferedInputStream input;

    /**
     * 输出（发给服务端的信息）
     */
    private BufferedOutputStream output;

    /**
     * 序列化输入流
     */
    private ObjectInputStream inputObject;

    /**
     * 序列化输出流
     */
    private ObjectOutputStream outputObject;

    /**
     * 客户端的监听输入线程
     */
    private ThreadInClient thread;

    /**
     * 纹理包
     */
    private Textures textures;

    /**
     * 初始化方法
     */
    private ClientManager() {
    }

    public int init(String IP, int port, Join join) {
        try {
            socket = new Socket(IP, port);
            output = new BufferedOutputStream(socket.getOutputStream());
            input = new BufferedInputStream(socket.getInputStream());
            // BE CAREFUL! ObjectOutputStream must be created before ObjectInputStream !
            // And you must flush the stream.
            // refer to https://stackoverflow.com/questions/14110986/new-objectinputstream-blocks
            outputObject = new ObjectOutputStream(output);
            outputObject.flush();
            inputObject = new ObjectInputStream(input);
        } catch (UnknownHostException exception) {
            return NetworkConstants.INIT_UNKNOWN_HOST_EXCEPTION;
        } catch (IOException e) {
            return NetworkConstants.INIT_IO_EXCEPTION;
        }

        // send join information
        try {
            output.write(SerializeConstants.JOIN);
            outputObject.writeObject(join);
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
     * 客户端的监听输入线程
     *
     * 线程创建完成后，要确保所有输入输出均在线程内进行，避免奇奇怪怪的错误。
     * 如果在线程外执行，一定要加synchronized
     */
    private class ThreadInClient extends Thread {
        @Override
        public void run() {
            synchronized (input) {
                try {
                    int type;
                    while(true) {
                        type = input.read();
                        System.out.println("Client received: " + type);

                        switch (type) {
                            case SerializeConstants.HEARTBEAT: {
                                synchronized (output) {
                                    output.write(SerializeConstants.HEARTBEAT);
                                    output.flush();
                                }
                                break;
                            }
                            case SerializeConstants.VERSION_CHECK: {
                                VersionCheck versionCheck = (VersionCheck) inputObject.readObject();
                                // TODO Version Check
                            }
                            case SerializeConstants.CLIENT_EXIT: {
                                System.out.println("收到退出指令");
                                break;
                            }
                            case SerializeConstants.START_GAME: {
                                RenderManager.getInstance().setMenuState(RenderManagerConstants.MULTIPLAYER_GAME);
                            }
                            case SerializeConstants.TEXTURE: {
                                textures = (Textures) inputObject.readObject();
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }

        }
    }
    /**
     * 向服务端发送一条信息
     * @param type 信息类型
     * @param object 信息值
     */
    public void sendMessage(int type, Object object) {
        synchronized (output) {
            try {
                output.write(type);
                output.flush();
                if(object != null) {
                    outputObject.writeObject(object);
                    outputObject.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void interrupt() {
        thread.interrupt();
    }

    /**
     * 获取纹理包
     */
    public Textures getTextures() {
        return textures;
    }
}
