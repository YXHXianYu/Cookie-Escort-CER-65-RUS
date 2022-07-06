package manager;

import entity.Character;
import entity.Entity;
import entity.Obstacle;
import factory.CharacterFactory;
import factory.ObstacleFactory;
import factory.ControllerFactory;

import java.io.IOException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-01
 */
public class TestRenderManager {
    public static void main(String[] args) throws IOException {

        // Entities
        Character character1 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, -200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_1));
        Character character2 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 0, 0, 1, ControllerFactory.getController(ControllerFactory.LOCAL_PLAYER_CONTROLLER));
        Character character3 = CharacterFactory.getCharacter(CharacterFactory.TEST_CHARACTER, 200, -300, 1, ControllerFactory.getController(ControllerFactory.TEST_CONTROLLER_2));

        //for(int i = -6; i <= 6; i++) {
        //    EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, -300, 100 * i));
        //    EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 300, 100 * i));
        //}
        //for(int i = -2; i <= 2; i++) {
        //    EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 100 * i, -600));
        //    EntityManager.getInstance().add(ObstacleFactory.getObstacle(ObstacleFactory.STONE, 100 * i, 600));
        //}

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

        GameManager.getInstance().play();
    }
}
