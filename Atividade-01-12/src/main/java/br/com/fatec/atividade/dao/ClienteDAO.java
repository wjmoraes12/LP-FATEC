package br.com.fatec.atividade.dao;

import br.com.fatec.atividade.model.Cliente;
import br.com.fatec.atividade.database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ClienteDAO {
    private ClienteDAO() {}

    public static void inserir(String nome, String telefone, String tipoCabelo) throws SQLException {
        String sql = "INSERT INTO cliente (nome, telefone, tipo_cabelo) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, telefone);
            statement.setString(3, tipoCabelo);
            statement.executeUpdate();
        }
    }

    public static boolean atualizar(long id, String nome, String telefone, String tipoCabelo) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, telefone = ?, tipo_cabelo = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, telefone);
            statement.setString(3, tipoCabelo);
            statement.setLong(4, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public static List<Cliente> listar() throws SQLException {
        List<Cliente> registros = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                registros.add(mapear(resultSet));
            }
        }
        return registros;
    }

    public static Optional<Cliente> buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapear(resultSet));
                return Optional.empty();
            }
        }
    }

    private static Cliente mapear(ResultSet resultSet) throws SQLException {
        Cliente registro = new Cliente(resultSet.getString("nome"), resultSet.getString("telefone"), resultSet.getString("tipo_cabelo"));
        registro.setId(resultSet.getLong("id"));
        return registro;
    }
}
