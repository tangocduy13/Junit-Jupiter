package fa.training.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<E, ID extends Serializable> {

    ID save(E e);

    List<E> getAll();

    E findById(ID id);

    void update(E e);

    void delete(ID id);

}
