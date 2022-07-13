package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Adiacenza {
	
	private long distrettoId1;
	private long distrettoId2;
	private LatLng latlng1;
	private LatLng latlng2;
	public double peso;
	
	public Adiacenza(long distrettoId1, long distrettoId2, LatLng latlng1, LatLng latlng2) {
		super();
		this.distrettoId1 = distrettoId1;
		this.distrettoId2 = distrettoId2;
		this.latlng1 = latlng1;
		this.latlng2 = latlng2;
		peso = LatLngTool.distance(latlng1, latlng2, LengthUnit.KILOMETER);
	}
	public long getDistrettoId1() {
		return distrettoId1;
	}
	public void setDistrettoId1(long distrettoId1) {
		this.distrettoId1 = distrettoId1;
	}
	public long getDistrettoId2() {
		return distrettoId2;
	}
	public void setDistrettoId2(long distrettoId2) {
		this.distrettoId2 = distrettoId2;
	}
	public LatLng getLatlng1() {
		return latlng1;
	}
	public void setLatlng1(LatLng latlng1) {
		this.latlng1 = latlng1;
	}
	public LatLng getLatlng2() {
		return latlng2;
	}
	public void setLatlng2(LatLng latlng2) {
		this.latlng2 = latlng2;
	}
	
	

}
