package repository;

import java.util.List;

public interface Repository<T> {
    T save(T entity);

    List<T> selectById(Integer id);

    List<T> selectByDepartment(String name);

    T updateById(Integer id, T t);

    void deleteById(Integer id);
}
