package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManagerConnector {
    private String url;
    private Properties properties;

    public DatabaseManagerConnector(Properties properties, String user, String password) {
        init(properties, user, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    private void init(Properties properties, String user, String password) {
        url = String.format("jdbc:postgresql://%s:%s/%s", properties.get("host"), properties.get("port"), properties.get("databaseName"));
        this.properties = new Properties();
        this.properties.setProperty("user", user);
        this.properties.setProperty("password", password);
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex){
            throw  new RuntimeException("Error loading postgres driver", ex);
        }
    }
}
