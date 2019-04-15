package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Excepciones.PlayerNotFoundByScoreException;
import junit.framework.TestCase;
import modelo.*;

class PartidaTest extends TestCase {

	private Partida partida;
	Jugador zeus = new Jugador("Zeus", 1200);
	Jugador angel = new Jugador("Angel", 1100);
	Jugador juan = new Jugador("Juan", 1000);

	// En este escenario se agregan 3 jugadores de modo que no esten organizados de
	// forma alfabetica
	private void escenarioUno() {

		partida = new Partida(Partida.FACIL);

		partida.getPlayers().add(zeus);
		partida.getPlayers().add(angel);
		partida.getPlayers().add(juan);

	}

	// En este escenario se agregan 3 jugadores de modo que no esten organizados de
	// mayor a menor con base en su puntaje
	private void escenarioDos() {

		partida = new Partida(Partida.FACIL);

		partida.getPlayers().add(angel);
		partida.getPlayers().add(juan);
		partida.getPlayers().add(zeus);

	}

	// En este escenario se agregan 3 jugadores de modo que esten organizado de
	// mayor a menor con base en su puntaje
	private void escenarioTres() {

		partida = new Partida(Partida.FACIL);

		partida.getPlayers().add(zeus);
		partida.getPlayers().add(angel);
		partida.getPlayers().add(juan);

	}

	// Este test es para verificar el metodo de ordenarJugadoresPorNombre
	@Test
	public void testOrdenarJugadoresPorNombre() {

		escenarioUno();

		// Aqui se crea un arreglo y se agregan a los mismos objetos pero de tal forma
		// que quedaran organizados de forma alfabetica
		Jugador[] listaEsperada = new Jugador[3];
		listaEsperada[0] = angel;
		listaEsperada[1] = juan;
		listaEsperada[2] = zeus;

		// Aqui se llama a la lista que no esta ordenada (La que se creo en el
		// escenario) para ordenarla con el método de
		// ordenarJugadoresPorNombre
		ArrayList<Jugador> listaOrdenada = partida.ordenarJugadoresPorNombre(partida.getPlayers());
		Jugador[] listaOrdenadaArray = listaOrdenada.toArray(new Jugador[listaOrdenada.size()]);

		// Y aqui se comparan si los dos arreglos son los mismos o no
		// Si se desea verificar que esto cumple, cambiar de posicion a un jugador en la
		// listaEsperada lanzará un error, ya que una no se encontrara en orden
		// alfabetico y la otra si gracias al metodo de ordenamiento.
		assertArrayEquals(listaEsperada, listaOrdenadaArray);
	}

	// Este test es para verificar el metodo de ordenarJugadoresPorPuntaje
	@Test
	public void testOrdenarJugadoresPorPuntaje() {

		escenarioDos();

		// Aqui se crea un arreglo y se agregan jugadores de tal forma que esten
		// organizados de mayor a menor con base en su puntaje
		Jugador[] listaEsperada = new Jugador[3];
		listaEsperada[0] = zeus;
		listaEsperada[1] = angel;
		listaEsperada[2] = juan;

		// Aqui se ordena la lista que no esta ordenada con base en el puntaje de los
		// jugadores (La que se creo en el escenario)
		ArrayList<Jugador> listaOrdenada = partida.ordenarJugadoresPorPuntaje(partida.getPlayers());
		Jugador[] listaOrdenadaArray = listaOrdenada.toArray(new Jugador[listaOrdenada.size()]);

		assertArrayEquals(listaEsperada, listaOrdenadaArray);

	}

	// Este test es para verificar el metodo de buscarJugadorPorPuntaje
	@Test
	public void testBuscarJugadorPorPuntaje() {

		escenarioTres();
		Jugador esperado = null;

		try {
			// Aqui la variable esperado pasa a ser el Jugador Angel que ya definimos que tiene 1100 de puntaje
			esperado = partida.buscarJugadorPorPuntaje(1100);
		} catch (PlayerNotFoundByScoreException ex) {

			System.out.println(ex.getMessage());

		}

		// Aqui comparamos al angel que tenemos definido, con el jugador que llega como resultado de la busqueda del metodo buscarJugadorPorPuntaje
		assertEquals(esperado, angel);

	}

	// @Test
	// void test() {
	// fail("Not yet implemented");
	// }

}
