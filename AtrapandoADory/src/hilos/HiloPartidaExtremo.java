package hilos;

import modelo.*;

public class HiloPartidaExtremo extends Thread {

	private Partida partida;

	public HiloPartidaExtremo(Partida partida) {

		this.partida = partida;

	}

	@Override
	public void run() {

		Personaje[] personajes = partida.getPersonajes();

		for (int i = 0; i < personajes.length; i++) {

			personajes[i].setSpeed(Partida.EXTREMO);
			personajes[i].moveHorizontal();

		}

		partida.resetProceso();

	}

}
