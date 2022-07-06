package network.pack;

import network.server.Room;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class CreateRoom {
    /**
     * 房间
     */
    private Room room;

    /**
     * 构造方法
     */
    public CreateRoom(Room room) {
        this.room = room;
    }

    /**
     * 获取房间
     */
    public Room getRoom() {
        return room;
    }
}
