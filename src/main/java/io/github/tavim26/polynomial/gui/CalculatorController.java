package io.github.tavim26.polynomial.gui;

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

    private final Operations operations = new Operations();
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

    @FXML
    private void onClearClick() {
        activeField.clear();
    }

    // ---------- Operations ----------

    @FXML
    private void onAddClick() {
        calculate(() -> operations.add(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onSubtractClick() {
        calculate(() -> operations.subtract(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onMultiplyClick() {
        calculate(() -> operations.multiply(parse(field1, "P1"), parse(field2, "P2")));
    }

    @FXML
    private void onDerivativeClick() {
        calculate(() -> operations.derivate(parse(field1, "P1")));
    }

    @FXML
    private void onIntegralClick() {
        calculate(() -> operations.integrate(parse(field1, "P1")));
    }

    private void calculate(Supplier<Polynomial> operation) {
        try {
            resultField.setText(operation.get().toString());
            errorLabel.setText("");
        } catch (InvalidPolynomialException e) {
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