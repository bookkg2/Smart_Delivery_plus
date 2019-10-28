package com.mapper;

import java.util.ArrayList;

import com.vo.Status;



public interface StatusMapper {
	public void insert(Status obj);
	public void delete(String obj);
	public void update(Status obj);
	public Status select(String obj);
	public Status select_oid(String obj);
	public ArrayList<Status> selectall();
	public Status selectpos(String obj);
}
