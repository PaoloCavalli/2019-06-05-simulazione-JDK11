package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer>  getAnni() {
		final String sql = "SELECT DISTINCT YEAR (e.reported_date) AS anni " + 
				"FROM `events` AS e " + 
				"ORDER BY anni ASC  ";
		List<Integer> anni = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
	        
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				anni.add(res.getInt("anni"));
				
			}
			
			conn.close();
			return anni;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
	}
	public List<Integer>  getMesi() {
		final String sql = "SELECT DISTINCT MONTH(e.reported_date) AS mesi " + 
				"FROM `events` AS e " + 
				"ORDER BY mesi ASC  ";
		List<Integer> mesi = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
	        
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				mesi.add(res.getInt("mesi"));
				
			}
			
			conn.close();
			return mesi;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
	}
	public List<Integer>  getGiorni() {
		final String sql = "SELECT DISTINCT DAY(e.reported_date) AS giorni " + 
				"FROM `events` AS e " + 
				"ORDER BY giorni ASC  ";
		List<Integer> giorni = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
	        
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				giorni.add(res.getInt("giorni"));
				
			}
			
			conn.close();
			return giorni;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
	}
	public List<Integer> getVertici(){
		final String sql = "SELECT DISTINCT e.district_id AS dis " + 
				"FROM `events` AS e " + 
				"ORDER BY dis asc ";
		List<Integer> distretti = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
	        
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				distretti.add(res.getInt("dis"));
				
			}
			
			conn.close();
			return distretti;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
	}
	}	
		public Double getLatMedia(Integer anno, Integer distretto) {
			final String sql="SELECT AVG (e.geo_lat) AS latMedia " + 
					"FROM `events` AS e " + 
					"WHERE YEAR (e.reported_date)=? AND e.district_id=?";
			
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
			    st.setInt(1, anno);
			    st.setInt(2, distretto);
			    ResultSet res = st.executeQuery() ;
			    
			    if(res.next()) {
			    	conn.close();
			    	return  res.getDouble("latMedia");
			    }else {
			    	conn.close();
			    	return  null;
			    }
	  
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
		public Double getLonMedia(Integer anno, Integer distretto) {
			final String sql="SELECT AVG (e.geo_lon) AS lonMedia " + 
					"FROM `events` AS e " + 
					"WHERE YEAR (e.reported_date)=? AND e.district_id=?";
			
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
			    st.setInt(1, anno);
			    st.setInt(2, distretto);
			    ResultSet res = st.executeQuery() ;
			    
			    if(res.next()) {
			    	conn.close();
			    	return  res.getDouble("lonMedia");
			    }else {
			    	conn.close();
			    	return  null;
			    }
	  
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
		public Integer getDistrettoMenoCriminoso(Integer anno) {
			final String sql = "SELECT e.district_id as dis " + 
					"FROM `events` AS e  " + 
					"WHERE YEAR(e.reported_date) =? " + 
					"GROUP BY e.district_id  " + 
					"ORDER BY COUNT(*) ASC " + 
					"LIMIT 1 ";
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
			    st.setInt(1, anno);
                ResultSet res = st.executeQuery() ;
			    
			    if(res.next()) {
			    	conn.close();
			    	return  res.getInt("dis");
			    }else {
			    	conn.close();
			    	return  null;
			    }
	  
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
			
		}
	}
	


