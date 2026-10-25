package io.github.tavim26.polynomial.gui;

import io.github.tavim26.polynomial.logic.DivisionResult;
import io.github.tavim26.polynomial.logic.Operations;
import io.github.tavim26.polynomial.model.InvalidPolynomialException;
import io.github.tavim26.polynomial.model.Polynomial;
import io.github.tavim26.polynomial.model.PolynomialParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.Supplier;

public class CalculatorController {

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private TextField resultField;
    @FXML
    private Label errorLabel;

    private TextField activeField;

    @FXML
    private void initialize() {
        activeField = field1;
        trackFocus(field1);
        trackFocus(field2);
        Platform.runLater(field1::requestFocus);
    }

    private void trackFocus(TextField field) {
        field.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                activeField = field;
            }
        });
    }

    // ---------- Keypad ----------

    @FXML
    private void onKeypadClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        activeField.replaceSelection(button.getText());
    }

    @FXML
    private void onDeleteClick() {
        activeField.deletePreviousChar();
    }

    @FXML
    private void onCaretLeftClick() {
        activeField.backward();
    }

    @FXML
    private void onCaretRightClick() {
        activeField.forward();
    }

    // ---------- Operations ----------

    @FXML
    private void onAddClick() {
        calculate(() -> Operations.add(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onSubtractClick() {
        calculate(() -> Operations.subtract(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onMultiplyClick() {
        calculate(() -> Operations.multiply(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onDivideClick() {
        calculate(() -> {
            DivisionResult result = Operations.divide(parse(field1, "P1"), parse(field2, "P2"));
            return "Q: " + result.quotient() + "   R: " + result.remainder();
        });
    }

    @FXML
    private void onDerivativeClick() {
        calculate(() -> Operations.derivative(parse(field1, "P1")));
    }

    @FXML
    private void onIntegralClick() {
        calculate(() -> {
            Polynomial integral = Operations.integral(parse(field1, "P1"));
            return integral.isZero() ? "C" : integral + "+C";
        });
    }

    private void calculate(Supplier<?> operation) {
        try {
            resultField.setText(String.valueOf(operation.get()));
            errorLabel.setText("");
        } catch (InvalidPolynomialException | ArithmeticException e) {
            resultField.clear();
            errorLabel.setText(e.getMessage());
        }
    }

    private static Polynomial parse(TextField field, String fieldName) {
        try {
            return PolynomialParser.parse(field.getText());
        } catch (InvalidPolynomialException e) {
            throw new InvalidPolynomialException(fieldName + ": " + e.getMessage());
        }
    }
}