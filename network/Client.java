package network;

import manager.GameManager;
import tool.MyTool;

/**
 * @author YXH_XianYu
 * Created On 2022-07-04
 *
 * 客户端
 * （只提供了程序入口）
 */
public class Client {
    /**
     * 程序入口
     */
    public static void main(String[] args) {

        GameManager.getInstance().setIsClient(true);

        MyTool.playBGM("sound/LOR_Nervous.wav");

        GameManager.getInstance().play();

    }
}
