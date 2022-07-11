package network.pack;

/**
 * @author YXH_XianYu
 * Created On 2022-07-06
 */
public class VersionCheck implements java.io.Serializable {
    /**
     * 大版本号
     */
    private int version1;

    /**
     * 小版本号
     */
    private int version2;

    /**
     * 构造方法
     * @param version1 大版本号
     * @param version2 小版本号
     */
    public VersionCheck(int version1, int version2) {
        this.version1 = version1;
        this.version2 = version2;
    }

    /**
     * 获取大版本号
     * @return 大版本号
     */
    public int getVersion1() {
        return version1;
    }

    /**
     * 获取小版本号
     * @return 小版本号
     */
    public int getVersion2() {
        return version2;
    }
}
