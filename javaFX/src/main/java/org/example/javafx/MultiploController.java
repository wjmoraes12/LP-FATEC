package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MultiploController {

    @FXML private TextField txtNumero;
    @FXML private TextField txtBase;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            int base = Integer.parseInt(txtBase.getText().trim());

            if (base == 0) {
                lblResultado.setText("A base não pode ser zero.");
            } else if (numero % base == 0) {
                lblResultado.setText(numero + " é múltiplo de " + base);
            } else {
                lblResultado.setText(numero + " não é múltiplo de " + base);
            }
        } catch (NumberFormatException e) {
            lblResultado.setText("Digite apenas números inteiros.");
        }
    }
}