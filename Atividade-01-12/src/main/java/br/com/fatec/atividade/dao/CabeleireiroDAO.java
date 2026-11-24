package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Cabeleireiro;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CabeleireiroDAO {
    private CabeleireiroDAO() {}

    public static void inserir(String nome, String especialidade, int anosExperiencia) throws SQLException {
        String sql = "INSERT INTO cabeleireiro (nome, especialidade, anos_experiencia) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, especialidade);
            statement.setInt(3, anosExperiencia);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, String especialidade, int anosExperiencia) throws SQLException {
        String sql = "UPDATE cabeleireiro SET nome = ?, especialidade = ?, anos_experiencia = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, especialidade);
            statement.setInt(3, anosExperiencia);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM cabeleireiro WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Cabeleireiro> listar() throws SQLException {
        List<Cabeleireiro> registros = new ArrayList<>();
        String sql = "SELECT * FROM cabeleireiro ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Cabeleireiro> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM cabeleireiro WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Cabeleireiro mapear(ResultSet resultSet) throws SQLException {
        Cabeleireiro registro = new Cabeleireiro(resultSet.getString("nome"), resultSet.getString("especialidade"), resultSet.getInt("anos_experiencia"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
