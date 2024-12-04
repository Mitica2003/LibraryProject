package database;

import java.sql.*;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD = "Mitica!2003";
    private static final int TIMEOUT = 5;
    private Connection connection;

    public JDBConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD);
            createTables();
            modifyTables(schema);
            createTableOrder();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author varchar(500) NOT NULL," +
                " title varchar(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " amount int(11) NOT NULL," +
                " price decimal(10,2) NOT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        statement.execute(sql);
    }

    private void modifyTables(String schema) throws SQLException {
        String sql1 = "ALTER TABLE book ADD COLUMN amount INTEGER DEFAULT 0;";
        String sql2 = "ALTER TABLE book ADD COLUMN price DECIMAL(10, 2) DEFAULT 0.0;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = connection.getMetaData().getColumns(null, schema, "book", "amount");
            if (!resultSet.next()) {
                statement.executeUpdate(sql1);
            }

            resultSet = connection.getMetaData().getColumns(null, schema, "book", "price");
            if (!resultSet.next()) {
                statement.executeUpdate(sql2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableOrder() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS sale(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " title varchar(500) NOT NULL," +
                " quantity INTEGER DEFAULT 0," +
                " price DECIMAL(10, 2) DEFAULT 0.0," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {
        return connection;
    }
}
