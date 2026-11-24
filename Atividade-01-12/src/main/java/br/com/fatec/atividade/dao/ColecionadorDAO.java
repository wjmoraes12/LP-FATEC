package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Colecionador;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ColecionadorDAO {
    private ColecionadorDAO() {}

    public static void inserir(String nome, int quantidadePecas, String especialidade) throws SQLException {
        String sql = "INSERT INTO colecionador (nome, quantidade_pecas, especialidade) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setInt(2, quantidadePecas);
            statement.setString(3, especialidade);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, int quantidadePecas, String especialidade) throws SQLException {
        String sql = "UPDATE colecionador SET nome = ?, quantidade_pecas = ?, especialidade = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setInt(2, quantidadePecas);
            statement.setString(3, especialidade);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM colecionador WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Colecionador> listar() throws SQLException {
        List<Colecionador> registros = new ArrayList<>();
        String sql = "SELECT * FROM colecionador ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Colecionador> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM colecionador WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Colecionador mapear(ResultSet resultSet) throws SQLException {
        Colecionador registro = new Colecionador(resultSet.getString("nome"), resultSet.getInt("quantidade_pecas"), resultSet.getString("especialidade"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
