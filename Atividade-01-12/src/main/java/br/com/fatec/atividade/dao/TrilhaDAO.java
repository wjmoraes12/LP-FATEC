package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Trilha;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TrilhaDAO {
    private TrilhaDAO() {}

    public static void inserir(String nome, double distancia, String dificuldade) throws SQLException {
        String sql = "INSERT INTO trilha (nome, distancia, dificuldade) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setDouble(2, distancia);
            statement.setString(3, dificuldade);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, double distancia, String dificuldade) throws SQLException {
        String sql = "UPDATE trilha SET nome = ?, distancia = ?, dificuldade = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setDouble(2, distancia);
            statement.setString(3, dificuldade);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM trilha WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Trilha> listar() throws SQLException {
        List<Trilha> registros = new ArrayList<>();
        String sql = "SELECT * FROM trilha ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Trilha> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM trilha WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Trilha mapear(ResultSet resultSet) throws SQLException {
        Trilha registro = new Trilha(resultSet.getString("nome"), resultSet.getDouble("distancia"), resultSet.getString("dificuldade"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
