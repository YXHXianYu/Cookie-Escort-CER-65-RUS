import manager.EntityManager;
import network.pack.Control;
import network.pack.EntityMessages;

import java.io.*;

/**
 * @author YXH_XianYu
 * Created On 2022-07-10
 */
public class TestSerialization {
    public static void main(String[] args) {
        try {
            File file = new File("test.txt");
            file.createNewFile();

            Control control = new Control();
            control.setAimX(1);
            control.setAimY(2);



            while(true) {

                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("test.txt"));
                output.flush();
                ObjectInputStream input = new ObjectInputStream(new FileInputStream("test.txt"));

                System.out.println("Output: " + control.getAimX() + ", " + control.getAimY());
                output.writeObject(control);
                output.flush();

                Control control1 = (Control) input.readObject();
                System.out.println("Input: " + control1.getAimX() + ", " + control1.getAimY());

                output.close();
                input.close();

                Thread.sleep(500);

                control.setAimX(control.getAimX() + 1);
                control.setAimY(control.getAimY() + 1);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
