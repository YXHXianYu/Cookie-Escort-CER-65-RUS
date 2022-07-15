package network;

import factory.CharacterFactory;
import factory.MapFactory;
import manager.GameManager;
import tool.MyTool;

import javax.imageio.ImageIO;
import java.io.IOException;

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
        // 设置参数
        for(int i = 0; args != null && i < args.length; i++) {
            if(args[i].equals("-character")) { // 指定端口 TODO 测试一下指定端口正确性
                i++;
                if(args[i].equals("Number10")) {
                    MapFactory.characterChoose = CharacterFactory.NUMBER_10;
                    System.out.println("Single Arena Choose Number10");
                } else if(args[i].equals("Number17")) {
                    MapFactory.characterChoose = CharacterFactory.NUMBER_17;
                    System.out.println("Single Arena Choose Number17");
                }
            }
        }

        GameManager.getInstance().setIsClient(true);

        MyTool.playBGM("sound/LOR_Nervous.wav");

        GameManager.getInstance().play();

    }
}
