package network;

import manager.GameManager;
import manager.RenderManager;

/**
 * @author YXH_XianYu
 * Created On 2022-07-04
 *
 * 客户端
 */
public class Client {
    public static void main(String[] args) {

        GameManager.getInstance().play();

    }
}
