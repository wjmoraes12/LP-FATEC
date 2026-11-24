package br.com.fatec.atividade.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DAOs usam essa classe quando precisam executar consultas.
public final class ConnectionFactory {
    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.databaseUrl(),
                DatabaseConfig.user(),
                DatabaseConfig.password()
        );
    }
}
