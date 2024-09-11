package main.java.repository.dao;

import java.util.List;
import java.util.Optional;

public abstract class HotelDao<T> {
    public abstract void save(T t);
    public abstract void update(T t);
    public abstract void delete(Long id);
    public abstract Optional<T> findById(Long id);
    public abstract List<T> findAll();
}