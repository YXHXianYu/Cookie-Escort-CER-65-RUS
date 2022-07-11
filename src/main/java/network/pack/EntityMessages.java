package network.pack;

import factory.entityFactoryMessage.FactoryMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author YXH_XianYu
 * Created On 2022-07-09
 *
 * 实体信息包
 *
 * 每次由服务端发给客户端，把要更新的实体信息全部给出
 */
public class EntityMessages implements java.io.Serializable {
    /**
     * 实体信息HashMap
     */
    private HashMap<Integer, EntityMessage> entityMessageHashMap;

    /**
     * 实体添加信息数组
     */
    private ArrayList<FactoryMessage> factoryMessages;

    /**
     * 构造方法
     */
    public EntityMessages(HashMap<Integer, EntityMessage> entityMessageHashMap, ArrayList<FactoryMessage> factoryMessages) {
        this.entityMessageHashMap = entityMessageHashMap;
        this.factoryMessages = factoryMessages;
    }

    /**
     * 获取实体信息HashMap
     */
    public HashMap<Integer, EntityMessage> getEntityMessageHashMap() {
        return entityMessageHashMap;
    }

    /**
     * 获取实体添加信息数组
     */
    public ArrayList<FactoryMessage> getFactoryMessages() {
        return factoryMessages;
    }
}
