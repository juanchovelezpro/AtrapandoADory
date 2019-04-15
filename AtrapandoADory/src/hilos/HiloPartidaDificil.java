package hilos;

import modelo.*;

public class HiloPartidaDificil extends Thread {

	private Partida partida;

	public HiloPartidaDificil(Partida partida) {

		this.partida = partida;

	}

	@Override
	public void run() {

		Personaje[] personajes = partida.getPersonajes();

		for (int i = 0; i < personajes.length; i++) {

			personajes[i].setSpeed(Partida.DIFICIL);
			personajes[i].moveHorizontal();

		}
		partida.resetProceso();

	}

}
