package repository;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<T> {
    T save(T entity);

    T selectById(Integer id);

    T selectByName(String name);

    T updateById(Integer id, T t);

    void deleteById(Integer id) throws SQLException;
}
