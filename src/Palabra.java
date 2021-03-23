
public class Palabra {

	public int posX, posY;
	public String word;

	// color
	public int r, g, b;

	// constructor
	public Palabra(String word, int posX, int posY, int r, int g, int b) {
		this.posX = posX;
		this.posY = posY;
		this.word = word;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void mover() {
		if (posY > 500) {
			posY = 0;
		}
		posY += 1;
	}

	public int getR() {
		return r;
	}

	public int getB() {
		return b;
	}

	public int getG() {
		return g;
	}

	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public String getPalabra() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

}
