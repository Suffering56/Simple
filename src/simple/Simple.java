package simple;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Simple {

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception ex) {
            Logger.getLogger(Simple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
