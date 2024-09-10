package main.java.repository.dao;

import java.util.List;

public abstract class HotelDao<T> {
    public abstract void save(T t);
    public abstract void update(T t);
    public abstract void delete(Long id);
    public abstract T findById(Long id);
    public abstract List<T> findAll();
}