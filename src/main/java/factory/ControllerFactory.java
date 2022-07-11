package factory;

import entity.Controller;
import entity.Character;
import manager.RenderManager;
import tool.MyTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author YXH_XianYu
 * Created On 2022-07-03
 *
 * 控制器工厂类
 */
public class ControllerFactory {

    /**
     * 控制器编号：本地玩家控制器 - 1
     */
    public static final int LOCAL_PLAYER_CONTROLLER = 1;

    /**
     * 控制器编号：测试控制器1 - 2
     */
    public static final int TEST_CONTROLLER_1 = 2;

    /**
     * 控制器编号：测试控制器2 - 3
     */
    public static final int TEST_CONTROLLER_2 = 3;

    /**
     * 控制器编号：服务器玩家控制器 - 4
     */
    public static final int SERVER_PLAYER_CONTROLLER = 4;

    /**
     * 获取控制器
     * @param type 编号
     * @return 控制器
     */
    public static Controller getController(int type) {
        if(type == LOCAL_PLAYER_CONTROLLER) {
            return new Controller() {
                private boolean firstPlay = true;
                private boolean[] keyboard = new boolean[26];
                @Override
                public void control(Character character) {
                    if(firstPlay) {
                        init();
                        firstPlay = false;
                    }
                    //System.out.println("W:" + pressed['W'] + ", S:" + pressed['S'] + ", A:" + pressed['A'] + ", D:" + pressed['D']);
                    int x, y;
                    if(keyboard[0] && !keyboard['D'-'A']) y = 0;
                    else if(!keyboard[0] && keyboard['D'-'A']) y = 2;
                    else y = 1;
                    if(keyboard['W'-'A'] && !keyboard['S'-'A']) x = 0;
                    else if(!keyboard['W'-'A'] && keyboard['S'-'A']) x = 2;
                    else x = 1;

                    getControl().setMoveDirect(MyTool.directTable[x][y]);

                    Point aimPoint = new Point(MouseInfo.getPointerInfo().getLocation());
                    SwingUtilities.convertPointFromScreen(aimPoint, RenderManager.getInstance());
                    // BE CAREFUL!! 这里进行了坐标系变换 + swap(x, y)
                    getControl().setAimX((int) ((aimPoint.y - RenderManager.getInstance().getHeight() / 2) / RenderManager.getInstance().getScale()));
                    getControl().setAimY((int) ((aimPoint.x - RenderManager.getInstance().getWidth() / 2) / RenderManager.getInstance().getScale()));

                    // play
                    if(character == null) return;
                    character.move(getControl().getMoveDirect());
                    character.aim(getControl().getAimX(), getControl().getAimY());
                    if(getControl().isAttack()) {
                        character.attack();
                    }
                    if(getControl().isRush()) {
                        character.rush();
                    }
                }
                private void init() {
                    //System.out.println("init controller.");
                    RenderManager.getInstance().addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            super.keyPressed(e);
                            if(KeyEvent.VK_A <= e.getKeyCode() && e.getKeyCode() <= KeyEvent.VK_Z)
                                keyboard[e.getKeyCode() - 'A'] = true;
                            //System.out.println("Pressed: " + (char)(e.getKeyCode()));
                        }
                        @Override
                        public void keyReleased(KeyEvent e) {
                            super.keyReleased(e);
                            if(KeyEvent.VK_A <= e.getKeyCode() && e.getKeyCode() <= KeyEvent.VK_Z)
                                keyboard[e.getKeyCode() - 'A'] = false;
                        }
                    });
                    RenderManager.getInstance().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            super.mousePressed(e);
                            if(e.getButton() == MouseEvent.BUTTON1) {
                                getControl().setAttack(true);
                            } else if(e.getButton() == MouseEvent.BUTTON3) {
                                getControl().setRush(true);
                            }
                        }
                        public void mouseReleased(MouseEvent e) {
                            super.mouseReleased(e);
                            if(e.getButton() == MouseEvent.BUTTON1) {
                                getControl().setAttack(false);
                            } else if(e.getButton() == MouseEvent.BUTTON3) {
                                getControl().setRush(false);
                            }
                        }
                    });
                }
            };
        } else if(type == TEST_CONTROLLER_1) {
            return new Controller() {
                int cnt1 = 0;
                int cnt2 = 0;
                @Override
                public void control(Character character) {
                    cnt2++;
                    if(cnt2 >= 40) {
                        cnt2 = 0;
                        cnt1 = (cnt1 + 1) % 8;
                        if(cnt1 % 2 == 1)
                            character.move(cnt1);
                        else
                            character.move(0);
                    }
                }
            };
        } else if(type == TEST_CONTROLLER_2) {
            return new Controller() {
                int cnt = 0;
                @Override
                public void control(Character character) {
                    cnt++;
                    if(cnt >= 80) {
                        character.move(7);
                        if(cnt >= 120)
                            cnt = 0;
                    } else {
                        character.move(0);
                    }
                }
            };
        } else if(type == SERVER_PLAYER_CONTROLLER) {
            return new Controller() {
                @Override
                public void control(Character character) {
                    // play
                    character.move(getControl().getMoveDirect());
                    character.aim(getControl().getAimX(), getControl().getAimY());
                    if(getControl().isAttack()) {
                        character.attack();
                    }
                    if(getControl().isRush()) {
                        getControl().setRush(false);
                        character.rush();
                    }
                }
            };
        } else {
            return null;
        }
    }
}
