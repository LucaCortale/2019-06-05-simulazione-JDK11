package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;

import it.polito.tdp.crimes.model.Adiacenza;
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
	
	public List<Adiacenza> getAdiacenze(int anno){
		String sql = "SELECT t1.district_id,t2.district_id,t1.geo_lon,t1.geo_lat,t2.geo_lon,t2.geo_lat "
				+ "FROM (SELECT e.district_id,e.geo_lon,e.geo_lat "
				+ "		FROM `events` e "
				+ "		WHERE YEAR(e.reported_date) = ? "
				+ "		GROUP BY e.district_id) AS t1, "
				+ "		(SELECT e.district_id,e.geo_lon,e.geo_lat "
				+ "		FROM `events` e "
				+ "		WHERE YEAR(e.reported_date) = ? "
				+ "		GROUP BY e.district_id) AS t2 "
				+ "WHERE t1.district_id > t2.district_id ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Adiacenza> list = new ArrayList<>() ;
			
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					
					long id1 = res.getLong("t1.district_id");
					long id2 = res.getLong("t2.district_id");
					LatLng latlng1 = new LatLng(res.getDouble("t1.geo_lon"),res.getDouble("t1.geo_lat"));
					LatLng latlng2 = new LatLng(res.getDouble("t2.geo_lon"),res.getDouble("t2.geo_lat"));
					
					list.add(new Adiacenza(id1,id2,latlng1,latlng2));
					
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

	
		
		public List<Event> listEventSimulazione(int anno,int mese,int giorno){
			String sql = "SELECT * "
					+ "FROM `events` e  "
					+ "WHERE YEAR(e.reported_date) = ? AND MONTH(e.reported_date) = ? AND DAY(e.reported_date) = ? "
					+ "order BY e.reported_date" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				st.setInt(1, anno);
				st.setInt(2, mese);
				st.setInt(3,giorno);
				
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

}
