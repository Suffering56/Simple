package simple;

import com.toedter.calendar.JCalendar;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Formatter;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.swixml.SwingEngine;

public class Main {

    public Main() throws Exception {
        swix = new SwingEngine(this);
        reportModel = new DefaultTableModel(0, 6);
        swix.getTaglib().registerTag("Calendar", JCalendar.class);
        swix.getTaglib().registerTag("headerLabel", HeaderLabel.class);
        swix.render("xml/main.xml").setVisible(true);
        init();
    }

    //Registration submit button
    private void regSubmitBtnActionPerformed(ActionEvent evt) {
        reg.submit();
    }

    //Report clear button
    private void reportClearBtnActionPerformed(ActionEvent evt) {
        reportModel.setRowCount(1);
    }

    //Calendar
    private void jCalendarListener(PropertyChangeEvent evt) {
        Calendar c = Calendar.getInstance();
        c.setTime(jCalendar.getDate());
        String stringDate = toStringDateConverter(c);
        selectedDateLabel.setText("Selected Date: " + stringDate);
    }

    private String toStringDateConverter(Calendar c) {
        return new Formatter().format("%1$td-%1$tm-%1$tY", c).toString();
    }

    //Media
    private void nextImageBtnActionPerformed(ActionEvent evt) {
        currentImageId++;
        if (currentImageId > 5) {
            currentImageId = 1;
        }
        changeImage();
    }

    private void prevImageBtnActionPerformed(ActionEvent evt) {
        currentImageId--;
        if (currentImageId < 1) {
            currentImageId = 5;
        }
        changeImage();
    }

    private void changeImage() {
        BufferedImage myPicture;
        try {
            URL img = getClass().getResource("/images/" + currentImageId + ".jpg");
            if (img != null) {
                errorMsgLabel.setText(null);
                myPicture = ImageIO.read(img);
                imgLabel.setIcon(new ImageIcon(myPicture));
            } else {
                throw new IOException("Image not found");
            }
        } catch (IOException ex) {
            errorMsgLabel.setText("Image Read Error: " + ex);
        }
    }

    //Left-sidebar(WEST) buttons (Registration, Report, Calendar, Media)
    public Action switchContent = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e != null) {
                errorMsgLabel.setText(null);
                int id = Integer.valueOf(e.getActionCommand());
                previousPanel.setVisible(false);
                contentPages[id].setVisible(true);
                previousPanel = contentPages[id];
                JButton sourceBtn = (JButton) e.getSource();
                header.setText(sourceBtn.getText());
            }
        }
    };
    public JLabel errorMsgLabel;
    private JPanel[] contentPages;
    private JPanel previousPanel;
    public JPanel registrationPanel;
    public JPanel reportPanel;
    public JPanel calendarPanel;
    public JPanel mediaPanel;
    //Registration components
    public JTextField userNameField;
    public JTextField eMailField;
    public JPasswordField passwordField;
    public JPasswordField confirmField;
    public JSpinner ageSpinner;
    public ButtonGroup genderGroup;
    public JComboBox colorComboBox;
    public JCheckBox hideProfileCheckBox;
    public JButton regSubmitBtn;
    public JCheckBox requiredCheckBox;
    //Table components
    public JTable reportTable;
    public DefaultTableModel reportModel;
    public JButton reportClearBtn;
    //Calendar components
    public JCalendar jCalendar;
    public HeaderLabel selectedDateLabel;
    private final SwingEngine swix;
    public JFrame mainFrame;
    private Registration reg;
    public HeaderLabel header;
    public JPanel contentPanel;
    public JScrollPane contentScroll;
    //Media components
    public JLabel imgLabel;
    public JButton nextImageBtn;
    public JButton prevImageBtn;
    private int currentImageId = 1;

    private void init() {
        //General init
        mainFrame.setLocationRelativeTo(null);
        contentPages = new JPanel[]{registrationPanel, reportPanel, calendarPanel, mediaPanel};
        previousPanel = registrationPanel;

        //Registration init
        reg = new Registration(this);
        regSubmitBtn.addActionListener(this::regSubmitBtnActionPerformed);

        //Report init
        reportTable.setModel(reportModel);
        reportModel.addRow(new Object[]{"user name", "e-mail", "age", "gender", "color", "profile"});
        reportClearBtn.addActionListener(this::reportClearBtnActionPerformed);
        reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //set table column width
        int n = reportTable.getColumnCount();
        for (int i = 0; i < n; i++) {
            reportTable.getColumnModel().getColumn(i).setPreferredWidth((contentPanel.getWidth() - 50) / n);
        }

        //Calendar init
        jCalendar.getDayChooser().addPropertyChangeListener(this::jCalendarListener);
        selectedDateLabel.setText("Selected Date: " + toStringDateConverter(Calendar.getInstance()));

        //Media init
        nextImageBtn.addActionListener(this::nextImageBtnActionPerformed);
        prevImageBtn.addActionListener(this::prevImageBtnActionPerformed);
        imgLabel.setSize(new Dimension(450, 230));
        prevImageBtn.setPreferredSize(new Dimension(imgLabel.getWidth() / 2, 25));
        nextImageBtn.setPreferredSize(prevImageBtn.getPreferredSize());
        changeImage();
    }
}
