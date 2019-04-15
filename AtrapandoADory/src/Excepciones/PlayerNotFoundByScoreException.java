package Excepciones;

public class PlayerNotFoundByScoreException extends Exception {

	public PlayerNotFoundByScoreException(int puntaje) {

		super("No se ha encontrado un jugador con " + puntaje + " puntos");

	}

}
