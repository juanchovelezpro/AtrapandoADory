package hilos;

import modelo.*;

public class HiloPartidaMedio extends Thread {

	private Partida partida;

	public HiloPartidaMedio(Partida partida) {

		this.partida = partida;

	}

	@Override
	public void run() {

		Personaje[] personajes = partida.getPersonajes();

		for (int i = 0; i < personajes.length; i++) {

			personajes[i].setSpeed(Partida.MEDIO);
			personajes[i].moveHorizontal();

		}

		partida.resetProceso();

	}

}
