package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CameloController {

    @FXML private TextField txtCamelos;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        try {
            int n = Integer.parseInt(txtCamelos.getText().trim());
            int total = n + 1; // camelo emprestado

            if (n <= 0 || total % 18 != 0) {
                lblResultado.setText("Com " + n + " camelos a divisão (1/2, 1/3 e 1/9) não fica exata. Tente 35.");
                return;
            }

            int filho1 = total / 2;
            int filho2 = total / 3;
            int filho3 = total / 9;
            int sobra = total - filho1 - filho2 - filho3;

            lblResultado.setText(String.format(
                    "Com o camelo emprestado: %d camelos%n"
                            + "Filho mais velho (1/2): %d%n"
                            + "Segundo filho (1/3): %d%n"
                            + "Filho mais novo (1/9): %d%n"
                            + "Sobram %d: 1 é devolvido ao dono e %d fica com quem ajudou.",
                    total, filho1, filho2, filho3, sobra, sobra - 1));
        } catch (NumberFormatException e) {
            lblResultado.setText("Digite apenas números inteiros.");
        }
    }
}