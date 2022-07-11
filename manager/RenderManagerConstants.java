package manager;

/**
 * @author YXH_XianYu
 * Created On 2022-07-08
 */
public interface RenderManagerConstants {
    /**
     * 主菜单
     */
    int MAIN_MENU = 1;

    /**
     * 单人游戏（游戏中）
     */
    int SINGLE_PLAYER_GAME = 2;

    /**
     * 多人游戏（游戏中）
     */
    int MULTIPLAYER_GAME = 3;

    /**
     * 多人游戏-登入菜单
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int LOGIN_MENU = 4;

    /**
     * 多人游戏-大厅菜单
     * @deprecated 因产能原因，大厅功能废弃
     */
    @Deprecated
    int LOBBY_MENU = 5;

    /**
     * 多人游戏-加入菜单
     */
    int JOIN_MENU = 6;

    /**
     * 多人游戏-等待开始菜单
     * WAIT_MENU是等待另一个玩家加入的菜单。本菜单可以显示一些TIPS，来缓解等待的枯燥。
     */
    int WAIT_MENU = 7;

}
