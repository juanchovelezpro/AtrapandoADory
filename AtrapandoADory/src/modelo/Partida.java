package modelo;

import java.util.ArrayList;
import java.util.Random;

import Excepciones.PlayerNotFoundByScoreException;

public class Partida {

	// Constantes

	// Constantes del nivel de la partida
	public final static int FACIL = 1;
	public final static int MEDIO = 2;
	public final static int DIFICIL = 3;
	public final static int EXTREMO = 4;

	// Constantes de posiciones
	public final static int Y_RANDOM = 750;
	public final static int X_MIN = -300;
	public final static int X_MAX = -100;

	// Constantes para sumar puntos cuando el jugador atrape a Dory
	public final static int PUNTOS_FACIL = 10;
	public final static int PUNTOS_MEDIO = 100;
	public final static int PUNTOS_DIFICIL = 1000;
	public final static int PUNTOS_EXTREMO = 1500;

	// Constantes para restar puntos cuando el jugador no atrape a Dory
	public final static int PUNTOS_NOFACIL = 5;
	public final static int PUNTOS_NOMEDIO = 100;
	public final static int PUNTOS_NODIFICIL = 500;
	public final static int PUNTOS_NOEXTREMO = 750;

	// Atributos
	private ArrayList<Jugador> players;
	private Personaje[] personajes;
	private int nivel;
	private Random random = new Random();

	// Constructor
	public Partida(int nivel) {

		players = new ArrayList<Jugador>();
		this.nivel = nivel;
		personajes = new Personaje[9];

		cargarPersonajes();

	}

	// Crea los personajes de la partida.
	public void cargarPersonajes() {

		Personaje dory = new Personaje(Personaje.DORY, 1, random.nextInt(X_MAX + 1 - (X_MIN)) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje nemo = new Personaje(Personaje.NEMO, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje turtle = new Personaje(Personaje.TURTLE, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje manta = new Personaje(Personaje.MANTA, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje marlin = new Personaje(Personaje.MARLIN, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje pulpo = new Personaje(Personaje.PULPO, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje shark = new Personaje(Personaje.SHARK, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje turtleBaby = new Personaje(Personaje.TURTLE_BABY, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(Y_RANDOM));
		Personaje whale = new Personaje(Personaje.WHALE, 1, random.nextInt(X_MAX + 1 - X_MIN) + X_MIN,
				random.nextInt(3000));

		personajes[0] = dory;
		personajes[1] = manta;
		personajes[2] = nemo;
		personajes[3] = turtle;
		personajes[4] = marlin;
		personajes[5] = pulpo;
		personajes[6] = shark;
		personajes[7] = turtleBaby;
		personajes[8] = whale;

	}

	public ArrayList<Jugador> getPlayers() {

		return players;

	}

	public void setPlayers(ArrayList<Jugador> players) {

		this.players = players;

	}

	public void setNivel(int nivel) {

		this.nivel = nivel;

	}

	public int getNivel() {

		return nivel;

	}

	// Se encarga de que cada uno de los personajes realice de nuevo el recorrido
	// cuando lo finalice.
	public void resetProceso() {

		for (int i = 0; i < personajes.length; i++) {

			if (personajes[i].getPosX() >= 1200) {

				personajes[i].setPosX(random.nextInt(X_MAX + 1 - (X_MIN)) + X_MIN);
				personajes[i].setPosY(random.nextInt(Y_RANDOM));

			}

		}

	}

	// Este método sirve para ordenar el ArrayList de Jugadores registrados, con el
	// orden natural de la clase Jugador, que en este caso es ordenando
	// segun el puntaje del jugador. (Utilicé el método de ordenamiento por
	// seleccion)
	public ArrayList<Jugador> ordenarJugadoresPorPuntaje(ArrayList<Jugador> jugadores) {

		ArrayList<Jugador> ordenado = new ArrayList<Jugador>(jugadores);

		for (int i = 0; i < ordenado.size() - 1; i++) {

			Jugador menor = ordenado.get(i);
			int cual = i;

			for (int j = i + 1; j < ordenado.size(); j++) {

				if (ordenado.get(j).compareTo(menor) > 0) {

					menor = ordenado.get(j);
					cual = j;

				}

			}
			Jugador temp = ordenado.get(i);
			ordenado.set(i, menor);
			ordenado.set(cual, temp);

		}

		return ordenado;

	}

	// Este método permite organizar los jugadores registrados en orden alfabetico.
	// (Orden parcial utilizando el método de ordenamiento por insercion)
	public ArrayList<Jugador> ordenarJugadoresPorNombre(ArrayList<Jugador> jugadores) {

		ArrayList<Jugador> ordenado = new ArrayList<Jugador>(jugadores);

		ComparadorNombre porNombre = new ComparadorNombre();

		for (int i = 1; i < ordenado.size(); i++) {
			for (int j = i; j > 0 && porNombre.compare(ordenado.get(j - 1), ordenado.get(j)) > 0; j--) {

				Jugador temp = ordenado.get(j);
				ordenado.set(j, ordenado.get(j - 1));
				ordenado.set(j - 1, temp);

			}

		}

		return ordenado;

	}

	// Este método permite buscar a un jugador por su puntaje, utilizando la
	// búsqueda binaria.
	public Jugador buscarJugadorPorPuntaje(int puntaje) throws PlayerNotFoundByScoreException, NullPointerException {

		ArrayList<Jugador> ordenado = ordenarJugadoresPorPuntaje(players);

		Jugador player = null;
		int inicio = 0;
		int fin = ordenado.size() - 1;
		while (inicio <= fin && player == null) {

			int medio = (inicio + fin) / 2;
			if (ordenado.get(medio).getScore() == puntaje) {

				player = ordenado.get(medio);

			} else if (ordenado.get(medio).getScore() > puntaje) {

				inicio = medio + 1;

			} else {

				fin = medio - 1;
			}

		}

		if (player == null) {

			throw new PlayerNotFoundByScoreException(puntaje);

		}

		return player;

	}

	public Personaje[] getPersonajes() {

		return personajes;

	}
}
