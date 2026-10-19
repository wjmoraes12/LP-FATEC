package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class MascarasController {

    // Aba Máscara
    @FXML private TextField txtMascaraOrigem, txtMascaraMaterial, txtMascaraEstilo;
    @FXML private HBox boxMascara;

    // Aba Colecionador
    @FXML private TextField txtColecionadorNome, txtColecionadorPecas, txtColecionadorEspecialidade;
    @FXML private HBox boxColecionador;

    // Aba Exposição
    @FXML private TextField txtExposicaoNome, txtExposicaoLocal, txtExposicaoQuantidade;
    @FXML private HBox boxExposicao;

    @FXML private TextArea txtLog;

    private Mascara mascara;
    private Colecionador colecionador;
    private Exposicao exposicao;

    // ---------- Criar os objetos ----------

    @FXML
    private void onCriarMascara() {
        if (vazio(txtMascaraOrigem, txtMascaraMaterial, txtMascaraEstilo)) return;
        mascara = new Mascara(texto(txtMascaraOrigem), texto(txtMascaraMaterial), texto(txtMascaraEstilo));
        boxMascara.setDisable(false);
        log("Máscara criada.");
    }

    @FXML
    private void onCriarColecionador() {
        if (vazio(txtColecionadorNome, txtColecionadorPecas, txtColecionadorEspecialidade)) return;
        try {
            int pecas = lerInt(txtColecionadorPecas);
            colecionador = new Colecionador(texto(txtColecionadorNome), pecas, texto(txtColecionadorEspecialidade));
            boxColecionador.setDisable(false);
            log("Colecionador criado.");
        } catch (NumberFormatException e) {
            log("Quantidade de peças inválida (use um número inteiro).");
        }
    }

    @FXML
    private void onCriarExposicao() {
        if (vazio(txtExposicaoNome, txtExposicaoLocal, txtExposicaoQuantidade)) return;
        try {
            int quantidade = lerInt(txtExposicaoQuantidade);
            exposicao = new Exposicao(texto(txtExposicaoNome), texto(txtExposicaoLocal), quantidade);
            boxExposicao.setDisable(false);
            log("Exposição criada.");
        } catch (NumberFormatException e) {
            log("Quantidade de máscaras inválida (use um número inteiro).");
        }
    }

    // ---------- Ações dos objetos ----------

    @FXML private void onMascaraExibirDetalhes()         { log(mascara.exibirDetalhes()); }
    @FXML private void onMascaraRestaurar()              { log(mascara.restaurar()); }
    @FXML private void onMascaraCatalogar()              { log(mascara.catalogar()); }

    @FXML private void onColecionadorAdicionarMascara()  { log(colecionador.adicionarMascara()); }
    @FXML private void onColecionadorPesquisarOrigem()   { log(colecionador.pesquisarOrigem()); }
    @FXML private void onColecionadorApresentarColecao() { log(colecionador.apresentarColecao()); }

    @FXML private void onExposicaoAbrir()                { log(exposicao.abrirExposicao()); }
    @FXML private void onExposicaoInformarAcervo()       { log(exposicao.informarAcervo()); }
    @FXML private void onExposicaoEncerrar()             { log(exposicao.encerrarExposicao()); }

    @FXML private void onLimpar()                        { txtLog.clear(); }

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

    private int lerInt(TextField campo) {
        int valor = Integer.parseInt(texto(campo));
        if (valor < 0) throw new NumberFormatException();
        return valor;
    }

    private void log(String mensagem) {
        txtLog.appendText(mensagem + "\n");
    }
}
