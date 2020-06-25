package it.polito.tdp.crimes.model;

import java.util.List;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<Integer> getAnni(){
		return this.dao.getAnni();
	}
	
	
}
