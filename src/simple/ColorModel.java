package simple;

import javax.swing.DefaultComboBoxModel;

public class ColorModel extends DefaultComboBoxModel<String> {

    public ColorModel() {
        super(new String[]{"Not selected", "Red", "Orange", "Yellow", "Green", "Blue", "Purple"});
    }
}
