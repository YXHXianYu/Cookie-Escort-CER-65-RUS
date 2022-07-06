package network.pack;

import network.server.Lobby;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class LobbyInformation {
    /**
     * 大厅
     */
    private Lobby lobby;

    /**
     * 构造方法
     */
    public LobbyInformation(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * 获取大厅
     */
    public Lobby getLobby() {
        return lobby;
    }
}
