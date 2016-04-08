package simple;

import javax.swing.JOptionPane;

public class Simple {

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "simple.main()", JOptionPane.ERROR_MESSAGE);
        }
    }
}
