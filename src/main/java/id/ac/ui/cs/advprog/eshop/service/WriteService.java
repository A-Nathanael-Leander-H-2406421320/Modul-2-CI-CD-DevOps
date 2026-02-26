package id.ac.ui.cs.advprog.eshop.service;

public interface WriteService<T> {
    T create(T entity);

    void update(String id, T entity);

    void delete(String id);
}