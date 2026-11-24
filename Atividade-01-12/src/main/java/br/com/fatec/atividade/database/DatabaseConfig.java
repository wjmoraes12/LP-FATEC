package br.com.fatec.atividade.database;

//Ela também monta as URLs JDBC. Não abre conexão.
public final class DatabaseConfig {
    private static final String HOST = setting("DB_HOST", "localhost");
    private static final String PORT = setting("DB_PORT", "3306");
    private static final String NAME = setting("DB_NAME", "atividade_01_12");
    private static final String USER = setting("DB_USER", "root");
    private static final String PASSWORD = setting("DB_PASSWORD", "");

    private DatabaseConfig() {}

    public static String serverUrl() {
        return "jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public static String databaseUrl() {
        return "jdbc:mysql://" + HOST + ":" + PORT + "/" + databaseName()
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public static String databaseName() {
        if (!NAME.matches("[A-Za-z0-9_]+")) {
            throw new IllegalArgumentException("DB_NAME aceita apenas letras, números e sublinhado.");
        }
        return NAME;
    }

    public static String user() { return USER; }
    public static String password() { return PASSWORD; }
    public static String address() { return HOST + ":" + PORT + "/" + databaseName(); }

    private static String setting(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
