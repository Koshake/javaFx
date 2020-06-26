package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public Button buttonSend;
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;

    public void clickButtonSend() {
        textArea.setText(textArea.getText() + textField.getText() + "\n");
        textField.clear();
    }

}
