package com.frame;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.vo.Status;

public interface StDependenciesBiz<K,V> {
	@Transactional
	public void register(V v) throws Exception;
	@Transactional
	public void modify(V v) throws Exception;
	@Transactional
	public void remove(K k) throws Exception;
	public V get(K k) throws Exception;
	public ArrayList<V> get() throws Exception;
	
	//Specialized
	public V selectpos(K k) throws Exception;
}
