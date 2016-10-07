package Logica;

import java.io.Serializable;

import processing.core.*;

public class MensajeContenido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int identificador, emisor, receptor;
	private PVector vector, velocidad;

	public MensajeContenido(int identificador, int emisor, int receptor, PVector vector, PVector velocidad) {
		this.identificador = identificador;
		this.emisor = emisor;
		this.receptor = receptor;
		this.vector = vector;
		this.velocidad = velocidad;

	}

	public int getIdentificador() {
		return identificador;
	}

	public int getEmisor() {
		return emisor;
	}

	public int getReceptor() {
		return receptor;
	}

	public PVector getVector() {
		return vector;
	}

	public PVector getVelocidad() {
		return velocidad;
	}
	
	

}
