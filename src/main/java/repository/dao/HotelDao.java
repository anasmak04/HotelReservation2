package main.java.repository.dao;

import java.util.List;

public interface HotelDao<T> {
    T save(T t);
    T update(T t);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
