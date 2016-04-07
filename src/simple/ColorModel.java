package simple;

import javax.swing.DefaultComboBoxModel;

public class ColorModel extends DefaultComboBoxModel<String> {

    public ColorModel() {
        super(new String[]{"Red", "Orange", "Yellow", "Green", "Blue", "Purple"});
    }
}
