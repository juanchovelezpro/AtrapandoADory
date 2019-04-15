package modelo;

import java.io.Serializable;

public class Jugador implements Serializable, Comparable<Jugador> {

	// Constantes
	public final static int PUNTAJE_INICIAL = 0;

	// Atributos
	private String nickname;
	private int score;

	// Constructor
	public Jugador(String nickname, int score) {

		this.nickname = nickname;
		this.score = score;

	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public void resetScore() {

		score = 0;

	}

	@Override
	public String toString() {

		return "Nickname: " + nickname + " - " + "Score: " + score;

	}

	@Override
	public int compareTo(Jugador player) {

		int valor = this.score - player.getScore();

		return valor;

	}
	
}
