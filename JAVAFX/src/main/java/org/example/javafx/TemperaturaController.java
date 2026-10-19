package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TemperaturaController {

    @FXML private TextField txtTemperatura;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        try {
            float temperatura = Float.parseFloat(txtTemperatura.getText().trim().replace(',', '.'));

            if (temperatura >= 18) {
                mostrar("Temperatura Agradável");
            } else {
                mostrar("Frio");
            }
        } catch (NumberFormatException e) {
            mostrar("Digite um valor numérico válido.");
        }
    }

    // Mostra o texto e ajusta a janela para o resultado não ficar cortado
    private void mostrar(String texto) {
        lblResultado.setText(texto);
        lblResultado.getScene().getWindow().sizeToScene();
    }
}