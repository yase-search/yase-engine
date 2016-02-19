package fr.imie.yase.database.dao;

import java.util.Map;

public interface DAO<T> {

	public T get(int id);
	
	public T find(Map<String, Object> params);
	
	public boolean delete(int id);
	
	public boolean update(int id);
	
	public T create(int id);
}
