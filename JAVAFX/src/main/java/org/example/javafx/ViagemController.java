package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViagemController {

    private static final float KM_POR_LITRO = 12f;
    private static final float LIMITE_DESCONTO_KM = 500f; // acima disso ganha 5% de desconto
    private static final float FATOR_DESCONTO = 0.95f;

    @FXML private TextField txtDistancia;
    @FXML private TextField txtValor;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        try {
            float distancia = Float.parseFloat(txtDistancia.getText().trim().replace(',', '.'));
            float valor = Float.parseFloat(txtValor.getText().trim().replace(',', '.'));

            if (distancia <= 0 || valor <= 0) {
                mostrar("Informe valores maiores que zero.");
                return;
            }

            float qtdGasolina = distancia / KM_POR_LITRO;

            float custo;
            if (distancia > LIMITE_DESCONTO_KM) {
                custo = (qtdGasolina * valor) * FATOR_DESCONTO;
            } else {
                custo = qtdGasolina * valor;
            }

            mostrar(String.format("Valor: R$ %.2f", custo));
        } catch (NumberFormatException e) {
            mostrar("Digite valores numéricos válidos.");
        }
    }

    // Mostra o texto e ajusta a janela para o resultado não ficar cortado
    private void mostrar(String texto) {
        lblResultado.setText(texto);
        lblResultado.getScene().getWindow().sizeToScene();
    }
}