package br.com.fatec.atividade.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//prepara o banco e as tabelas. Cria o Banco caso ele nao exista
public final class DatabaseInitializer {
    private DatabaseInitializer() {}

    public static void initialize() throws SQLException, IOException {
        String database = DatabaseConfig.databaseName();
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.serverUrl(), DatabaseConfig.user(), DatabaseConfig.password());
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + database
                    + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }

        StringBuilder script = new StringBuilder();
        try (InputStream input = DatabaseInitializer.class.getResourceAsStream("/sql/schema.sql")) {
            if (input == null) {
                throw new IOException("Arquivo sql/schema.sql não foi encontrado no projeto.");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    script.append(line).append('\n');
                }
            }
        }

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : script.toString().split(";")) {
                if (!sql.isBlank()) {
                    statement.executeUpdate(sql.trim());
                }
            }
        }
    }
}
