import java.io.File;
import java.net.URISyntaxException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-11
 */
public class TestFile {
    public static void main(String[] args) {
        try {
            //File file = new File("test.txt");
            File file = new File(TestFile.class.getClassLoader().getResource("./test.txt").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
