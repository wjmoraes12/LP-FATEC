package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LojaController {

    // Aba Roupa
    @FXML private TextField txtRoupaTipo, txtRoupaCor, txtRoupaPreco;
    @FXML private HBox boxRoupa;

    // Aba Manequim
    @FXML private TextField txtManequimMaterial, txtManequimCor, txtManequimRoupa;
    @FXML private HBox boxManequim;

    // Aba Loja
    @FXML private TextField txtLojaNome, txtLojaEndereco, txtLojaFuncionarios;
    @FXML private HBox boxLoja;

    @FXML private TextArea txtLog;

    private Roupa roupa;
    private Manequim manequim;
    private Loja loja;

    // ---------- Criar os objetos ----------

    @FXML
    private void onCriarRoupa() {
        if (vazio(txtRoupaTipo, txtRoupaCor, txtRoupaPreco)) return;
        try {
            double preco = lerDouble(txtRoupaPreco);
            roupa = new Roupa(texto(txtRoupaTipo), texto(txtRoupaCor), preco);
            boxRoupa.setDisable(false);
            log("Roupa criada.");
        } catch (NumberFormatException e) {
            log("Preço inválido.");
        }
    }

    @FXML
    private void onCriarManequim() {
        if (vazio(txtManequimMaterial, txtManequimCor, txtManequimRoupa)) return;
        manequim = new Manequim(texto(txtManequimMaterial), texto(txtManequimCor), texto(txtManequimRoupa));
        boxManequim.setDisable(false);
        log("Manequim criado.");
    }

    @FXML
    private void onCriarLoja() {
        if (vazio(txtLojaNome, txtLojaEndereco, txtLojaFuncionarios)) return;
        try {
            int funcionarios = lerInt(txtLojaFuncionarios);
            loja = new Loja(texto(txtLojaNome), texto(txtLojaEndereco), funcionarios);
            boxLoja.setDisable(false);
            log("Loja criada.");
        } catch (NumberFormatException e) {
            log("Quantidade de funcionários inválida (use um número inteiro).");
        }
    }

    // ---------- Ações dos objetos ----------

    @FXML private void onRoupaVestir()             { log(roupa.vestir()); }
    @FXML private void onRoupaVender()             { log(roupa.vender()); }
    @FXML private void onRoupaExibirPreco()        { log(roupa.exibirPreco()); }

    @FXML private void onManequimVestirRoupa()     { log(manequim.vestirRoupa()); }
    @FXML private void onManequimExibir()          { log(manequim.exibir()); }
    @FXML private void onManequimTrocarRoupa()     { log(manequim.trocarRoupa()); }

    @FXML private void onLojaAbrir()               { log(loja.abrir()); }
    @FXML private void onLojaFechar()              { log(loja.fechar()); }
    @FXML private void onLojaExibirInformacoes()   { log(loja.exibirInformacoes()); }

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
