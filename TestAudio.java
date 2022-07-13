import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * @author YXH_XianYu
 * Created On 2022-07-13
 */
public class TestAudio {
    public static void main(String[] args) throws Exception {
        InputStream input = TestAudio.class.getClassLoader().getResourceAsStream("sound/pistol.wav");
        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.
                getAudioInputStream( input );
        clip.open(ais);

        InputStream input2 = TestAudio.class.getClassLoader().getResourceAsStream("sound/LOR_Nervous.wav");
        Clip clip2 = AudioSystem.getClip();
        AudioInputStream ais2 = AudioSystem.
                getAudioInputStream( input2 );
        clip2.open(ais2);

        // loop continuously
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip2.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                JOptionPane.showMessageDialog(null, "Close to exit!");
            }
        });
    }
}
