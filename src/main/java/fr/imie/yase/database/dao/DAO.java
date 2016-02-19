package fr.imie.yase.database.dao;

public interface DAO<T> {

	public T find(int id);
	
	public boolean delete(int id);
	
	public boolean update(int id);
	
	public T create(int id);
}
