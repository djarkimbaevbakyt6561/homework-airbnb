package java12.dao;

import java.util.Optional;

public interface GeneralDao<T> {
    void save(T t);
    Optional<T> getById(Long id);
    void updateById(Long id, T t);
    void deleteById(Long id);
}
