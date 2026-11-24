package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Bicicleta;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BicicletaDAO {
    private BicicletaDAO() {}

    public static void inserir(String marca, int aro, String tipoFreio) throws SQLException {
        String sql = "INSERT INTO bicicleta (marca, aro, tipo_freio) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, marca);
            statement.setInt(2, aro);
            statement.setString(3, tipoFreio);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String marca, int aro, String tipoFreio) throws SQLException {
        String sql = "UPDATE bicicleta SET marca = ?, aro = ?, tipo_freio = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, marca);
            statement.setInt(2, aro);
            statement.setString(3, tipoFreio);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM bicicleta WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Bicicleta> listar() throws SQLException {
        List<Bicicleta> registros = new ArrayList<>();
        String sql = "SELECT * FROM bicicleta ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Bicicleta> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM bicicleta WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Bicicleta mapear(ResultSet resultSet) throws SQLException {
        Bicicleta registro = new Bicicleta(resultSet.getString("marca"), resultSet.getInt("aro"), resultSet.getString("tipo_freio"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
