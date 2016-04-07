package simple;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class HeaderLabel extends JPanel {

    public HeaderLabel() {
        headerLabel.setForeground(Color.MAGENTA);
        headerLabel.setFont(Font.decode("Algerian-30"));
        headerLabel.setBorder(new EmptyBorder(-5, 3, -5, 3));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(headerLabel);
    }

    public void setText(String text) {
        headerLabel.setText(text);
    }

    private final JLabel headerLabel = new JLabel();
}
