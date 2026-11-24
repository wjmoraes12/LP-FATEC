package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Roupa;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RoupaDAO {
    private RoupaDAO() {}

    public static void inserir(String tipo, String cor, double preco) throws SQLException {
        String sql = "INSERT INTO roupa (tipo, cor, preco) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tipo);
            statement.setString(2, cor);
            statement.setDouble(3, preco);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String tipo, String cor, double preco) throws SQLException {
        String sql = "UPDATE roupa SET tipo = ?, cor = ?, preco = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tipo);
            statement.setString(2, cor);
            statement.setDouble(3, preco);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM roupa WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Roupa> listar() throws SQLException {
        List<Roupa> registros = new ArrayList<>();
        String sql = "SELECT * FROM roupa ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Roupa> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM roupa WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Roupa mapear(ResultSet resultSet) throws SQLException {
        Roupa registro = new Roupa(resultSet.getString("tipo"), resultSet.getString("cor"), resultSet.getDouble("preco"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
