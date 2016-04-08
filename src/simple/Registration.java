package simple;

public class Registration {

    public Registration(Main main) {
        this.main = main;
        clearForm();
    }

    public void submit() {
        boolean correct = isRequiredCorrect();
        if (!main.requiredCheckBox.isSelected()) {
            correct = true;
            main.errorMsgLabel.setText(null);
        }
        if (correct) {
            String profileStatus = "show";
            if (main.hideProfileCheckBox.isSelected()) {
                profileStatus = "hide";
            }
            String gender = "What are you?";
            if (main.genderGroup.getSelection() != null) {
                gender = main.genderGroup.getSelection().getActionCommand();
            }

            main.reportModel.addRow(new Object[]{main.userNameField.getText(), main.eMailField.getText(), main.ageSpinner.getValue(),
                gender, main.colorComboBox.getSelectedItem(), profileStatus});
        }
        clearForm();
    }

    private boolean isRequiredCorrect() {
        main.errorMsgLabel.setText(null);
        boolean correct = true;
        String errorMsg = "Required error: ";
        if (main.userNameField.getText().isEmpty()) {
            errorMsg += "[enter user name]";
            correct = false;
        }
        if (main.eMailField.getText().isEmpty()) {
            errorMsg += "[enter e-mail]";
            correct = false;
        }
        if (!main.passwordField.getText().equals(main.confirmField.getText())) {
            errorMsg += "[passwords do not match]";
            correct = false;
        }
        if (correct) {
            main.errorMsgLabel.setText(null);
        } else {
            main.errorMsgLabel.setText(errorMsg);
        }
        return correct;
    }

    private void clearForm() {
        main.userNameField.setText("");
        main.eMailField.setText("");
        main.passwordField.setText("");
        main.confirmField.setText("");
        main.ageSpinner.setValue(20);
        main.genderGroup.clearSelection();
        main.colorComboBox.setSelectedIndex(0);
        main.hideProfileCheckBox.setSelected(false);
    }

    private final Main main;
}
