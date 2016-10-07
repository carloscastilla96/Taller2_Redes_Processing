package Logica;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import gifAnimation.Gif;
import processing.core.*;
import Comun.*;

public class Logica implements Observer {

	private Jugador jug;
	private ArrayList<Bala> balasJugador;
	private PApplet app;
	private Conexion con;
	private PImage imagenesFondo[];
	private int pantallas = 3;
	private Gif onLoad;

	public Logica(PApplet app) {

		con = new Conexion();
		con.addObserver(this);
		Thread thread = new Thread(con);
		thread.start();
		onLoad = new Gif(app, ".." + File.separatorChar + "data" + File.separatorChar + "ellipsis.gif");
		imagenesFondo = new PImage[] {
				app.loadImage(".." + File.separatorChar + "data" + File.separatorChar + "LOGO.png") };
		onLoad.loop();
		this.app = app;
		pantallas = 1;
		jug = new Jugador(app);

		balasJugador = new ArrayList<>();
		if (con.getIdentifier() == 3) {
			String comenzarJuego = "start";
			con.sendObjectMessage(comenzarJuego);

		}
	}

	public void display() {
		switch (pantallas) {
		case 1:
			switch (con.getIdentifier()) {
			case 1:
				pantalla1();
				break;
			case 2:

				break;
			case 3:

				break;

			default:
				app.fill(255);
				app.textSize(10);
				app.text("Lo sentimos, No se puede Conectar a este grupo", app.width / 4, app.height / 2);
				break;
			}
			break;

		case 3:

			switch (con.getIdentifier()) {
			case 1:
				jugador();
				recorrerArreglos();
				break;
			case 2:
				recorrerArreglos();
				break;
			case 3:
				recorrerArreglos();
				break;

			}
			break;
		}

	}

	public void limitesBalaJugador() {
		MensajeContenido mensaje;

		int receptor;
		synchronized (balasJugador) {

			for (int i = 0; i < balasJugador.size(); i++) {

				Bala bJ = balasJugador.get(i);

				if (bJ.getPosicion().x > app.width) {// si es por el lado
														// derecho en
														// x

					switch (con.getIdentifier()) {

					case 1:// centro
						receptor = 2;
						mensaje = new MensajeContenido(bJ.getID(), con.getIdentifier(), receptor, bJ.getPosicion(),
								bJ.getDir());
						System.out.println(" envie");
						con.sendObjectMessage(mensaje);
						System.out.println("Saliooooooooooooo");
						balasJugador.remove(bJ);
						break;

					case 2:// izquierda

						break;

					case 3:// derecha

						break;

					}
				}
				if (bJ.getPosicion().x < 4) {// si es por el lado izquierdo en x

					switch (con.getIdentifier()) {

					case 1:// centro

						break;

					case 2:// izquierda

						break;

					case 3:// derecha

						break;

					}

				}
				if (bJ.getPosicion().y > app.height || bJ.getPosicion().y < 0) {
					balasJugador.remove(bJ);
				}
			}

		}
	}

	public void update(Observable o, Object arg) {

		if (arg instanceof String) {
			String entrante = arg.toString();
			if (entrante.contains("start")) {
				pantallas = 3;
			}
		}

		if (arg instanceof MensajeContenido) {
			MensajeContenido entrante = (MensajeContenido) arg;
			if (con.getIdentifier() == entrante.getReceptor()) {

				switch (con.getIdentifier()) {
				case 1:

					if (entrante.getEmisor() == 2) {

					}
					if (entrante.getEmisor() == 3) {

					}

					break;
				case 2:
					if (entrante.getEmisor() == 1) {
						System.out.println("llegooooooooooooo");
						System.out.println();
						balasJugador.add(new Bala(app,vector, entrante.getVelocidad(),
								entrante.getIdentificador()));

					}
					if (entrante.getEmisor() == 3) {

					}

					break;
				case 3:

					if (entrante.getEmisor() == 1) {

					}
					if (entrante.getEmisor() == 2) {

					}
					break;

				}

			}
		}
		if (arg instanceof MensajeAndroid) {

			String entrante = arg.toString();
			

		}

	}

	public void jugador() {

		jug.display();
		limitesBalaJugador();

	}

	public void recorrerArreglos() {

		for (Bala b : balasJugador) {
			b.display();
			b.mover();
		
			System.out.println("el nodo: "+con.getIdentifier()+" tiene :"+balasJugador.size()+" balas");
		}
	}

	public void enemigos() {

	}

	public void coalision() {

	}

	public void pantalla1() {

		app.image(onLoad, app.width / 2 - 30, app.height - 200, onLoad.width / 2, onLoad.height / 2);
		app.fill(255);
		app.textSize(15);
		app.textMode(app.CENTER);
		app.text("CONECTANDO", app.width / 2 - 50, app.height / 2 + 160);
		app.image(imagenesFondo[0], -80, app.height / 6, imagenesFondo[0].width / 2, imagenesFondo[0].height / 2);

	}

	public void keys() {

		jug.move();
	}

	public void click() {

		balasJugador.add(jug.disparo());
	}

	public int getPantallas() {
		return pantallas;
	}

}
