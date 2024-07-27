package repository.contract.base;

import java.util.Optional;

public interface IBaseRepository<T, Id> {
    void add(T entity);

    void update(T entity);

    Optional<T> getById(Id id);
}