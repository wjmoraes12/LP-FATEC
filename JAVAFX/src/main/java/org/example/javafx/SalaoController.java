package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SalaoController {

    // Aba Cliente
    @FXML private TextField txtClienteNome, txtClienteTelefone, txtClienteTipoCabelo;
    @FXML private HBox boxCliente;

    // Aba Corte
    @FXML private TextField txtCorteEstilo, txtCorteComprimento, txtCortePreco, txtCorteNovoEstilo;
    @FXML private VBox boxCorte;

    // Aba Cabeleireiro
    @FXML private TextField txtCabeleireiroNome, txtCabeleireiroEspecialidade, txtCabeleireiroAnos;
    @FXML private HBox boxCabeleireiro;

    @FXML private TextArea txtLog;

    private Cliente cliente;
    private Corte corte;
    private Cabeleireiro cabeleireiro;

    // ---------- Criar os objetos ----------

    @FXML
    private void onCriarCliente() {
        if (vazio(txtClienteNome, txtClienteTelefone, txtClienteTipoCabelo)) return;
        cliente = new Cliente(texto(txtClienteNome), texto(txtClienteTelefone), texto(txtClienteTipoCabelo));
        boxCliente.setDisable(false);
        log("Cliente criado.");
    }

    @FXML
    private void onCriarCorte() {
        if (vazio(txtCorteEstilo, txtCorteComprimento, txtCortePreco)) return;
        try {
            double preco = lerDouble(txtCortePreco);
            corte = new Corte(texto(txtCorteEstilo), texto(txtCorteComprimento), preco);
            boxCorte.setDisable(false);
            log("Corte criado.");
        } catch (NumberFormatException e) {
            log("Preço inválido.");
        }
    }

    @FXML
    private void onCriarCabeleireiro() {
        if (vazio(txtCabeleireiroNome, txtCabeleireiroEspecialidade, txtCabeleireiroAnos)) return;
        try {
            int anos = lerInt(txtCabeleireiroAnos);
            cabeleireiro = new Cabeleireiro(texto(txtCabeleireiroNome), texto(txtCabeleireiroEspecialidade), anos);
            boxCabeleireiro.setDisable(false);
            log("Cabeleireiro criado.");
        } catch (NumberFormatException e) {
            log("Anos de experiência inválidos (use um número inteiro).");
        }
    }

    // ---------- Ações dos objetos ----------

    @FXML private void onClienteAgendarCorte()              { log(cliente.agendarCorte()); }
    @FXML private void onClienteEscolherEstilo()            { log(cliente.escolherEstilo()); }
    @FXML private void onClienteAtualizarTelefone()         { log(cliente.atualizarTelefone()); }

    @FXML private void onCorteRealizar()                    { log(corte.realizarCorte()); }
    @FXML private void onCorteExibirPreco()                 { log(corte.exibirPreco()); }

    @FXML
    private void onCorteAlterarEstilo() {
        String novoEstilo = texto(txtCorteNovoEstilo);
        if (novoEstilo.isEmpty()) {
            log("Informe o novo estilo.");
            return;
        }
        log(corte.alterarEstilo(novoEstilo));
        txtCorteEstilo.setText(novoEstilo); // mantém o campo igual ao estado do objeto
        txtCorteNovoEstilo.clear();
    }

    @FXML private void onCabeleireiroAtender()              { log(cabeleireiro.atenderCliente()); }
    @FXML private void onCabeleireiroDemonstrar()           { log(cabeleireiro.demonstrarEspecialidade()); }
    @FXML private void onCabeleireiroInformarExperiencia()  { log(cabeleireiro.informarExperiencia()); }

    @FXML private void onLimpar()                           { txtLog.clear(); }

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
