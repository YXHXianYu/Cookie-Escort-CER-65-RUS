package manager;

import entity.*;
import entity.Character;
import factory.BulletFactory;
import factory.CharacterFactory;
import factory.ObstacleFactory;
import factory.entityFactoryMessage.BulletFactoryMessage;
import factory.entityFactoryMessage.CharacterFactoryMessage;
import factory.entityFactoryMessage.FactoryMessage;
import factory.entityFactoryMessage.ObstacleFactoryMessage;
import network.pack.EntityMessage;
import network.pack.EntityMessages;
import physics.Hitbox;
import tool.MyTool;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author YXH_XianYu
 * Created On 2022-06-30
 *
 * 实体管理类
 * 储存目前存在的所有实体
 * 并进行实体之间的处理
 *
 * 是物理引擎的重要部分
 *  - 物理引擎中，碰撞箱边界重合，不代表碰撞
 */
public class EntityManager {
    /**
     * 单体模式
     */
    private static EntityManager Instance = null;

    /**
     * 单体模式，获取实例
     * @return 实例
     */
    public static EntityManager getInstance() {
        if(Instance == null)
            Instance = new EntityManager();
        return Instance;
    }

    /**
     * 储存目前存在的所有实体
     */
    private ArrayList<Entity> entityList;

    /**
     * 实体修改锁
     */
    private final Object entityListModifyLock = new Object();

    /**
     * 实体编号计数器
     */
    private int entityCount;

    /**
     * 初始化实体管理类
     */
    private EntityManager() {
        synchronized (entityListModifyLock) {
            entityList = new ArrayList<>();
        }
        entityCount = 0;
    }

    /**
     * 让所有实体进行一帧运动（移动、碰撞、伤害、死亡判断）
     *
     * 第一阶段，角色.play()刷新
     * 第二阶段，移动、碰撞、伤害判断，实体数量不改变
     *  - 第二阶段中，实体就算hp<=0，也会继续参与判断
     *    - 表现：如果有多个实体同时撞上一枚子弹，那么所有实体都会受伤
     *  - 第二阶段，只改变了实体的位置和血量
     * 第三阶段，死亡判断，实体数量会改变
     *  - 第三阶段，将死亡(hp<=0)的实体从entityList中移除
     */
    public void play() {

        if(entityList.size() == 0) {
            System.out.println("实体列表为空");
            return;
        }

        // 第一阶段，实体刷新（控制器）
        for(int i = 0; i < entityList.size(); i++) {
            if(entityList.get(i) instanceof Character)
                ((Character)entityList.get(i)).play();
        }

        // 第二阶段，以移动为核心的判断（移动、碰撞、伤害）
        for(int i = 0; i < entityList.size(); i++) {
            Entity entity = entityList.get(i);

            //if(!entity.isExist()) continue; // 实体不存在（死亡）
            if(entity instanceof Obstacle) continue; // 障碍物不会移动
            if(entity.getHitbox().getVx() == 0 && entity.getHitbox().getVy() == 0) continue; // 静止实体不会移动

            Hitbox hitbox = entity.getHitbox();
            // 移动↓
            Hitbox nextHitbox = entity.getHitbox().nextFrameHitbox(); // "true" next frame hitbox
            for(int j = 0; j < entityList.size(); j++) {
                if(i == j) continue; // 同个物体，跳过

                Entity anotherEntity = entityList.get(j);
                //if(!entityList.get(j).isExist()) // 实体不存在，跳过
                if(!anotherEntity.getHitbox().isHit(nextHitbox)) continue; // 没碰撞，跳过

                if(entity instanceof Bullet && anotherEntity instanceof Character && ((Bullet)entity).getSenderID() == (anotherEntity).getID()) continue;
                if(anotherEntity instanceof Bullet && entity instanceof Character && ((Bullet)anotherEntity).getSenderID() == (entity).getID()) continue;

                Hitbox anotherHitbox = anotherEntity.getHitbox();

                // 发生碰撞，处理碰撞
                // 1. 位置(碰撞)处理 - 改变了nextHitbox
                if(!(anotherEntity instanceof Bullet)) {
                    // 1. vx == 0, 调整y
                    // 2. vy == 0, 调整x
                    // 3. 回退x，如果仍然碰撞，x自由，调整y
                    // 4. 回退y，如果仍然碰撞，y自由，调整x
                    // 5. 回退x和y后，两者均不碰撞，则调整空余距离多的那一个轴。
                    char cood;
                    if(nextHitbox.getVx() == 0) { // 1
                        cood = 'y';
                    } else if(nextHitbox.getVy() == 0) { // 2
                        cood = 'x';
                    } else if(anotherHitbox.isHit(new Hitbox(hitbox.getX(), nextHitbox.getY(), nextHitbox.getLx(), nextHitbox.getLy()))) { // 3
                        // 注意这里的判断，新hitbox的x轴是移动之前hitbox的x轴
                        cood = 'y';
                    } else if(anotherHitbox.isHit(new Hitbox(nextHitbox.getX(), hitbox.getY(), nextHitbox.getLx(), nextHitbox.getLy()))) {
                        cood = 'x';
                    } else {
                        int disX = Math.abs(hitbox.getX() - anotherHitbox.getX()) - hitbox.getLx() - anotherHitbox.getLx();
                        int disY = Math.abs(hitbox.getY() - anotherHitbox.getY()) - hitbox.getLy() - anotherHitbox.getLy();
                        if(disX > disY) cood = 'x';
                        else cood = 'y';
                    }
                    // 调整x轴或调整y轴
                    if(cood == 'x') {
                        if(nextHitbox.getVx() > 0)
                            nextHitbox.setCoordinate(anotherHitbox.getX() - anotherHitbox.getLx() - nextHitbox.getLx(), nextHitbox.getY());
                        else
                            nextHitbox.setCoordinate(anotherHitbox.getX() + anotherHitbox.getLx() + nextHitbox.getLx(), nextHitbox.getY());
                    } else {
                        if(nextHitbox.getVy() > 0)
                            nextHitbox.setCoordinate(nextHitbox.getX(), anotherHitbox.getY() - anotherHitbox.getLy() - nextHitbox.getLy());
                        else
                            nextHitbox.setCoordinate(nextHitbox.getX(), anotherHitbox.getY() + anotherHitbox.getLy() + nextHitbox.getLy());
                    }
                }

                // 2. 伤害处理
                if(entity instanceof Bullet) {
                    anotherEntity.subHp(((Bullet)entity).getDamage());
                    entity.subHp(1);
                } else if(anotherEntity instanceof Bullet) {
                    entity.subHp(((Bullet)anotherEntity).getDamage());
                    anotherEntity.subHp(1);
                }
            }

            // 更新第i个实体的位置
            hitbox.setCoordinate(nextHitbox.getX(), nextHitbox.getY());
        }

        // 第2.5阶段，记录实体信息
        entityMessageHashMap = new HashMap<>();
        for(Entity e: entityList) {
            entityMessageHashMap.put(e.getID(), new EntityMessage(e.getID(), e.getHp(), e.getHitbox().getX(), e.getHitbox().getY(), e.getHitbox().getVx(), e.getHitbox().getVy(), e.getHitbox().getLx(), e.getHitbox().getLy()));
        }

        // 第三阶段，将死亡(hp<=0)的实体从entityList中移除
        clearDeadEntity();
    }

    private void clearDeadEntity() {
        synchronized (entityListModifyLock) {
            ArrayList<Entity> newEntityList = new ArrayList<>();
            for(Entity e: entityList) {
                if(!e.isExist()) { // 死亡
                    // TODO 播放死亡特效
                } else {
                    newEntityList.add(e);
                }
            }
            entityList = newEntityList;
        }
    }

    /**
     * 重置实体管理器
     *  - 清空实体列表
     */
    public void restart() {
        synchronized (entityListModifyLock) {
            entityList = new ArrayList<>();
        }
        entityCount = 0;
    }

    /**
     * 往游戏中添加一个新实体
     * @param entity 新实体
     * @param factoryMessage 实体信息
     */
    public void add(Entity entity, FactoryMessage factoryMessage) {
        if(entity == null) {
            System.out.println("添加了空实体");
            return;
        }
        entity.setID(++entityCount);
        synchronized (entityListModifyLock) {
            entityList.add(entity);
        }

        factoryMessage.setID(entityCount);
        factoryMessages.add(factoryMessage);
    }

    /**
     * 往游戏中添加一个新实体
     * @param entity 新实体
     */
    public void addWithoutMessage(int ID, Entity entity) {
        if(entity == null) {
            System.out.println("添加了空实体");
            return;
        }
        entity.setID(ID);
        synchronized (entityListModifyLock) {
            entityList.add(entity);
        }
    }

    /**
     * 将实体管理器内所有实体的坐标按顺序输出，便于调试
     * @return 坐标序列字符串
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < entityList.size(); i++) {
            str.append(i + 1).append(": (")
                    .append(entityList.get(i).getHitbox().getX())
                    .append(", ")
                    .append(entityList.get(i).getHitbox().getY())
                    .append(")")
                    .append(entityList.get(i).isExist())
                    .append("     ");
        }
        return str.toString();
    }

    /**
     * 按给定方式排序实体列表
     * @param comparator 比较器
     */
    public void sort(Comparator<Entity> comparator) {
        synchronized (entityListModifyLock) {
            entityList.sort(comparator);
        }
    }

    /**
     * 获取实体列表中的第index个实体
     * @param index 编号（从0开始）
     * @return 实体
     */
    public Entity getEntity(int index) {
        return entityList.get(index);
    }

    /**
     * 获取实体列表的大小
     * @return 大小
     */
    public int getEntitySize() {
        return entityList.size();
    }

    /**
     * 获取实体列表的一份复制
     * @return 实体列表的一份复制
     */
    public ArrayList<Entity> getEntityListClone() {
        ArrayList<Entity> newEntityList = new ArrayList<>();
        for(Entity i: entityList)
            newEntityList.add(new Entity(i)); // deep copy
        return newEntityList;
    }

    /**
     * 获取实体列表的一份引用
     * @return 实体列表的一份引用
     */
    public ArrayList<Entity> getEntityListReference() {
        return entityList;
    }

    /* ----- ----- 网络部分 ----- ----- */

    private ArrayList<FactoryMessage> factoryMessages = new ArrayList<>();

    private HashMap<Integer, EntityMessage> entityMessageHashMap = new HashMap<>();

    public EntityMessages getEntityMessages() {
        EntityMessages entityMessages = new EntityMessages(entityMessageHashMap, factoryMessages);
        factoryMessages = new ArrayList<>();
        entityMessageHashMap = new HashMap<>();
        return entityMessages;
    }

    private int playSoundCnt = 0;

    public void useEntityMessages(EntityMessages entityMessagesPack) {
        if(playSoundCnt > 0) playSoundCnt--;

        ArrayList<FactoryMessage> factoryMessages = entityMessagesPack.getFactoryMessages();
        HashMap<Integer, EntityMessage> entityMessageHashMap = entityMessagesPack.getEntityMessageHashMap();

        for(FactoryMessage i: factoryMessages) {
            if(i instanceof CharacterFactoryMessage) {
                addWithoutMessage(i.getID(), CharacterFactory.getCharacter(i.getType(), 0, 0, 1000));
            } else if(i instanceof ObstacleFactoryMessage) {
                addWithoutMessage(i.getID(), ObstacleFactory.getObstacle(i.getType(), 0, 0));
            } else if(i instanceof BulletFactoryMessage) {
                if(playSoundCnt <= 0) {
                    playSoundCnt = 2;
                    MyTool.playSound(BulletFactory.getSoundEffect(i.getType()));
                }
                addWithoutMessage(i.getID(), BulletFactory.getBullet(i.getType()));
            } else {
                System.out.println("Factory Message Error.");
            }
        }

        for(int i = 0; i < entityList.size(); i++) {
            Entity entity = entityList.get(i);
            EntityMessage entityMessage = entityMessageHashMap.get(entity.getID());
            if(entityMessage == null) return;

            entity.setHp(entityMessage.getHp());
            entity.getHitbox().setCoordinate(entityMessage.getX(), entityMessage.getY());
            entity.getHitbox().setVelocity(entityMessage.getVx(), entityMessage.getVy());
            entity.getHitbox().setHitboxLength(entityMessage.getLx(), entityMessage.getLy());
        }
        clearDeadEntity();
    }
}
