package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AluguelController {

    private static final float DIARIA = 100f;
    private static final float LIMITE_KM = 100f;
    private static final float VALOR_KM_EXCEDENTE = 0.50f;

    @FXML private TextField txtDias;
    @FXML private TextField txtKm;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        try {
            int dias = Integer.parseInt(txtDias.getText().trim());
            float km = Float.parseFloat(txtKm.getText().trim().replace(',', '.'));

            if (dias <= 0 || km < 0) {
                mostrar("Informe dias maiores que zero e uma quilometragem válida.");
                return;
            }

            float custo = dias * DIARIA;

            if (km > LIMITE_KM) {
                float kmExcedente = km - LIMITE_KM;
                custo += kmExcedente * VALOR_KM_EXCEDENTE;
            }

            mostrar(String.format("O valor total do aluguel foi de R$ %.2f", custo));
        } catch (NumberFormatException e) {
            mostrar("Digite valores numéricos válidos (os dias devem ser um número inteiro).");
        }
    }

    // Mostra o texto e ajusta a janela para o resultado não ficar cortado
    private void mostrar(String texto) {
        lblResultado.setText(texto);
        lblResultado.getScene().getWindow().sizeToScene();
    }
}