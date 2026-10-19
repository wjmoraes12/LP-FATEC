package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DescontoController {

    private static final String[] PRODUTOS = {
            "Arroz (5 kg)",
            "Feijão (1 ou 2 kg)",
            "Óleo de soja (900 ml)",
            "Açúcar (1 kg)",
            "Café (250 g ou 500 g)",
            "Macarrão (500 g)",
            "Farinha (1 kg ou 500 g)",
            "Fubá (500 g)",
            "Molho/extrato de tomate (300 g)",
            "Sal refinado (1 kg)"
    };

    private static final double LIMITE_DESCONTO = 100.0; // desconto só se total > 100
    private static final double PERCENTUAL_DESCONTO = 0.10;

    @FXML private GridPane gridProdutos;
    @FXML private Label lblResultado;

    private final TextField[] txtQuantidade = new TextField[PRODUTOS.length];
    private final TextField[] txtPreco = new TextField[PRODUTOS.length];

    // Chamado automaticamente pelo FXMLLoader depois de carregar o FXML
    @FXML
    private void initialize() {
        gridProdutos.add(cabecalho("Produto"), 0, 0);
        gridProdutos.add(cabecalho("Quantidade"), 1, 0);
        gridProdutos.add(cabecalho("Preço (R$)"), 2, 0);

        for (int i = 0; i < PRODUTOS.length; i++) {
            txtQuantidade[i] = new TextField();
            txtQuantidade[i].setPromptText("0");
            txtQuantidade[i].setPrefWidth(90);

            txtPreco[i] = new TextField();
            txtPreco[i].setPromptText("0,00");
            txtPreco[i].setPrefWidth(110);

            gridProdutos.add(new Label(PRODUTOS[i]), 0, i + 1);
            gridProdutos.add(txtQuantidade[i], 1, i + 1);
            gridProdutos.add(txtPreco[i], 2, i + 1);
        }
    }

    @FXML
    private void onCalcular() {
        double total = 0;

        for (int i = 0; i < PRODUTOS.length; i++) {
            try {
                int quantidade = lerInteiro(txtQuantidade[i]);
                double preco = lerDecimal(txtPreco[i]);

                if (quantidade < 0 || preco < 0) {
                    throw new NumberFormatException();
                }
                total += quantidade * preco;
            } catch (NumberFormatException e) {
                mostrar("Valor inválido em: " + PRODUTOS[i]);
                return;
            }
        }

        String resultado = String.format("Total da compra: R$ %.2f", total);

        if (total > LIMITE_DESCONTO) {
            double desconto = total * PERCENTUAL_DESCONTO;
            resultado += String.format(
                    "\nDesconto de 10%%: R$ %.2f\nTotal com desconto: R$ %.2f",
                    desconto, total - desconto);
        } else {
            resultado += "\nA compra não possui desconto";
        }

        mostrar(resultado);
    }

    private Label cabecalho(String texto) {
        Label label = new Label(texto);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private int lerInteiro(TextField campo) {
        String texto = campo.getText().trim();
        return texto.isEmpty() ? 0 : Integer.parseInt(texto);
    }

    private double lerDecimal(TextField campo) {
        String texto = campo.getText().trim().replace(',', '.');
        return texto.isEmpty() ? 0 : Double.parseDouble(texto);
    }

    // Mostra o texto e ajusta a janela para o resultado não ficar cortado
    private void mostrar(String texto) {
        lblResultado.setText(texto);
        lblResultado.getScene().getWindow().sizeToScene();
    }
}