package com.status;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frame.StDependenciesBiz;
import com.frame.StDependenciesDao;
import com.vo.Status;

@Service("sbiz")
public class StatusBiz implements StDependenciesBiz<String, Status> {

	@Resource(name="sdao")
	StDependenciesDao<String, Status> dao;

	@Override
	public void register(Status v) throws Exception {
		dao.insert(v);
	}

	@Override
	public void modify(Status v) throws Exception {
		dao.update(v);
	}

	@Override
	public void remove(String k) throws Exception {
		dao.delete(k);
	}

	@Override
	public Status get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Status> get() throws Exception {
		return dao.select();
	}

	@Override
	public Status selectpos(String k) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectpos(k);
	}




}
