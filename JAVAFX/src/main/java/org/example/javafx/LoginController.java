package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private static final String USUARIO = "admin";
    private static final String SENHA = "1234";

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblResultado;

    @FXML
    private void onCalcular() {
        if (USUARIO.equals(txtUsuario.getText().trim()) && SENHA.equals(txtSenha.getText())) {
            lblResultado.setText("Login realizado com sucesso!");
        } else {
            lblResultado.setText("Usuário ou senha incorretos.");
        }
    }
}