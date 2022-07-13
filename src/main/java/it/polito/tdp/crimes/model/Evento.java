package it.polito.tdp.crimes.model;


public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		
		INIZIO_INTERVENTO,
		FINE_INTERVENTO
		
	}
	private int time;
	public Event evento;
	
	private EventType type;
	//private int agente;
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	public Evento(int time,Event evento, EventType type){//, int agente) {
		super();
		this.time = time;
		this.evento =evento;
		
		this.type = type;
		//this.agente = agente;
	}
	public int getTime() {
		return time;
	}
	public EventType getType() {
		return type;
	}
//	public int getAgente() {
//		return agente;
//	}
	
	

}
