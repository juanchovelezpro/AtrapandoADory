package interfaz;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import modelo.Personaje;
import modelo.Partida;
import modelo.Jugador;

import java.lang.Thread;
import java.util.Random;

public class PanelDibujo extends JPanel implements MouseListener {

	// Atributos
	private VentanaPrincipal ventana;
	private Image dory;
	private Image turtle;
	private Image marlin;
	private Image shark;
	private Image nemo;
	private Image manta;
	private Image pulpo;
	private Image turtleBaby;
	private Image whale;
	private Image background;
	private Random random = new Random();

	// Constructor
	public PanelDibujo(VentanaPrincipal window) {

		ventana = window;

		dory = Toolkit.getDefaultToolkit().createImage("./data/imagenes/doryFinal.png");
		manta = Toolkit.getDefaultToolkit().createImage("./data/imagenes/mantaFinal.png");
		nemo = Toolkit.getDefaultToolkit().createImage("./data/imagenes/nemoFinal.png");
		marlin = Toolkit.getDefaultToolkit().createImage("./data/imagenes/marlinFinal.png");
		pulpo = Toolkit.getDefaultToolkit().createImage("./data/imagenes/pulpoFinal.png");
		turtleBaby = Toolkit.getDefaultToolkit().createImage("./data/imagenes/turtleBabyFinal.png");
		whale = Toolkit.getDefaultToolkit().createImage("./data/imagenes/whaleFinal.png");
		shark = Toolkit.getDefaultToolkit().createImage("./data/imagenes/sharkFinal.png");
		turtle = Toolkit.getDefaultToolkit().createImage("./data/imagenes/turtleFinal.png");
		background = Toolkit.getDefaultToolkit().createImage("./data/imagenes/fondo.jpg");
		addMouseListener(this);
	}

	// Se encarga de dibujar los personajes con sus respectivas imagenes
	@Override
	public void paintComponent(Graphics fig) {

		super.paintComponent(fig);

		fig.drawImage(background, 0, 0, null);

		Personaje[] personajes = ventana.getPartida().getPersonajes();

		// Se dibujan los personajes en el panel
		for (int i = 0; i < personajes.length; i++) {

			if (personajes[i].getNombre().equals(Personaje.DORY)) {
				fig.drawImage(dory, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.MANTA)) {

				fig.drawImage(manta, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.NEMO)) {

				fig.drawImage(nemo, personajes[i].getPosX(), personajes[i].getPosY(), null);
			} else if (personajes[i].getNombre().equals(Personaje.MARLIN)) {

				fig.drawImage(marlin, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.PULPO)) {

				fig.drawImage(pulpo, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.WHALE)) {

				fig.drawImage(whale, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.TURTLE_BABY)) {

				fig.drawImage(turtleBaby, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.SHARK)) {

				fig.drawImage(shark, personajes[i].getPosX(), personajes[i].getPosY(), null);

			} else if (personajes[i].getNombre().equals(Personaje.TURTLE)) {

				fig.drawImage(turtle, personajes[i].getPosX(), personajes[i].getPosY(), null);

			}
		}

		// Se inicializan los hilos
		Thread nivelFacil = new Thread(ventana.getHiloFacil());
		Thread nivelMedio = new Thread(ventana.getHiloMedio());
		Thread nivelDificil = new Thread(ventana.getHiloDificil());
		Thread nivelExtremo = new Thread(ventana.getHiloExtremo());

		Jugador player = ventana.continuePlayer(ventana.getPlayer());

		// Se establece los puntajes para pasar de un nivel a otro con sus respectivas
		// condiciones
		if (player.getScore() < 100) {

			nivelMedio.interrupt();
			ventana.getPartida().setNivel(Partida.FACIL);
			ventana.getPanelOpciones().setTxtLevel(String.valueOf(ventana.getPartida().getNivel()));
			nivelFacil.start();

		}

		if (player.getScore() >= 100) {
			nivelFacil.interrupt();
			ventana.getPartida().setNivel(Partida.MEDIO);
			ventana.getPanelOpciones().setTxtLevel(String.valueOf(ventana.getPartida().getNivel()));
			nivelMedio.start();

		}

		if (player.getScore() >= 1000) {

			nivelMedio.interrupt();

			ventana.getPartida().setNivel(Partida.DIFICIL);
			ventana.getPanelOpciones().setTxtLevel(String.valueOf(ventana.getPartida().getNivel()));

			nivelDificil.start();

		}

		if (player.getScore() >= 15000) {

			nivelDificil.interrupt();

			ventana.getPartida().setNivel(Partida.EXTREMO);
			ventana.getPanelOpciones().setTxtLevel(String.valueOf(ventana.getPartida().getNivel()));

			nivelExtremo.start();

		}
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Personaje[] personajes = ventana.getPartida().getPersonajes();

		if (((e.getX() <= (personajes[0].getPosX() + 80)) && (e.getX() >= personajes[0].getPosX() - 80))
				&& (e.getY() <= (personajes[0].getPosY() + 80) && (e.getY() >= personajes[0].getPosY() - 80))) {

			personajes[0].setPosX(-400);
			personajes[0].setPosY(random.nextInt(650));

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		// System.out.println("llegue!");

	}

	@Override
	public void mouseExited(MouseEvent e) {

		// System.out.println("me fui");

	}

	// Si el usuario presiona a Dory
	@Override
	public void mousePressed(MouseEvent e) {

		Jugador player = ventana.continuePlayer(ventana.getPlayer());

		boolean condicionFacil = player.getScore() < 100;
		boolean condicionMedio = player.getScore() >= 100;
		boolean condicionDificil = player.getScore() >= 1000;
		boolean condicionExtremo = player.getScore() >= 15000;

		// System.out.println(e.getX() + "," + e.getY());
		Personaje[] personajes = ventana.getPartida().getPersonajes();

		// Condicion para saber si el raton atrapa o no a Dory
		if (((e.getX() <= (personajes[0].getPosX() + 80)) && (e.getX() >= personajes[0].getPosX() - 80))
				&& (e.getY() <= (personajes[0].getPosY() + 80) && (e.getY() >= personajes[0].getPosY() - 80))) {

			personajes[0].setPosX(-200);
			personajes[0].setPosY(random.nextInt(650));

			// Si la atrapa se suman puntos.
			if (condicionFacil) {
				player.setScore(player.getScore() + Partida.PUNTOS_FACIL);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));
			}

			if (condicionMedio) {

				player.setScore(player.getScore() + Partida.PUNTOS_MEDIO);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));
			}

			if (condicionDificil) {

				player.setScore(player.getScore() + Partida.PUNTOS_DIFICIL);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));
			}

			if (condicionExtremo) {

				player.setScore(player.getScore() + Partida.PUNTOS_EXTREMO);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));

			}

			// Si no la atrapa pierde puntos
		} else {

			if (condicionFacil) {
				player.setScore(player.getScore() - Partida.PUNTOS_NOFACIL);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));

			}

			if (condicionMedio) {

				player.setScore(player.getScore() - Partida.PUNTOS_NOMEDIO);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));

			}

			if (condicionDificil) {

				player.setScore(player.getScore() - Partida.PUNTOS_NODIFICIL);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));
			}

			if (condicionExtremo) {

				player.setScore(player.getScore() - Partida.PUNTOS_NOEXTREMO);
				ventana.getPanelOpciones().setTxtScore(String.valueOf(player.getScore()));

			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
