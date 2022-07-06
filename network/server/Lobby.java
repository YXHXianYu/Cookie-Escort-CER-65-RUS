package network.server;

import java.util.ArrayList;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class Lobby {
    /**
     * 房间列表
     */
    private ArrayList<Room> roomList;

    /**
     * 构造房间
     */
    public Lobby() {
        roomList = new ArrayList<>();
    }

    /**
     * 添加房间
     * @param room 房间
     */
    public void addRoom(Room room) {
        roomList.add(room);
    }

    /**
     * 删除房间
     * @param room 房间
     */
    public void removeRoom(Room room) {
        roomList.remove(room);
    }

}
