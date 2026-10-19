package org.example.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MenuController {

    @FXML private MenuButton menuProgramas;
    @FXML private ScrollPane scrollConteudo;
    @FXML private StackPane areaConteudo;

    // Carrega o programa dentro da própria janela, na área abaixo do menu
    private void mostrarTela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node tela = loader.load();

            // mantém a largura original da tela em vez de esticar até as bordas
            if (tela instanceof Region) {
                Region regiao = (Region) tela;
                if (regiao.getPrefWidth() > 0) {
                    regiao.setMaxWidth(regiao.getPrefWidth());
                }
            }

            areaConteudo.getChildren().setAll(tela);
            menuProgramas.setText(titulo);
            scrollConteudo.setVvalue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onMultiplo()    { mostrarTela("multiplo.fxml", "Verificação de Múltiplo"); }
    @FXML private void onLogin()       { mostrarTela("login.fxml", "Sistema de Login Simplificado"); }
    @FXML private void onDesconto()    { mostrarTela("desconto.fxml", "Compra com Desconto"); }
    @FXML private void onTemperatura() { mostrarTela("temperatura.fxml", "Temperatura"); }
    @FXML private void onCamelo()      { mostrarTela("camelo.fxml", "Os 35 Camelos"); }
    @FXML private void onViagem()      { mostrarTela("viagem.fxml", "Calculadora de Viagem"); }
    @FXML private void onAluguel()     { mostrarTela("aluguel.fxml", "Aluguel de Carro"); }

    @FXML private void onLoja()        { mostrarTela("loja.fxml", "Loja de Roupas"); }
    @FXML private void onCiclismo()    { mostrarTela("ciclismo.fxml", "Ciclismo na Trilha"); }
    @FXML private void onMascaras()    { mostrarTela("mascaras.fxml", "Exposição de Máscaras"); }
    @FXML private void onSalao()       { mostrarTela("salao.fxml", "Salão de Cabeleireiro"); }

    @FXML
    private void onSair() {
        Platform.exit();
    }
}
