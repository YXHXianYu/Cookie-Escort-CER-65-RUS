package network.server;

import map.GameMap;

import java.util.ArrayList;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 *
 * 房间类
 *
 * 该类用于表示一个多人游戏房间
 */
public class Room {
    /**
     * 房间名称
     */
    private String name;

    /**
     * 当前玩家数量
     */
    private int playerNumber;

    /**
     * 玩家数量上限
     */
    private int playerNumberLimit;

    /**
     * 玩家列表
     */
    private ArrayList<Player> playerList;

    /**
     * 房间密码
     *  - 为""或null均表示无密码
     */
    private String roomPassword;

    /**
     * 是否允许访客加入
     */
    private boolean enableGuest;

    /**
     * 游戏地图
     */
    private GameMap map;

    /**
     * 构造一个房间
     * @param name 房间名字
     * @param playerNumberLimit 玩家信息
     * @param roomPassword 房间密码
     * @param enableGuest 是否允许访客
     * @param map 地图
     */
    public Room(String name, int playerNumberLimit, String roomPassword, boolean enableGuest, GameMap map) {
        this.name = name;
        this.playerNumber = 0;
        this.playerNumberLimit = playerNumberLimit;
        this.playerList = new ArrayList<>();
        this.roomPassword = roomPassword;
        this.enableGuest = enableGuest;
        this.map = map;
    }

    /**
     * 获取房间名称
     * @return 房间名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取玩家数量
     * @return 玩家数量
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * 设置玩家数量
     * @param playerNumber 玩家数量
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * 获取玩家数量上限
     * @return 玩家数量上限
     */
    public int getPlayerNumberLimit() {
        return playerNumberLimit;
    }

    /**
     * 获取玩家列表
     * @return 玩家列表
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * 获取房间密码
     * @return 房间密码
     */
    public String getRoomPassword() {
        return roomPassword;
    }

    /**
     * 查询是否允许访客
     * @return 是否允许访客
     */
    public boolean isEnableGuest() {
        return enableGuest;
    }

    /**
     * 获取游戏地图
     * @return 游戏地图
     */
    public GameMap getMap() {
        return map;
    }
}
