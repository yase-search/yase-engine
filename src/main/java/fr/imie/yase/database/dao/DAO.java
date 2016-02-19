package fr.imie.yase.database.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAO<T> {

	public T get(int id);
	
	public List<T> find(Map<String, Object> params) throws SQLException;
	
	public boolean delete(int id);
	
	public T update(T entity);
	
	public T create(T entity);
}
