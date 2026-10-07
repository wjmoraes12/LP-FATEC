package org.example.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private void abrirTela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onMultiplo()    { abrirTela("multiplo.fxml", "Verificação de Múltiplo"); }
    @FXML private void onLogin()       { abrirTela("login.fxml", "Sistema de Login Simplificado"); }
    @FXML private void onDesconto()    { abrirTela("desconto.fxml", "Compra com Desconto"); }
    @FXML private void onTemperatura() { abrirTela("temperatura.fxml", "Temperatura"); }
    @FXML private void onCamelo()      { abrirTela("camelo.fxml", "Os 35 Camelos"); }
    @FXML private void onViagem()      { abrirTela("viagem.fxml", "Calculadora de Viagem"); }
    @FXML private void onAluguel()     { abrirTela("aluguel.fxml", "Aluguel de Carro"); }

    @FXML
    private void onSair() {
        Platform.exit();
    }
}