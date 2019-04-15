package modelo;

public class Personaje {

	public final static String DORY = "dory";
	public final static String NEMO = "nemo";
	public final static String TURTLE = "turtle";
	public final static String MANTA = "manta";
	public final static String MARLIN = "marlin";
	public final static String PULPO = "pulpo";
	public final static String SHARK = "shark";
	public final static String TURTLE_BABY = "turtlebaby";
	public final static String WHALE = "whale";

	private String nombre;
	private int speed;
	private int posX;
	private int posY;

	public Personaje(String nombre, int speed, int posX, int posY) {

		this.nombre = nombre;
		this.speed = speed;
		this.posX = posX;
		this.posY = posY;

	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX
	 *            the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY
	 *            the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int moveHorizontal() {

		setPosX(posX + speed);

		return posX;

	}

	public void resetX() {

		posX = -100;

	}

}
