package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Ciclista;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CiclistaDAO {
    private CiclistaDAO() {}

    public static void inserir(String nome, String nivel, double velocidade) throws SQLException {
        String sql = "INSERT INTO ciclista (nome, nivel, velocidade) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, nivel);
            statement.setDouble(3, velocidade);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, String nivel, double velocidade) throws SQLException {
        String sql = "UPDATE ciclista SET nome = ?, nivel = ?, velocidade = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, nivel);
            statement.setDouble(3, velocidade);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM ciclista WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Ciclista> listar() throws SQLException {
        List<Ciclista> registros = new ArrayList<>();
        String sql = "SELECT * FROM ciclista ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Ciclista> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM ciclista WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Ciclista mapear(ResultSet resultSet) throws SQLException {
        Ciclista registro = new Ciclista(resultSet.getString("nome"), resultSet.getString("nivel"), resultSet.getDouble("velocidade"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
