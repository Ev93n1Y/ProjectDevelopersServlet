package repository;

import config.DatabaseManagerConnector;
import entities.dao.SkillDao;

import java.sql.*;

public class SkillRepository implements Repository<SkillDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT = "INSERT INTO skills (department, level) VALUES (?,?)";
    private static final String SELECT_BY_ID = "SELECT id, department, level FROM skills WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT id, department, level FROM skills WHERE department = ?";
    private static final String UPDATE_BY_ID = "UPDATE skills SET  department = ?, level = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM skills WHERE id = ?";

    public SkillRepository(DatabaseManagerConnector connector) {
        this.connector = connector;
    }

    @Override
    public SkillDao save(SkillDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            prStatement.setObject(1, dao.getDepartment(), Types.OTHER);
            prStatement.setObject(2, dao.getLevel(), Types.OTHER);
            prStatement.executeUpdate();
            try (ResultSet generatedKeys = prStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill not created");
        }
        return dao;
    }

    @Override
    public SkillDao selectById(Integer id) {
        ResultSet resultSet;
        SkillDao dao = null;
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

    public SkillDao selectByName(String name) {
        ResultSet resultSet;
        SkillDao dao = null;
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
    public SkillDao updateById(Integer id, SkillDao dao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setObject(1, dao.getDepartment(), Types.OTHER);
            statement.setObject(2, dao.getLevel(), Types.OTHER);
            statement.setInt(3, id);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dao.setId(generatedKeys.getInt(1));
                    dao.setDepartment(generatedKeys.getString(2));
                    dao.setLevel(generatedKeys.getString(3));
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

    private SkillDao convert(ResultSet resultSet) throws SQLException {
        SkillDao skillDao = new SkillDao();
        while (resultSet.next()) {
            skillDao.setId(resultSet.getInt("id"));
            skillDao.setDepartment(resultSet.getString("department"));
            skillDao.setLevel(resultSet.getString("level"));
        }
        return skillDao;
    }
}
