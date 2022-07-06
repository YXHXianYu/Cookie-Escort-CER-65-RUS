package factory;

import entity.Character;
import manager.EntityManager;

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
     * 往实体管理器里设置地图
     * @param type 类型
     */
    public static void setMapIntoEntityManager(int type) {
        if(type == DEFAULT_MAP) {
            System.out.println("I DID by MapFactory.");

            EntityManager.getInstance().restart();

            // Entities
            Character character1 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, -200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_1));
            Character character2 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 0, 0, 1, ControllerFactory.getController(ControllerFactory.LOCAL_PLAYER_CONTROLLER));
            Character character3 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_2));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, -400, 0, 50, 750));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 400, 0, 50, 750));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, -700, 450, 50));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.AIR_WALL, 0, 700, 450, 50));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, -100, -400));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 0, -400));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 100, -400));

            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, -100, 400));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 0, 400));
            EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 100, 400));

            character2.addRushTimes(100);

            EntityManager.getInstance().add(character1);
            EntityManager.getInstance().add(character2);
            EntityManager.getInstance().add(character3);
        }
    }
}
