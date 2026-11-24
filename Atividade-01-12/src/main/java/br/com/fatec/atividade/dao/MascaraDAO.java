package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Mascara;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MascaraDAO {
    private MascaraDAO() {}

    public static void inserir(String origem, String material, String estilo) throws SQLException {
        String sql = "INSERT INTO mascara (origem, material, estilo) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, origem);
            statement.setString(2, material);
            statement.setString(3, estilo);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String origem, String material, String estilo) throws SQLException {
        String sql = "UPDATE mascara SET origem = ?, material = ?, estilo = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, origem);
            statement.setString(2, material);
            statement.setString(3, estilo);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM mascara WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Mascara> listar() throws SQLException {
        List<Mascara> registros = new ArrayList<>();
        String sql = "SELECT * FROM mascara ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Mascara> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM mascara WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Mascara mapear(ResultSet resultSet) throws SQLException {
        Mascara registro = new Mascara(resultSet.getString("origem"), resultSet.getString("material"), resultSet.getString("estilo"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
