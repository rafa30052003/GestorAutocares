package iessfranciscodelosrios.proyecto3.GestorAutocares.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T,K>{
	
	List <T> findAll() throws SQLException;
	
	T findById (K id) throws SQLException;
	
	T save(T entity) throws SQLException ;
	
	void delete(T entity) throws SQLException ;
}
