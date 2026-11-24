package br.com.fatec.atividade.ui;

import br.com.fatec.atividade.database.DatabaseConfig;
import br.com.fatec.atividade.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public final class MainApp extends Application {
    private static String databaseStatus = "Banco ainda não inicializado.";

    public static String getDatabaseStatus() { return databaseStatus; }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            DatabaseInitializer.initialize();
            databaseStatus = "Conectado a " + DatabaseConfig.address();
        } catch (SQLException | java.io.IOException | IllegalArgumentException e) {
            databaseStatus = "Sem conexão com o banco local: " + e.getMessage();
        }

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/br/com/fatec/atividade/ui/main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Atividade OO e Banco de Dados | Imagens 1 a 4");
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
