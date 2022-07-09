package network;

import factory.MapFactory;
import manager.EntityManager;
import manager.GameManager;
import network.pack.Join;
import network.pack.Textures;
import network.pack.VersionCheck;
import network.server.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 *
 * 服务端
 *
 * 单例模式
 *
 */
public class Server {
    /**
     * 单例模式
     */
    private static Server Instance = null;

    /**
     * 单例模式
     * @return 实例
     */
    public static Server getInstance() {
        if(Instance == null)
            Instance = new Server(); // 调用默认构造方法，因为初始化等内容我直接在launch()内实现了。
        return Instance;
    }

    /**
     * ServerSocket
     */
    private ServerSocket serverSocket;

    /**
     * 服务器开放端口
     * 默认开放端口为11451
     */
    private int port = 11451;

    /**
     * 玩家人数
     * 默认人数为2
     */
    private int playerMax = 2;

    /**
     * 根据UUID定位客户端
     */
    private HashMap<String, Client> clientHash;

    /**
     * 心跳线程
     */
    private HeartbeatThread heartbeatThread;

    /**
     * 启动&运行
     */
    private void launch() {
        /* 1. 初始化部分  */
        // Server Socket Open
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException exception) {
            System.out.println("未知错误");
            return;
        } catch (IllegalArgumentException exception) {
            System.out.println("端口错误");
            return;
        }
        // Initialize
        clientHash = new HashMap<>();

        heartbeatThread = new HeartbeatThread();
        heartbeatThread.start();
        System.out.println("启动心跳线程");

        System.out.println("服务器启动完毕");
        /* 2. 等待部分  */
        while(true) {
            System.out.println("等待客户端连接...(" + clientHash.size() + "/" + playerMax + ")");
            String guid = UUID.randomUUID().toString().replaceAll("-", "");
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException exception) {
                exception.printStackTrace();
                return;
            }
            System.out.println("发现新客户端");
            clientHash.put(guid, new Client(guid, socket));
            if(clientHash.size() >= 2) break;
        }

        /* 3. 游戏部分  */
        System.out.println("启动游戏中...");

        // 广播游戏开始
        for(Client client: clientHash.values())
            client.sendMessage(SerializeConstants.START_GAME, null);

        // TODO 调用GameManager.playServer(...)
        GameManager.getInstance().playServer();


    }

    public void broadcastTextures(Textures texturesBroadcast) {
        for(Client client: clientHash.values()) {
            client.sendMessage(SerializeConstants.TEXTURE, texturesBroadcast);
        }
        //System.out.println("Broadcast");
    }

    private void removeClient(String guid) {
        Client client = clientHash.get(guid);
        client.thread.interrupt();
        if(clientHash.remove(guid, client))
            System.out.println("客户端已移除");
        else
            System.out.println("客户端移除失败！");
        System.out.println("当前客户端个数 (" + clientHash.size() + "/" + playerMax + ")");
    }

    /**
     * 心跳线程
     * 向所有客户端广播心跳，并检测客户端心跳计数，若<=0则删去客户端
     */
    private class HeartbeatThread extends Thread {
        private static final long HEARTBEAT_INTERVAL = 10000; // 10s
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("Heartbeat...");
                ArrayList<String> removeClients = new ArrayList<>();
                for(Client client: clientHash.values()) {
                    if(client.heartbeatCnt <= 0 || client.socket.isClosed()) {
                        if(client.socket.isClosed())
                            System.out.println("该客户端已关闭");
                        removeClients.add(client.guid);
                    } else {
                        client.heartbeatCnt--;
                        client.sendMessage(SerializeConstants.HEARTBEAT, null);
                    }
                }
                for(String i: removeClients)
                    removeClient(i);
            }
        }
    }

    /**
     * 客户端内部类
     * 每个连接至服务端的客户端都对应着一个客户端内部类
     */
    private class Client {
        /**
         * GUID
         */
        private String guid;

        /**
         * Socket
         */
        private Socket socket;

        /**
         * 输入流
         */
        private BufferedInputStream input;

        /**
         * 输出流
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
         * 玩家姓名
         */
        private String name;

        /**
         * 服务端的监听输入线程
         */
        private ThreadInServer thread;

        /**
         * 心跳响应计数器
         */
        private long heartbeatCnt;

        /**
         * 构造方法
         */
        public Client(String guid, Socket socket) {
            this.guid = guid;
            this.socket = socket;
            try {
                output = new BufferedOutputStream(socket.getOutputStream());
                input = new BufferedInputStream(socket.getInputStream());
                // BE CAREFUL! ObjectOutputStream must be created before ObjectInputStream !
                // And you must flush the stream.
                // refer to https://stackoverflow.com/questions/14110986/new-objectinputstream-blocks
                outputObject = new ObjectOutputStream(output);
                outputObject.flush();
                inputObject = new ObjectInputStream(input);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            heartbeatCnt = 3;

            thread = new ThreadInServer();
            thread.start();
        }

        /**
         * 服务端的监听输入线程
         */
        private class ThreadInServer extends Thread {
            @Override
            public void run() {
                synchronized (input) {
                    try {
                        // do <===
                        int type;
                        while(true) {
                            type = input.read();
                            System.out.println("Server received: " + type);
                            if(type == SerializeConstants.JOIN) {
                                Join join = (Join) inputObject.readObject();
                                name = join.getName();
                            } else if(type == SerializeConstants.HEARTBEAT) {
                                heartbeatCnt = 2;
                            } else if(type == SerializeConstants.CLIENT_EXIT || type == -1) {
                                removeClient(guid);
                                return;
                            }
                        }
                        // do ===>
                    } catch (SocketException e) {
                        // 大概率是客户端关闭
                        return;
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 向客户端发送一条信息
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
                    //System.out.println("客户端失去连接");
                }
            }
        }
    }

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        // 设置参数
        for(int i = 0; args != null && i < args.length; i++) {
            if(args[i].equals("-port")) { // 指定端口 TODO 测试一下指定端口正确性
                i++;
                try {
                    Server.getInstance().port = Integer.parseInt(args[i]);
                } catch (NumberFormatException exception) {
                    System.out.println("端口错误");
                    return;
                }
            }
        }

        // 启动服务器
        Server.getInstance().launch();
    }
}
