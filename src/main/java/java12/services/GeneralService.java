package java12.services;

public interface GeneralService<T> {
    void save(T t);
    T getById(Long id);
    void updateById(Long id, T t);
    void deleteById(Long id);
}
