package entity;

import network.pack.Control;

/**
 * @author YXH_XianYu
 * Created On 2022-07-10
 *
 * 控制器Plus
 * （带有控制包的控制器）
 */
public abstract class Controller {
    /**
     * 控制包
     */
    private Control control = new Control();

    /**
     * 控制
     */
    public void control(Character character) {
        // do nothing
    }

    /**
     * 获取控制包
     */
    public Control getControl() {
        if(control != null)
            return control;
        else
            return new Control();
    }

    /**
     * 设置控制包
     */
    public void setControl(Control control) {
        this.control = control;
    }
}
