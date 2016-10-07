package Logica;

import processing.core.*;

public class Bala {

	private PVector posicion,dir;
	private int x, y;
	private PApplet app;

	private int ID;

	public Bala(PApplet app, Object o) {

		this.app = app;

		seleccion(o);

	}

	public Bala(PApplet app, PVector posicion, PVector direccion, int ID) {
		this.app = app;
		this.posicion = posicion;
		this.dir = direccion;
		this.ID = ID;

	}

	public int seleccion(Object o) {

		if (o instanceof Jugador) {
			ID = 1;
			Jugador j = (Jugador) o;
			posicion = j.getPosicion().get();
			dir = j.getDir().get();

		} else {
			ID = 2;
		}
		return ID;

	}

	public void display() {

		app.ellipse(posicion.x, posicion.y, 5, 5);

	}

	public void mover() {
		posicion.add(dir);
	}

	public boolean coalision(PVector vector) {
		return app.dist(posicion.x, posicion.y, vector.x, vector.y) < 30;

	}

	public void disparo() {
		posicion.add(dir);
	}

	public int getID() {
		return ID;
	}

	public PVector getDir() {
		return dir;
	}

	public PVector getPosicion() {
		return posicion;
	}


}
