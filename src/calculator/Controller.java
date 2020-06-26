package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

public class Controller {

    public TextField computeTextField;
    public Button btn1;
    public TextField resultTextField;
    private int operation;
    private int result;
    private int firstNum;
    private int secondNum;
    private boolean canClear;

    public void buttonClick(ActionEvent actionEvent) {
        if (canClear) {
            computeTextField.clear();
            resultTextField.clear();
        }
        Button btn = (Button) actionEvent.getSource();
        String regex = "^[1-9]";
        computeTextField.setText(computeTextField.getText() + btn.getText());
        if (btn.getText().matches(regex)) {
            resultTextField.setText(btn.getText());
        }
        canClear = false;
    }

    public void btnEqualsClick() {
        secondNum = Integer.parseInt(resultTextField.getText());
        switch (operation) {
            case 1:
                result = firstNum + secondNum;
                break;
            case 2:
                result = firstNum - secondNum;
                break;
            case 3:
                result = firstNum * secondNum;
                break;
            case 4:
                result = firstNum / secondNum;
                break;
            default:
        }
        computeTextField.setText(computeTextField.getText() + "=" + result);
        resultTextField.setText("" + result);
        canClear = true;

    }

    public void btnDivideClick() {
        operation = 4;
        firstNum = Integer.parseInt(resultTextField.getText());
        computeTextField.setText(computeTextField.getText() + "/");

    }

    public void btnMultClick() {
        operation = 3;
        firstNum = Integer.parseInt(resultTextField.getText());
        computeTextField.setText(computeTextField.getText() + "*");
    }

    public void btnPlusClick() {
        operation = 1;
        firstNum = Integer.parseInt(resultTextField.getText());
        computeTextField.setText(computeTextField.getText() + "+");
    }

    public void btnMinusClick() {
        operation = 2;
        firstNum = Integer.parseInt(resultTextField.getText());
        computeTextField.setText(computeTextField.getText() + "-");
    }
}
