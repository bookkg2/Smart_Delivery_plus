package com.frame;

import java.util.ArrayList;

import com.vo.Status;

public interface StDependenciesDao<K,V> {
	public void insert(V v) throws Exception;
	public void update(V v) throws Exception;
	public void delete(K k) throws Exception;
	public V select(K k) throws Exception;
	public ArrayList<V> select() throws Exception;
	
	//Specialized
	Status selectpos(K k) throws Exception;
}
