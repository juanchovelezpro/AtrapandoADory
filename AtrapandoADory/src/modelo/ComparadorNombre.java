package modelo;

import java.util.Comparator;

public class ComparadorNombre implements Comparator<Jugador> {

	@Override
	public int compare(Jugador uno, Jugador dos) {
		// TODO Auto-generated method stub
		return uno.getNickname().compareToIgnoreCase(dos.getNickname());
	}

}
