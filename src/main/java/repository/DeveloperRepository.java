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
            "SELECT dev.id, dev.name, s.department FROM developers AS dev " +
                    "JOIN developers_skills AS d_s ON dev.id = d_s.developer_id " +
                    "JOIN skills AS s ON s.id = d_s.skill_id " +
                    "WHERE s.department = 'Java'";
    private static final String SELECT_ALL_MIDDLE_DEVELOPERS =
            "SELECT dev.id, dev.name, s.level FROM developers AS dev " +
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not created");
        }
        return dao;
    }


    @Override
    public DeveloperDao selectById(Integer id) {
        ResultSet resultSet;
        DeveloperDao dao = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dao;
    }

    public DeveloperDao selectByName(String name) {
        ResultSet resultSet;
        DeveloperDao dao = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            dao = convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dao;
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
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public Integer selectTotalSalaryByProjectId(Integer id) {
        int totalSalary;// = null;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TOTAL_SALARY_BY_PROJECT_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            //while (resultSet.next()) {
                totalSalary = resultSet.getInt(1);
            //}
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return totalSalary;
    }

    public List<DeveloperDao> selectAllDevelopersByProjectId(Integer id) {
        List<DeveloperDao> daoList = new ArrayList<>();
        DeveloperDao dao = new DeveloperDao();
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DEVELOPERS_BY_PROJECT_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dao.setId(resultSet.getInt(1));
                dao.setName(resultSet.getString(2));
                dao.setAge(resultSet.getInt(3));
                dao.setGender(resultSet.getString(4));
                dao.setSalary(resultSet.getInt(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return daoList;
    }

    public StringBuilder selectAllJavaDevelopers() {
        StringBuilder stringBuilder = new StringBuilder("All Java developers:\n");
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_JAVA_DEVELOPERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringBuilder.append("id: ");
                stringBuilder.append(resultSet.getInt(1));
                stringBuilder.append(" | name: ");
                stringBuilder.append(resultSet.getString(2));
                stringBuilder.append(" | department: ");
                stringBuilder.append(resultSet.getString(3));
                stringBuilder.append("\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return stringBuilder;
    }

    public StringBuilder selectAllMiddleDevelopers() {
        StringBuilder stringBuilder = new StringBuilder("All middle Java developers:\n");
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MIDDLE_DEVELOPERS)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringBuilder.append("id: ");
                stringBuilder.append(resultSet.getInt(1));
                stringBuilder.append(" | name: ");
                stringBuilder.append(resultSet.getString(2));
                stringBuilder.append(" | level: ");
                stringBuilder.append(resultSet.getString(3));
                stringBuilder.append("\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return stringBuilder;
    }

    private DeveloperDao convert(ResultSet resultSet) throws SQLException {
        DeveloperDao developerDao = new DeveloperDao();
        while (resultSet.next()) {
            developerDao.setId(resultSet.getInt("id"));
            developerDao.setName(resultSet.getString("name"));
            developerDao.setAge(resultSet.getInt("age"));
            developerDao.setGender(resultSet.getString("gender"));
            developerDao.setSalary(resultSet.getInt("salary"));
        }
        return developerDao;
    }
}
