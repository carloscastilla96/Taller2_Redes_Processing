package Logica;

import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.*;

public class Jugador {

	private PVector posicion, dir, posicionBala;
	private float r = 35;
	private float angulo, posX, posY;
	private ArrayList<Bala> balas;
	private PApplet app;
	private boolean disparar;
	private int ID=1; 

	public Jugador(PApplet app) {
		this.app = app;
		posicion = new PVector(app.random(30, app.width - 30), app.random(30, app.height - 30));
		dir = new PVector(0, 0);
		// posicionBala = new PVector(0, 0);
	}

	public void display() {
		dir.x = r * app.cos(angulo);
		dir.y = r * app.sin(angulo);
		app.fill(0);
		app.pushMatrix();
		app.translate(posicion.x, posicion.y);
		app.ellipse(0, 0, 30, 30);
		app.stroke(255, 0, 0);
		app.line(0, 0, dir.x, dir.y);
		app.popMatrix();

	}

	public Bala disparo() {
		Bala b = new Bala(app, this);
		return b;
	}

	public void move() {

		if (app.keyCode == app.DOWN) {
			posicion.y += 3;
		}
		if (app.keyCode == app.UP) {
			posicion.y -= 3;
		}
		if (app.keyCode == app.LEFT) {
			posicion.x -= 3;
			angulo += 0.1;
		}
		if (app.keyCode == app.RIGHT) {
			posicion.x += 3;
			angulo -= 0.1;
		}

	}

	public PVector getPosicion() {
		return posicion;
	}

	public void setPosicion(PVector posicion) {
		this.posicion = posicion;
	}

	public PVector getDir() {
		return dir;
	}

	public void setDir(PVector dir) {
		this.dir = dir;
	}

	public PVector getPosicionBala() {
		return posicionBala;
	}

	public float getR() {
		return r;
	}

	public float getAngulo() {
		return angulo;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public ArrayList<Bala> getBalas() {
		return balas;
	}

	public PApplet getApp() {
		return app;
	}

	public boolean isDisparar() {
		return disparar;
	}

	public int getID() {
		return ID;
	}

}
