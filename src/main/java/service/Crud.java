package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Crud<T> {
    T create(T t);

    List<T> read(Integer id);

    List<T> read(String name);

    T update(Integer id, T t);

    void delete(Integer id) ;
}
