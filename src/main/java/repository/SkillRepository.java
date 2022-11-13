package repository;

import config.DatabaseManagerConnector;
import entities.dao.SkillDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository implements Repository<SkillDao> {
    private final DatabaseManagerConnector connector;
    private static final String INSERT = "INSERT INTO skills (department, level) VALUES (?,?)";
    private static final String SELECT_BY_ID = "SELECT id, department, level FROM skills WHERE id = ?";
    private static final String SELECT_BY_DEP = "SELECT id, department, level FROM skills WHERE department = ?";
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Skill not created! " + e.getMessage());
        }
        return dao;
    }

    @Override
    public List<SkillDao> selectById(Integer id) {
        List<SkillDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Skill not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Skill not found! " + e.getMessage());
        }
    }

    public List<SkillDao> selectByDepartment(String department) {
        List<SkillDao> daoList;
        ResultSet resultSet;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEP)) {
            statement.setObject(1, department, Types.OTHER);
            resultSet = statement.executeQuery();
            daoList = convert(resultSet);
            if(daoList.isEmpty()){
                throw new RuntimeException("Skill not found! ");
            }
            return daoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Skill not found! " + e.getMessage());
        }
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
            throw new RuntimeException("Skill not updated! " + e.getMessage());
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
            throw new RuntimeException("Skill not deleted! " + e.getMessage());
        }
    }

    private List<SkillDao> convert(ResultSet resultSet) throws SQLException {
        List<SkillDao> daoList = new ArrayList<>();
        SkillDao skillDao;
        while (resultSet.next()) {
            skillDao = new SkillDao();
            skillDao.setId(resultSet.getInt("id"));
            skillDao.setDepartment(resultSet.getString("department"));
            skillDao.setLevel(resultSet.getString("level"));
            daoList.add(skillDao);
        }
        return daoList;
    }
}
