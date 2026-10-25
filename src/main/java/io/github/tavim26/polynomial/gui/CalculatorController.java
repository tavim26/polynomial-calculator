package io.github.tavim26.polynomial.gui;

import io.github.tavim26.polynomial.logic.Operations;
import io.github.tavim26.polynomial.model.Polynomial;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField field1, field2, fieldr;

    @FXML
    private Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;
    @FXML
    private Button plusButton, minusButton, slashButton, dinkusButton, circumfelxButton, dotButton, xButton, delButton;
    @FXML
    private Button addButton, subButton, mulButton, divButton, derButton, intButton;

    private TextField activeField;
    private int caretPosition1, caretPosition2;

    private Operations operations;


    @FXML
    private void initialize() {
        field1.setOnMouseClicked(e -> activeField = field1);
        field2.setOnMouseClicked(e -> activeField = field2);
        activeField = field1;

        caretPosition1 = 0;
        caretPosition2 = 0;

        operations = new Operations();

    }

    private void insertText(String text)
    {
        if (activeField != null)
        {

            if(activeField == field1)
            {
                activeField.insertText(caretPosition1, text);

                caretPosition1 = caretPosition1 + text.length();
                activeField.positionCaret(caretPosition1);

            }
            else if(activeField == field2)
            {
                activeField.insertText(caretPosition2, text);

                caretPosition2 = caretPosition2 + text.length();
                activeField.positionCaret(caretPosition2);

            }


        }
    }





    @FXML
    private void oneClick() { insertText("1"); }

    @FXML
    private void twoClick() { insertText("2"); }

    @FXML
    private void threeClick() { insertText("3"); }

    @FXML
    private void fourClick() { insertText("4"); }

    @FXML
    private void fiveClick() { insertText("5"); }

    @FXML
    private void sixClick() { insertText("6"); }

    @FXML
    private void sevenClick() { insertText("7"); }

    @FXML
    private void eightClick() { insertText("8"); }

    @FXML
    private void nineClick() { insertText("9"); }

    @FXML
    private void zeroClick() { insertText("0"); }

    @FXML
    private void plusClick() { insertText("+"); }

    @FXML
    private void minusClick() { insertText("-"); }

    @FXML
    private void slashClick() { insertText("/"); }

    @FXML
    private void dinkusClick() { insertText("*"); }

    @FXML
    private void circumfelxClick() { insertText("^"); }

    @FXML
    private void dotClick() { insertText("."); }

    @FXML
    private void xClick() { insertText("x"); }

    @FXML
    private void delClick() {
        if (activeField != null)
        {

            if(activeField == field1)
            {
                if (caretPosition1 > 0)
                {
                    activeField.positionCaret(caretPosition1 - 1);
                    activeField.deleteText(caretPosition1- 1, caretPosition1);
                    caretPosition1--;

                }

            }
            else if(activeField == field2)
            {
                if (caretPosition2 > 0)
                {
                    activeField.positionCaret(caretPosition2 - 1);
                    activeField.deleteText(caretPosition2- 1, caretPosition2);
                    caretPosition2--;


                }
            }
        }

    }


    @FXML
    private void addClick()
    {
        String polynomialText1 = field1.getText();
        String polynomialText2 = field2.getText();

        //creare polinoame din stringuri
        Polynomial polynomial1 = new Polynomial(polynomialText1);
        Polynomial polynomial2 = new Polynomial(polynomialText2);

        //efectuare operatii pe polinoame
        Polynomial resultPolynomial = operations.add(polynomial1, polynomial2);

        //afisare rezultat
        fieldr.setText(resultPolynomial.toString());

    }

    @FXML
    private void subClick() {

        String polynomialText1 = field1.getText();
        String polynomialText2 = field2.getText();

        //creare polinoame din stringuri
        Polynomial polynomial1 = new Polynomial(polynomialText1);
        Polynomial polynomial2 = new Polynomial(polynomialText2);

        //efectuare operatii pe polinoame
        Polynomial resultPolynomial = operations.subtract(polynomial1, polynomial2);

        //afisare rezultat
        fieldr.setText(resultPolynomial.toString());

    }

    @FXML
    private void mulClick() {


        String polynomialText1 = field1.getText();
        String polynomialText2 = field2.getText();

        //creare polinoame din stringuri
        Polynomial polynomial1 = new Polynomial(polynomialText1);
        Polynomial polynomial2 = new Polynomial(polynomialText2);

        //efectuare operatii pe polinoame
        Polynomial resultPolynomial = operations.multiply(polynomial1, polynomial2);

        //afisare rezultat
        fieldr.setText(resultPolynomial.toString());
    }

    @FXML
    private void divClick() {
        // Cod pentru acțiunea butonului DIV
    }

    @FXML
    private void derClick() {

        String polynomialText1 = field1.getText();

        //creare polinoame din stringuri
        Polynomial polynomial1 = new Polynomial(polynomialText1);

        //efectuare operatii pe polinoame
        Polynomial resultPolynomial = operations.derivate(polynomial1);

        //afisare rezultat
        fieldr.setText(resultPolynomial.toString());
    }

    @FXML
    private void intClick() {

        String polynomialText1 = field1.getText();

        //creare polinoame din stringuri
        Polynomial polynomial1 = new Polynomial(polynomialText1);

        //efectuare operatii pe polinoame
        Polynomial resultPolynomial = operations.integrate(polynomial1);

        //afisare rezultat
        fieldr.setText(resultPolynomial.toString());
    }
}
