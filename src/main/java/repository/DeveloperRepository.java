package repository;

import config.DatabaseManagerConnector;
import entities.dao.DeveloperDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepository implements Repository<DeveloperDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT = "INSERT INTO developers (name, age, gender, salary) VALUES (?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT id, name, age, gender, salary FROM developers WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id, name, age, gender, salary FROM developers WHERE name = ?";
    private static final String UPDATE_BY_ID = "UPDATE developers SET " +
            "name = ?, age = ?, gender = ?, salary = ? WHERE id= ?";
    private static final String DELETE_BY_ID = "DELETE FROM developers WHERE id = ?";
    private static final String SELECT_TOTAL_SALARY_BY_PROJECT_ID =
            "SELECT SUM(dev.salary) FROM developers AS dev " +
                    "JOIN developers_projects AS d_p ON dev.id = d_p.developer_id " +
                    "WHERE d_p.project_id = ?";
    private static final String SELECT_ALL_DEVELOPERS_BY_PROJECT_ID =
            "SELECT dev.id, dev.name, dev.age, dev.gender, dev.salary FROM developers AS dev " +
                    "JOIN developers_projects AS d_p ON dev.id = d_p.developer_id " +
                    "WHERE d_p.project_id = ?";
    private static final String SELECT_ALL_JAVA_DEVELOPERS =
            "SELECT dev.id, dev.name, dev.age, dev.gender, dev.salary FROM developers AS dev " +
                    "JOIN developers_skills AS d_s ON dev.id = d_s.developer_id " +
                    "JOIN skills AS s ON s.id = d_s.skill_id " +
                    "WHERE s.department = 'Java'";
    private static final String SELECT_ALL_MIDDLE_DEVELOPERS =
            "SELECT dev.id, dev.name, dev.age, dev.gender, dev.salary FROM developers AS dev " +
                    "JOIN developers_skills AS d_s ON dev.id = d_s.developer_id " +
                    "JOIN skills AS s ON s.id = d_s.skill_id " +
                    "WHERE s.level = 'Middle'";

    public DeveloperRepository(DatabaseManagerConnector connector) {
        this.connector = connector;
    }

    @Override
    public DeveloperDao save(DeveloperDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dao.getName());
            statement.setInt(2, dao.getAge());
            statement.setObject(3, dao.getGender(), Types.OTHER);
            statement.setInt(4, dao.getSalary());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer not created! " + e.getMessage());
        }
        return dao;
    }


    @Override
    public List<DeveloperDao> selectById(Integer id) {
        List<DeveloperDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Developer not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer not found! " + e.getMessage());
        }
    }

    public List<DeveloperDao> selectByDepartment(String name) {
        List<DeveloperDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Developer not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer not found! " + e.getMessage());
        }
    }

    @Override
    public DeveloperDao updateById(Integer id, DeveloperDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, dao.getName());
            statement.setInt(2, dao.getAge());
            statement.setObject(3, dao.getGender(), Types.OTHER);
            statement.setInt(4, dao.getSalary());
            statement.setInt(5, id);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                    dao.setName(generatedKeys.getString(2));
                    dao.setAge(generatedKeys.getInt(3));
                    dao.setGender(generatedKeys.getString(4));
                    dao.setSalary(generatedKeys.getInt(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer not updated! " + e.getMessage());
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
            throw new RuntimeException("Developer not deleted" + e.getMessage());
        }
    }

    public Integer selectTotalSalaryByProjectId(Integer id) {
        Integer totalSalary = null;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TOTAL_SALARY_BY_PROJECT_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                totalSalary = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return totalSalary;
    }

    public List<DeveloperDao> selectAllDevelopersByProject(Integer id) {
        List<DeveloperDao> daoList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DEVELOPERS_BY_PROJECT_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DeveloperDao dao = new DeveloperDao();
                dao.setId(resultSet.getInt(1));
                dao.setName(resultSet.getString(2));
                dao.setAge(resultSet.getInt(3));
                dao.setGender(resultSet.getString(4));
                dao.setSalary(resultSet.getInt(5));
                daoList.add(dao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return daoList;
    }

    public List<DeveloperDao> selectAllJavaDevelopers() {
        List<DeveloperDao> daoList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_JAVA_DEVELOPERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DeveloperDao dao = new DeveloperDao();
                dao.setId(resultSet.getInt(1));
                dao.setName(resultSet.getString(2));
                dao.setAge(resultSet.getInt(3));
                dao.setGender(resultSet.getString(4));
                dao.setSalary(resultSet.getInt(5));
                daoList.add(dao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return daoList;
    }

    public List<DeveloperDao> selectAllMiddleDevelopers() {
        List<DeveloperDao> daoList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MIDDLE_DEVELOPERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DeveloperDao dao = new DeveloperDao();
                dao.setId(resultSet.getInt(1));
                dao.setName(resultSet.getString(2));
                dao.setAge(resultSet.getInt(3));
                dao.setGender(resultSet.getString(4));
                dao.setSalary(resultSet.getInt(5));
                daoList.add(dao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return daoList;
    }

    private List<DeveloperDao> convert(ResultSet resultSet) throws SQLException {
        List<DeveloperDao> daoList = new ArrayList<>();
        DeveloperDao dao;
        while (resultSet.next()) {
            dao = new DeveloperDao();
            dao.setId(resultSet.getInt("id"));
            dao.setName(resultSet.getString("name"));
            dao.setAge(resultSet.getInt("age"));
            dao.setGender(resultSet.getString("gender"));
            dao.setSalary(resultSet.getInt("salary"));
            daoList.add(dao);
        }
        return daoList;
    }
}
