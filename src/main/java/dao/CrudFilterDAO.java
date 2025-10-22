package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudFilterDAO<T> extends CrudDAO<T> {

    List<T> filtrar (String campo, Long id) throws SQLException;


}
