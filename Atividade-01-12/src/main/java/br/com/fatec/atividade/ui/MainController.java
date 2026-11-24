package br.com.fatec.atividade.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public final class MainController {
    @FXML private Label databaseStatus;

    @FXML
    private void initialize() {
        databaseStatus.setText(MainApp.getDatabaseStatus());
    }
}
