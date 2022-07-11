package factory;

import common.Texture;
import entity.Character;
import factory.entityFactoryMessage.CharacterFactoryMessage;
import factory.entityFactoryMessage.ObstacleFactoryMessage;
import manager.EntityManager;
import manager.RenderManager;
import network.Server;

import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class MapFactory {

    /**
     * 默认地图
     */
    public static int DEFAULT_MAP = 0;

    /**
     * 服务端版 CER-65 RUS 实验室3
     */
    public static int SERVER_CER65RUS_LAB3 = 1;

    /**
     * 客户端版 CER-65 RUS 实验室3
     */
    public static int CLIENT_CER65RUS_LAB3 = 2;

    /**
     * CER-65 RUS 实验室3
     */
    public static int CER65RUS_LAB3 = 3;

    /**
     * 往实体管理器里设置地图
     * @param type 类型
     */
    public static void setMapIntoEntityManager(int type) {
        if(type == DEFAULT_MAP) {
            EntityManager.getInstance().restart();

            // Entities
            Character character1 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, -200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_1));
            Character character2 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 0, 0, 1, ControllerFactory.getController(ControllerFactory.LOCAL_PLAYER_CONTROLLER));
            Character character3 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_2));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, -400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, -700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, 700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            character2.addRushTimes(100);

            EntityManager.getInstance().add(character1, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
            EntityManager.getInstance().add(character2, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
            EntityManager.getInstance().add(character3, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
        } else if(type == SERVER_CER65RUS_LAB3) {
            EntityManager.getInstance().restart();

            try {
                RenderManager.getInstance().setBackground(new Texture("pics/background_LAB3.png", 1080, 1920, 540, 960));
            } catch (IOException e) {e.printStackTrace();}

            // Players
            switch (Server.getInstance().getPlayerMax()) {
                case 1 : {
                    Character player1 = CharacterFactory.getCharacter(CharacterFactory.NUMBER_10, 0, -550, 1, null);
                    player1.setController(Server.getInstance().getController(0));
                    player1.setRushTimes(10);
                    EntityManager.getInstance().add(player1, new CharacterFactoryMessage(CharacterFactory.NUMBER_10));
                    break;
                }
                case 2 : {
                    Character player1 = CharacterFactory.getCharacter(CharacterFactory.NUMBER_10, 0, -550, 1, null);
                    player1.setController(Server.getInstance().getController(0));
                    player1.setRushTimes(10);
                    EntityManager.getInstance().add(player1, new CharacterFactoryMessage(CharacterFactory.NUMBER_10));
                    Character player2 = CharacterFactory.getCharacter(CharacterFactory.NUMBER_10, 0, 550, 1, null);
                    player2.setController(Server.getInstance().getController(1));
                    player2.setRushTimes(10);
                    EntityManager.getInstance().add(player2, new CharacterFactoryMessage(CharacterFactory.NUMBER_10));
                    break;
                }
            }

            // Entities
            Character character1 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, -200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_1));
            Character character2 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_2));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, -400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, -700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, 700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            character2.addRushTimes(100);

            EntityManager.getInstance().add(character1, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
            EntityManager.getInstance().add(character2, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
        } else if(type == CLIENT_CER65RUS_LAB3) {
            EntityManager.getInstance().restart();
            try {
                RenderManager.getInstance().setBackground(new Texture("pics/background_LAB3.png", 1080, 1920, 540, 960));
            } catch (IOException e) {e.printStackTrace();}
        } else if(type == CER65RUS_LAB3) {
            EntityManager.getInstance().restart();

            try {
                RenderManager.getInstance().setBackground(new Texture("pics/background_LAB3.png", 1080, 1920, 540, 960));
            } catch (IOException e) {e.printStackTrace();}

            // Players
            Character player1 = CharacterFactory.getCharacter(CharacterFactory.NUMBER_10, 0, 0, 1, ControllerFactory.getController(ControllerFactory.LOCAL_PLAYER_CONTROLLER));
            player1.setRushTimes(100);
            EntityManager.getInstance().add(player1, new CharacterFactoryMessage(CharacterFactory.NUMBER_10));


            // Entities
            Character character1 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, -200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_1));
            Character character2 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_2));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, -400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 400, 0, 50, 750), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, -700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, 700, 450, 50), new ObstacleFactoryMessage(ObstacleFactory.AIR_WALL));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, -400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, -100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 0, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.CONCRETE, 100, 400), new ObstacleFactoryMessage(ObstacleFactory.CONCRETE));

            EntityManager.getInstance().add(character1, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
            EntityManager.getInstance().add(character2, new CharacterFactoryMessage(CharacterFactory.TEST_CHARACTER));
        }
    }
}
