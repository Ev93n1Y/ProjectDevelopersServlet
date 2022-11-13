package repository;

import config.DatabaseManagerConnector;
import entities.dao.CustomerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Customer not created! " + e.getMessage());
        }
        return dao;
    }

    @Override
    public List<CustomerDao> selectById(Integer id) {
        List<CustomerDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Customer not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Customer not found! " + e.getMessage());
        }
    }

    public List<CustomerDao> selectByDepartment(String name) {
        List<CustomerDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Customer not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Customer not found" + e.getMessage());
        }
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
            throw new RuntimeException("Customer not updated" + e.getMessage());
        }
        return dao;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Customer not deleted!" + e.getMessage());
        }
    }

    private List<CustomerDao> convert(ResultSet resultSet) throws SQLException {
        List<CustomerDao> daoList = new ArrayList<>();
        CustomerDao dao;
        while (resultSet.next()) {
            dao = new CustomerDao();
            dao.setId(resultSet.getInt("id"));
            dao.setName(resultSet.getString("name"));
            dao.setEmail(resultSet.getString("email"));
            daoList.add(dao);
        }
        return daoList;
    }
}
