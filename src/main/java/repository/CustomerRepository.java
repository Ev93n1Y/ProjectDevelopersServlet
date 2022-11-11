package repository;

import config.DatabaseManagerConnector;
import entities.dao.CustomerDao;

import java.sql.*;

public class CustomerRepository implements Repository<CustomerDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT = "INSERT INTO customers (name, email) VALUES (?,?)";
    private static final String SELECT_BY_ID = "SELECT id, name, email FROM customers WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id, name, email FROM customers WHERE name = ?";
    private static final String UPDATE_BY_ID = "UPDATE customers SET name = ?, email = ? WHERE id= ?";
    private static final String DELETE_BY_ID = "DELETE FROM customers WHERE id = ?";

    public CustomerRepository(DatabaseManagerConnector connector) {
        this.connector = connector;
    }

    @Override
    public CustomerDao save(CustomerDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dao.getName());
            statement.setString(2, dao.getEmail());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Customer not created");
        }
        return dao;
    }

    @Override
    public CustomerDao selectById(Integer id) {
        ResultSet resultSet;
        CustomerDao dao = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }

    public CustomerDao selectByName(String name) {
        ResultSet resultSet;
        CustomerDao dao = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public CustomerDao updateById(Integer id, CustomerDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, dao.getName());
            statement.setString(2, dao.getEmail());
            statement.setInt(3, id);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                    dao.setName(generatedKeys.getString(2));
                    dao.setEmail(generatedKeys.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        Connection connection = connector.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    private CustomerDao convert(ResultSet resultSet) throws SQLException {
        CustomerDao customerDao = new CustomerDao();
        while (resultSet.next()) {
            customerDao.setId(resultSet.getInt("id"));
            customerDao.setName(resultSet.getString("name"));
            customerDao.setEmail(resultSet.getString("email"));
        }
        return customerDao;
    }
}
