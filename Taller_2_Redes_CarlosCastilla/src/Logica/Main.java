package Logica;

import java.util.ArrayList;

import processing.core.*;
import gifAnimation.*;
public class Main extends PApplet {

	Logica log;

	public void setup() {

		size(430, 700);
		log = new Logica(this);

	}

	public void draw() {
		
		background(31, 24, 38);
		log.display();

	}

	@Override
	public void keyPressed() {
		log.keys();
	}

	@Override
	public void mousePressed() {
		log.click();
	}
}
