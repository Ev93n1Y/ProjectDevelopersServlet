package repository;

import config.DatabaseManagerConnector;
import entities.dao.ProjectDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements Repository<ProjectDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT =
            "INSERT INTO projects (name, company_id, customer_id, cost, creation_date) VALUES (?,?,?,?,?)";
    private static final String SELECT_BY_ID =
            "SELECT id, name, company_id, customer_id, cost, creation_date FROM projects WHERE id = ?";
    private static final String SELECT_BY_NAME =
            "SELECT id, name, company_id, customer_id, cost, creation_date FROM projects WHERE name = ?";
    private static final String UPDATE_BY_ID = "UPDATE projects SET " +
            "name = ?, company_id = ?, customer_id = ?, cost = ?, creation_date = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM projects WHERE id = ?";
    private static final String SELECT_PROJECTS =
            "SELECT p.id, p.name, p.company_id, p.customer_id, p.cost, p.creation_date," +
                    " COUNT(d_p.developer_id) FROM projects AS p " +
                    "JOIN developers_projects AS d_p ON p.id = d_p.project_id " +
                    "GROUP BY p.creation_date , p.id " +
                    "ORDER BY p.creation_date";


    public ProjectRepository(DatabaseManagerConnector connector) {
        this.connector = connector;
    }

    @Override
    public ProjectDao save(ProjectDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dao.getName());
            statement.setInt(2, dao.getCompany_id());
            statement.setInt(3, dao.getCustomer_id());
            System.err.println("-");
            statement.setInt(4, dao.getCost());
            System.err.println("--");
            statement.setDate(5, dao.getCreation_date());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Project not created! " + e.getMessage());
        }
        return dao;
    }

    @Override
    public List<ProjectDao> selectById(Integer id) {
        List<ProjectDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Project not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Project not found! " + e.getMessage());
        }
    }

    public List<ProjectDao> selectByDepartment(String name) {
        List<ProjectDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Project not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Project not found! " + e.getMessage());
        }
    }

    @Override
    public ProjectDao updateById(Integer id, ProjectDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, dao.getName());
            statement.setInt(2, dao.getCompany_id());
            statement.setInt(3, dao.getCustomer_id());
            statement.setInt(4, dao.getCost());
            statement.setDate(5, dao.getCreation_date());
            statement.setInt(6, id);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                    dao.setName(generatedKeys.getString(2));
                    dao.setCompany_id(generatedKeys.getInt(3));
                    dao.setCustomer_id(generatedKeys.getInt(4));
                    dao.setCost(generatedKeys.getInt(5));
                    dao.setCreation_date(generatedKeys.getDate(6));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Project not updated! " + e.getMessage());
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
            throw new RuntimeException("Project not deleted! " + e.getMessage());
        }
    }

    public List<ProjectDao> selectAllProjects() {
        List<ProjectDao> daoList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PROJECTS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProjectDao dao = new ProjectDao();
                dao.setId(resultSet.getInt(1));
                dao.setName(resultSet.getString(2));
                dao.setCompany_id(resultSet.getInt(3));
                dao.setCustomer_id(resultSet.getInt(4));
                dao.setCost(resultSet.getInt(5));
                dao.setCreation_date(resultSet.getDate(6));
                daoList.add(dao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return daoList;
    }

    private List<ProjectDao> convert(ResultSet resultSet) throws SQLException {
        List<ProjectDao> daoList = new ArrayList<>();
        ProjectDao dao;
        while (resultSet.next()) {
            dao = new ProjectDao();
            dao.setId(resultSet.getInt("id"));
            dao.setName(resultSet.getString("name"));
            dao.setCompany_id(resultSet.getInt("company_id"));
            dao.setCustomer_id(resultSet.getInt("customer_id"));
            dao.setCost(resultSet.getInt("cost"));
            dao.setCreation_date(resultSet.getDate("creation_date"));
            daoList.add(dao);
        }
        return daoList;
    }
}
