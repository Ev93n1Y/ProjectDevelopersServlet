package service;

import java.sql.SQLException;
import java.util.Optional;

public interface Crud<T> {
    T create(T t);

    T read(Integer id);

    T read(String name);

    T update(Integer id, T t);

    void delete(Integer id) throws SQLException;
}
