package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	
	public long id;
	public LatLng posizione;
	public Distretto(long id, LatLng posizione) {
		super();
		this.id = id;
		this.posizione = posizione;
	}
	

}
