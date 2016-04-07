package simple;

import com.toedter.calendar.JCalendar;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.swixml.SwingEngine;

public class Main extends SwingEngine {

    public Main() throws Exception {
        this.reportModel = new DefaultTableModel(0, 6);
        swix.getTaglib().registerTag("Calendar", JCalendar.class);
        this.getTaglib().registerTag("headerLabel", HeaderLabel.class);
        swix.render("xml/main.xml").setVisible(true);
        init();
    }

    private void init() {
        mainFrame.setLocationRelativeTo(null);
        contentPages = new JPanel[]{commonPanel, tablePanel, calendarPanel, mediaPanel};
        previousPanel = commonPanel;

        commonSubmitBtn.addActionListener(this::submitRegBtnActionPerformed);
        jCalendar.getDayChooser().addPropertyChangeListener(this::jCalendarListener);

        reportTable.setModel(reportModel);
        reportModel.addRow(new Object[]{"user name", "e-mail", "age", "gender", "color", "profile"});
        reportClearBtn.addActionListener(this::reportClearBtnActionPerformed);
        //  reportModel.addRow(new Object[]{});

        selectedDateLabel.setText("Selected Date: " + toStringDateConverter(Calendar.getInstance()));
    }

    private void jCalendarListener(PropertyChangeEvent evt) {
        Calendar c = Calendar.getInstance();
        c.setTime(jCalendar.getDate());
        String stringDate = toStringDateConverter(c);
        selectedDateLabel.setText("Selected Date: " + stringDate);
    }

    private String toStringDateConverter(Calendar c) {
        return new Formatter().format("%1$td-%1$tm-%1$tY", c).toString();
    }

    private void submitRegBtnActionPerformed(ActionEvent evt) {
        String profileStatus = "show";
        if (hideProfileCheckBox.isSelected()) {
            profileStatus = "hide";
        }
        //reportModel.removeRow(1);
        reportModel.addRow(new Object[]{userNameField.getText(), eMailField.getText(), ageSpinner.getValue(),
            genderGroup.getSelection().getActionCommand(), colorComboBox.getSelectedItem(), profileStatus});
    }

    private void reportClearBtnActionPerformed(ActionEvent evt) {
        reportModel.setRowCount(1);
    }

    public Action switchContent = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e != null) {
                int id = Integer.valueOf(e.getActionCommand());
                previousPanel.setVisible(false);
                contentPages[id].setVisible(true);
                previousPanel = contentPages[id];
                JButton sourceBtn = (JButton) e.getSource();
                header.setText(sourceBtn.getText());
            }
        }
    };

    //Common components
    public JTextField userNameField;
    public JTextField eMailField;
    public JPasswordField passwordField;
    public JPasswordField confirmField;
    public JSpinner ageSpinner;
    public ButtonGroup genderGroup;
    public JComboBox colorComboBox;
    public JCheckBox hideProfileCheckBox;
    public JButton commonSubmitBtn;
    //Table components
    public JTable reportTable;
    private final DefaultTableModel reportModel;
    public JButton reportClearBtn;
    //Calendar components
    public JCalendar jCalendar;
    public HeaderLabel selectedDateLabel;

    //Main components
    private final SwingEngine swix = new SwingEngine(this);
    public JFrame mainFrame;
    public HeaderLabel header;
    public JPanel contentPanel;
    public JScrollPane contentScroll;

    public JPanel commonPanel;
    public JPanel tablePanel;
    public JPanel calendarPanel;
    public JPanel mediaPanel;

    private JPanel[] contentPages;
    private JPanel previousPanel;
}
