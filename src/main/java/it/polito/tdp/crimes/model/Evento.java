package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	public enum TipoEvento {
		CRIMINE,
		ARRIVA_GENTE,
		GESTITO
	}
	
	private TipoEvento tipo;
	private LocalDateTime data;
	private Event crimine;
	/**
	 * @param tipo: può essere un crimine, l'arrivo di un agente, e la gestione positiva o negativa del crimine 
	 * @param data: momento esatto del tipo di evento!
	 * @param crimine: evento criminoso
	 */
	public Evento(TipoEvento tipo, LocalDateTime data, Event crimine) {
		super();
		this.tipo = tipo;
		this.data = data;
		this.crimine = crimine;
	}
	public TipoEvento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Event getCrimine() {
		return crimine;
	}
	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}
	@Override
	public int compareTo(Evento o) {
	
		return this.data.compareTo(o.data);
	}
	
	
	
}
