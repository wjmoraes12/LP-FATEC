package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CiclismoController {

    // Aba Ciclista
    @FXML private TextField txtCiclistaNome, txtCiclistaNivel, txtCiclistaVelocidade;
    @FXML private HBox boxCiclista;

    // Aba Bicicleta
    @FXML private TextField txtBicicletaMarca, txtBicicletaAro, txtBicicletaTipoFreio;
    @FXML private HBox boxBicicleta;

    // Aba Trilha
    @FXML private TextField txtTrilhaNome, txtTrilhaDistancia, txtTrilhaDificuldade;
    @FXML private HBox boxTrilha;

    @FXML private TextArea txtLog;

    private Ciclista ciclista;
    private Bicicleta bicicleta;
    private Trilha trilha;

    // ---------- Criar os objetos ----------

    @FXML
    private void onCriarCiclista() {
        if (vazio(txtCiclistaNome, txtCiclistaNivel, txtCiclistaVelocidade)) return;
        try {
            double velocidade = lerDouble(txtCiclistaVelocidade);
            ciclista = new Ciclista(texto(txtCiclistaNome), texto(txtCiclistaNivel), velocidade);
            boxCiclista.setDisable(false);
            log("Ciclista criado.");
        } catch (NumberFormatException e) {
            log("Velocidade inválida.");
        }
    }

    @FXML
    private void onCriarBicicleta() {
        if (vazio(txtBicicletaMarca, txtBicicletaAro, txtBicicletaTipoFreio)) return;
        try {
            int aro = lerInt(txtBicicletaAro);
            bicicleta = new Bicicleta(texto(txtBicicletaMarca), aro, texto(txtBicicletaTipoFreio));
            boxBicicleta.setDisable(false);
            log("Bicicleta criada.");
        } catch (NumberFormatException e) {
            log("Aro inválido (use um número inteiro).");
        }
    }

    @FXML
    private void onCriarTrilha() {
        if (vazio(txtTrilhaNome, txtTrilhaDistancia, txtTrilhaDificuldade)) return;
        try {
            double distancia = lerDouble(txtTrilhaDistancia);
            trilha = new Trilha(texto(txtTrilhaNome), distancia, texto(txtTrilhaDificuldade));
            boxTrilha.setDisable(false);
            log("Trilha criada.");
        } catch (NumberFormatException e) {
            log("Distância inválida.");
        }
    }

    // ---------- Ações dos objetos ----------

    @FXML private void onCiclistaPedalar()         { log(ciclista.pedalar()); }
    @FXML private void onCiclistaFrear()           { log(ciclista.frear()); }
    @FXML private void onCiclistaAcelerar()        { log(ciclista.acelerar()); }

    @FXML private void onBicicletaPedalar()        { log(bicicleta.pedalar()); }
    @FXML private void onBicicletaCalibrarPneu()   { log(bicicleta.calibrarPneu()); }
    @FXML private void onBicicletaFrear()          { log(bicicleta.frear()); }

    @FXML private void onTrilhaIniciar()           { log(trilha.iniciar()); }
    @FXML private void onTrilhaMapear()            { log(trilha.mapear()); }
    @FXML private void onTrilhaFinalizar()         { log(trilha.finalizar()); }

    @FXML private void onLimpar()                  { txtLog.clear(); }

    // ---------- Auxiliares ----------

    private String texto(TextField campo) {
        return campo.getText().trim();
    }

    private boolean vazio(TextField... campos) {
        for (TextField campo : campos) {
            if (texto(campo).isEmpty()) {
                log("Preencha todos os campos da aba.");
                return true;
            }
        }
        return false;
    }

    private double lerDouble(TextField campo) {
        double valor = Double.parseDouble(texto(campo).replace(',', '.'));
        if (valor < 0) throw new NumberFormatException();
        return valor;
    }

    private int lerInt(TextField campo) {
        int valor = Integer.parseInt(texto(campo));
        if (valor < 0) throw new NumberFormatException();
        return valor;
    }

    private void log(String mensagem) {
        txtLog.appendText(mensagem + "\n");
    }
}
