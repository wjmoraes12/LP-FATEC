package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Exposicao;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ExposicaoDAO {
    private ExposicaoDAO() {}

    public static void inserir(String nome, String local, int quantidadeMascara) throws SQLException {
        String sql = "INSERT INTO exposicao (nome, local, quantidade_mascaras) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, local);
            statement.setInt(3, quantidadeMascara);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, String local, int quantidadeMascara) throws SQLException {
        String sql = "UPDATE exposicao SET nome = ?, local = ?, quantidade_mascaras = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, local);
            statement.setInt(3, quantidadeMascara);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM exposicao WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Exposicao> listar() throws SQLException {
        List<Exposicao> registros = new ArrayList<>();
        String sql = "SELECT * FROM exposicao ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Exposicao> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM exposicao WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Exposicao mapear(ResultSet resultSet) throws SQLException {
        Exposicao registro = new Exposicao(resultSet.getString("nome"), resultSet.getString("local"), resultSet.getInt("quantidade_mascaras"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
