package repository;

import config.DatabaseManagerConnector;
import entities.dao.CompanyDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Company not created! " + e.getMessage());
        }
        return dao;
    }

    @Override
    public List<CompanyDao> selectById(Integer id) {
        List<CompanyDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Company not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CompanyDao> selectByDepartment(String name) {
        List<CompanyDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Company not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
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
            throw new RuntimeException("Company not updated! " + e.getMessage());
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
            throw new RuntimeException("Company not deleted! " + e.getMessage());
        }
    }

    private List<CompanyDao> convert(ResultSet resultSet) throws SQLException {
        List<CompanyDao> daoList = new ArrayList<>();
        CompanyDao dao;
        while (resultSet.next()) {
            dao =  new CompanyDao();
            dao.setId(resultSet.getInt("id"));
            dao.setName(resultSet.getString("name"));
            dao.setLocation(resultSet.getString("location"));
            daoList.add(dao);
        }
        return daoList;
    }
}
