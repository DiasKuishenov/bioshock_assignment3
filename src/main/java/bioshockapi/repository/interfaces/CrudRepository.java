package bioshockapi.repository.interfaces;

import bioshockapi.exception.DatabaseOperationException;
import java.util.List;

public interface CrudRepository<T> {

    T create(T entity) throws DatabaseOperationException;

    T findById(int id) throws DatabaseOperationException;

    List<T> findAll() throws DatabaseOperationException;

    void update(T entity) throws DatabaseOperationException;

    void delete(int id) throws DatabaseOperationException;
}