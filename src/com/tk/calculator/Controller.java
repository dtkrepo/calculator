package com.tk.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private Text output;

    private long number1 = 0;
    private String operator = "";
    private boolean start = true;
    private Model model = new Model();

    @FXML
    private void processNumPad(javafx.event.ActionEvent actionEvent) {
        if (start){
            output.setText("");
            start = false;
        }
        String value = ((Button)actionEvent.getSource()).getText();
        output.setText(output.getText() + value);
    }
    @FXML
    private void processOperator(ActionEvent actionEvent) {
        String value = ((Button)actionEvent.getSource()).getText();
        performOperation(value);
    }
    private void performOperation(String value){
        if (!"=".equals(value)){
            if (!operator.isEmpty())
                return;
            operator = value;
            number1 = Long.parseLong(output.getText());
            output.setText("");
        }
        else {
            if (operator.isEmpty())
                return;
            output.setText(String.valueOf(model.calculate(number1,Long.parseLong(output.getText()),operator)));
            operator = "";
            start = true;
        }
    }
    @FXML
    private void processClear(ActionEvent actionEvent) {
        String value = ((Button)actionEvent.getSource()).getText();
        switch (value){
            case "C":
                number1 = 0;
                operator = "";
                start = true;
                output.setText("");
                break;
            case "CE":
                output.setText("");
                break;
            case "BS":
                if (output.getText().equals(""))
                    break;
                String currentOutput = output.getText();
                int len = currentOutput.length();
                output.setText(currentOutput.substring(0,len-1));
                break;
        }
    }
    @FXML
    private void processNegate() {
        String value = output.getText();
        if (value.isEmpty()){
            output.setText("-");
        }
        else if (value.substring(0,1).equals("-")){
            output.setText(value.substring(1));
        }
        else {
            output.setText("-".concat(value));
        }
    }
    @FXML
    private void processKeyTyped(KeyEvent keyEvent) {
        String typedKey = keyEvent.getCharacter();
        if (typedKey.matches("[0-9]")){
            output.setText(output.getText().concat(typedKey));
        }
        else {
            performOperation(typedKey);
        }
    }
}
