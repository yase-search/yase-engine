package fr.imie.yase.database.dao;

import java.util.List;
import java.util.Map;

public interface DAO<T> {

	public T get(int id);
	
	public List<T> find(Map<String, Object> params);
	
	public boolean delete(int id);
	
	public boolean update(int id);
	
	public T create(int id);
}
