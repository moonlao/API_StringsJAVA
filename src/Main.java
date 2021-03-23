import java.io.PrintWriter;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	@Override
	public void settings() {
		size(500, 500);
	}

	// declaración de variables
	String[] textoOG;
	String[] textoNuevo;

	ArrayList<Palabra> words;
	ArrayList<Palabra> palabrasMatched;

	Palabra elegida;
	int elegidaPos;
	boolean verCaja;

	PrintWriter escribir;

	int cajaPosX;
	int cajaPosY;

	boolean verElegida;
	Palabra palabraMouse;

	int parejas;

	@Override
	public void setup() {

		textoOG = loadStrings("./texto/textoOG.txt");
		textoNuevo = loadStrings("./texto/textoNuevo.txt");

		words = new ArrayList<Palabra>();
		palabrasMatched = new ArrayList<Palabra>();

		elegida = null;
		elegidaPos = 0;
		verCaja = true;

		cajaPosX = 350;
		cajaPosY = 455;

		parejas = 0;

		// recorrer el texto original para partir las frases y hacer una lista de
		// palabras
		for (int index = 0; index < textoOG.length; index++) {
			String[] arrayWords = textoOG[index].split(" ");
			for (int j = 0; j < arrayWords.length; j++) {
				words.add(new Palabra(arrayWords[j], (int) (random(25, 450)), -100 + (index * -80), 0, 0, 0));
			}
		}

		// encontrar las 4 palabras random
		for (int k = 0; k <= 3; k++) {
			palabrasMatched.add(
					new Palabra(words.get((int) random(words.size())).getPalabra(), 50 + (k * 80), 470, 255, 255, 255));
		}
	}

	@Override
	public void draw() {
		background(255);

		// creación de banner
		fill(130, 130, 0);
		rect(0, 450, 500, 60);

		// dibujar las palabras que caen
		for (int i = 0; i < words.size(); i++) {
			textAlign(PConstants.CENTER, PConstants.CENTER);
			textSize(16);
			fill(words.get(i).getR());
			text(words.get(i).getPalabra(), words.get(i).getPosX(), words.get(i).getPosY());
			words.get(i).mover();
		}

		// dibujar las palabras del banner
		for (int i = 0; i < palabrasMatched.size(); i++) {
			textSize(20);
			fill(palabrasMatched.get(i).getR(), palabrasMatched.get(i).getG(), palabrasMatched.get(i).getB());
			text(palabrasMatched.get(i).getPalabra(), palabrasMatched.get(i).getPosX(),
					palabrasMatched.get(i).getPosY());

		}

		// cuando se emparejen las 4 que aparezca la caja
		if (parejas == 4) {
			verCaja = true;
			botonGuardar();
		}

		// ver palabra seleccionada pegada en el mouse
		if (verElegida == true) {
			fill(0);
			textSize(16);
			text(palabraMouse.getPalabra(), mouseX, mouseY);
		}
	}

	// método para pintar el botón
	public void botonGuardar() {

		if (verCaja == true) {
			noStroke();
			fill(52, 152, 219);
			rect(cajaPosX, cajaPosY, 100, 40);
			textSize(16);
			fill(255);
			text("Guardar", 400, 470);

			// responsive
			if (mouseX > cajaPosX && mouseX < cajaPosX + 130 && mouseY > cajaPosY && mouseY < cajaPosY + 40) {
				fill(36, 113, 163);
				rect(cajaPosX, cajaPosY, 100, 40);
				fill(255);
				text("Guardar", 400, 470);
			}
		}
	}

	@Override
	public void mousePressed() {

		// recorrer las palabras del banner y seleccionar elegida
		for (int i = 0; i < palabrasMatched.size(); i++) {
			Palabra p = palabrasMatched.get(i);
			if (mouseX > p.getPosX() - 18 && mouseX < p.getPosX() + 36 && mouseY > p.getPosY() - 9
					&& mouseY < p.getPosY() + 18) {
				palabrasMatched.get(i).setColor(255, 0, 0);
				verElegida = true;
				palabraMouse = palabrasMatched.get(i);
				elegida = palabrasMatched.get(i);
				elegidaPos = palabrasMatched.indexOf(elegida);
			}
		}

		// recorrer todas las palabras y comparar con la elegida
		// eliminar la palabra y agregar a parejas
		for (int i = 0; i < words.size(); i++) {
			Palabra p = words.get(i);
			if (mouseX > p.getPosX() - 18 && mouseX < p.getPosX() + 36 && mouseY > p.getPosY() - 9
					&& mouseY < p.getPosY() + 18) {
				if (elegida.getPalabra().compareTo(words.get(i).getPalabra()) == 0) {
					words.remove(i);
					palabrasMatched.get(elegidaPos).setColor(0, 255, 0);
					verElegida = false;
					parejas++;
				}
			}
		}

		// al darle click al boton que se cree el nuevo txt con las palabras no usadas
		// en lowercase
		if (verCaja == true && mouseX < 450 && mouseX > 350 && mouseY < 495 && mouseY > 455) {
			escribir = createWriter("./texto/textoNuevo.txt");
			ArrayList<Palabra> monda = new ArrayList<Palabra>();
			monda = words;
			for (int i = 0; i < monda.size(); i++) {
				for (int j = 0; j < palabrasMatched.size(); j++) {
					if (monda.get(i).getPalabra().equals(palabrasMatched.get(j))) {
						// (que no haga nada con estas)
					} else {
						escribir.println(monda.get(i).getPalabra());
						break;
					}
				}
			}
			for (int j = 0; j < palabrasMatched.size(); j++) {
				// ahora sí que pase a uppercase las emparejadas
				escribir.println(palabrasMatched.get(j).getPalabra().toUpperCase());
			}

			// flush y close del nuevo txt
			escribir.flush();
			escribir.close();
		}

	}

}
