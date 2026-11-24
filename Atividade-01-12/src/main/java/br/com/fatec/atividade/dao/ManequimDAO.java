package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Manequim;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ManequimDAO {
    private ManequimDAO() {}

    public static void inserir(String material, String cor, String roupa) throws SQLException {
        String sql = "INSERT INTO manequim (material, cor, roupa) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, material);
            statement.setString(2, cor);
            statement.setString(3, roupa);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String material, String cor, String roupa) throws SQLException {
        String sql = "UPDATE manequim SET material = ?, cor = ?, roupa = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, material);
            statement.setString(2, cor);
            statement.setString(3, roupa);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM manequim WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Manequim> listar() throws SQLException {
        List<Manequim> registros = new ArrayList<>();
        String sql = "SELECT * FROM manequim ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Manequim> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM manequim WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Manequim mapear(ResultSet resultSet) throws SQLException {
        Manequim registro = new Manequim(resultSet.getString("material"), resultSet.getString("cor"), resultSet.getString("roupa"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
