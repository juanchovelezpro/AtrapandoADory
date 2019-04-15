package hilos;

import modelo.*;

public class HiloPartida extends Thread {

	private Partida partida;

	public HiloPartida(Partida partida) {

		this.partida = partida;

	}

	@Override
	public void run() {

		Personaje[] personajes = partida.getPersonajes();

		for (int i = 0; i < personajes.length; i++) {

			personajes[i].moveHorizontal();

		}
		
		partida.resetProceso();

	}
}
