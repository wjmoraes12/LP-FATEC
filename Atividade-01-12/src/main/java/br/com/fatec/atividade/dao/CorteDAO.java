package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Corte;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CorteDAO {
    private CorteDAO() {}

    public static void inserir(String estilo, String comprimento, double preco) throws SQLException {
        String sql = "INSERT INTO corte (estilo, comprimento, preco) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, estilo);
            statement.setString(2, comprimento);
            statement.setDouble(3, preco);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String estilo, String comprimento, double preco) throws SQLException {
        String sql = "UPDATE corte SET estilo = ?, comprimento = ?, preco = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, estilo);
            statement.setString(2, comprimento);
            statement.setDouble(3, preco);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM corte WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Corte> listar() throws SQLException {
        List<Corte> registros = new ArrayList<>();
        String sql = "SELECT * FROM corte ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Corte> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM corte WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Corte mapear(ResultSet resultSet) throws SQLException {
        Corte registro = new Corte(resultSet.getString("estilo"), resultSet.getString("comprimento"), resultSet.getDouble("preco"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
