package repository;

import config.DatabaseManagerConnector;
import entities.dao.CompanyDao;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import java.sql.*;

public class CompanyRepository implements Repository<CompanyDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT = "INSERT INTO companies (name, location) VALUES (?,?)";
    private static final String SELECT_BY_ID = "SELECT id, name, location FROM companies WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id, name, location FROM companies WHERE name = ?";
    private static final String UPDATE_BY_ID = "UPDATE companies SET name = ?, location = ? WHERE id= ?";
    private static final String DELETE_BY_ID = "DELETE FROM companies WHERE id = ?";

    public CompanyRepository(DatabaseManagerConnector connector) {
        this.connector = connector;
    }

    @Override
    public CompanyDao save(CompanyDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dao.getName());
            statement.setString(2, dao.getLocation());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dao.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return dao;
    }

    @Override
    public CompanyDao selectById(Integer id) {
        ResultSet resultSet;
        CompanyDao dao;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
            return dao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompanyDao selectByName(String name) {
        ResultSet resultSet;
        CompanyDao dao;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
            return dao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CompanyDao updateById(Integer id, CompanyDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, dao.getName());
            statement.setString(2, dao.getLocation());
            statement.setInt(3, id);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                    dao.setName(generatedKeys.getString(2));
                    dao.setLocation(generatedKeys.getString(3));
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

    private CompanyDao convert(ResultSet resultSet) throws SQLException {
        CompanyDao companyDao = new CompanyDao();
        while (resultSet.next()) {
            companyDao.setId(resultSet.getInt("id"));
            companyDao.setName(resultSet.getString("name"));
            companyDao.setLocation(resultSet.getString("location"));
        }
        return companyDao;
    }
}
