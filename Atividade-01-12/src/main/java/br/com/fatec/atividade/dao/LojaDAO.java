package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Loja;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LojaDAO {
    private LojaDAO() {}

    public static void inserir(String nome, String endereco, int quantidadeFuncionarios) throws SQLException {
        String sql = "INSERT INTO loja (nome, endereco, quantidade_funcionarios) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, endereco);
            statement.setInt(3, quantidadeFuncionarios);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, String endereco, int quantidadeFuncionarios) throws SQLException {
        String sql = "UPDATE loja SET nome = ?, endereco = ?, quantidade_funcionarios = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, endereco);
            statement.setInt(3, quantidadeFuncionarios);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM loja WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Loja> listar() throws SQLException {
        List<Loja> registros = new ArrayList<>();
        String sql = "SELECT * FROM loja ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Loja> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM loja WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Loja mapear(ResultSet resultSet) throws SQLException {
        Loja registro = new Loja(resultSet.getString("nome"), resultSet.getString("endereco"), resultSet.getInt("quantidade_funcionarios"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
