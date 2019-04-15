package interfaz;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import Excepciones.PlayerNotFoundByScoreException;
import modelo.*;
import hilos.*;

public class VentanaPrincipal extends JFrame {

	// Atributos
	private PanelPortada portada;
	private PanelOpciones options;
	private Partida partida;
	private PanelDibujo dibujo;
	private HiloPartida hiloFacil;
	private HiloPartidaMedio hiloMedio;
	private HiloPartidaDificil hiloDificil;
	private HiloPartidaExtremo hiloExtremo;
	private JLabel fondo;
	private String player;

	// Constructor
	public VentanaPrincipal() {

		setTitle("Atrapando a Dory");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1200, 1000);

		// Icono de la aplicacion
		JLabel imagen = new JLabel();
		ImageIcon icono = new ImageIcon("./data/imagenes/icono.jpg");
		setIconImage(icono.getImage());
		imagen.setIcon(icono);

		// Es el fondo cuando se abre la aplicacion
		fondo = new JLabel();
		ImageIcon background = new ImageIcon("./data/imagenes/fondoPro.jpg");
		fondo.setIcon(background);

		// Se inicializan los paneles y la partida.
		portada = new PanelPortada();
		options = new PanelOpciones(this);
		dibujo = new PanelDibujo(this);
		partida = new Partida(Partida.FACIL);

		iniciar();
		infoGame();

		// crearPartida();

		// Se agregan los componentes
		add(portada, BorderLayout.NORTH);
		add(options, BorderLayout.SOUTH);
		add(fondo, BorderLayout.CENTER);

		// Se inicializan los hilos.
		hiloFacil = new HiloPartida(partida);
		hiloMedio = new HiloPartidaMedio(partida);
		hiloDificil = new HiloPartidaDificil(partida);
		hiloExtremo = new HiloPartidaExtremo(partida);

		setVisible(true);

	}

	// Este método es para conseguir el String que ayuda a conseguir el jugador que
	// se busca en el ArrayList players que tiene la clase Partida. (Vease los
	// métodos resumeGame(), continuePlayer() y newPlayer())
	public String getPlayer() {

		return player;

	}

	public PanelDibujo getPanelDibujo() {

		return dibujo;

	}

	public PanelOpciones getPanelOpciones() {

		return options;

	}

	public JLabel getFondo() {

		return fondo;

	}

	// Este método es para preguntarle al usuario si va a ingresar un nickname nuevo
	// o va a continuar con uno que ya está registrado.
	public void iniciar() {

		String[] opciones = { "New Game", "Resume Game" };

		ImageIcon icon = new ImageIcon("./data/imagenes/doryFinal.png");

		int option = JOptionPane.showOptionDialog(null, "Select an option", "What you want to do?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, opciones, 0);

		if (option == 0) {

			newPlayer();

		}

		else if (option == 1) {

			resumeGame();
			updateInfoResumePlayer();

		} else {

			System.exit(0);

		}

	}

	// Este método es para guardar el estado del modelo (Persistencia), en este caso
	// se serializa la clase Jugador a traves de una relacion que se tiene de la
	// Clase Partida con la clase Jugador como un ArrayList de tipo Jugador
	public void save() {

		FileOutputStream fS = null;
		ObjectOutputStream oS = null;
		ArrayList<Jugador> players = partida.getPlayers();

		try {

			fS = new FileOutputStream("./files/dataGames.dat");
			oS = new ObjectOutputStream(fS);

			oS.writeObject(players);

		} catch (FileNotFoundException ex) {

			System.out.println(ex.getMessage());

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		} finally {

			try {

				if (players != null) {

					fS.close();

				}
				if (oS != null) {

					oS.close();

				}

			} catch (IOException ex) {

				System.out.println(ex.getMessage());

			}
		}

	}

	public Partida getPartida() {

		return partida;

	}

	// Este método se encarga de des-serializar, es decir, que la aplicacion cuenta
	// con la informacion que ya ha sido serializada y se la pasa al ArrayList de
	// tipo Jugador del modelo, en pocas palabras actualiza el ArrayList con el que
	// ha sido serializado antes.
	public void recuperarInfo() {

		FileInputStream fS = null;
		ObjectInputStream oS = null;

		ArrayList<Jugador> players = null;

		try {

			fS = new FileInputStream("./files/dataGames.dat");
			oS = new ObjectInputStream(fS);
			players = (ArrayList<Jugador>) oS.readObject();
			partida.setPlayers(players);

			// for (int i = 0; i < players.size(); i++) {
			// System.out.println(players.get(i));
			// }

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		} finally {

			try {
				if (fS != null) {
					fS.close();
				}
				if (oS != null) {
					oS.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}

	}

	/*
	 * Este método se encarga de crear un nuevo Jugador y solamente se ejecuta si el
	 * usuario va a ingresar un nuevo Jugador.
	 */
	public void newPlayer() {

		String nickname = JOptionPane.showInputDialog("Ingrese su nickname", "nickname");

		if (nickname != null) {
			recuperarInfo();

			partida.getPlayers().add(new Jugador(nickname, Jugador.PUNTAJE_INICIAL));

			options.setTxtPlayer(partida.getPlayers().get(partida.getPlayers().size() - 1).getNickname());
			options.setTxtLevel(String.valueOf(partida.getNivel()));
			options.setTxtScore(String.valueOf(partida.getPlayers().get(partida.getPlayers().size() - 1).getScore()));

			player = partida.getPlayers().get(partida.getPlayers().size() - 1).getNickname();
		} else {

			iniciar();

		}
	}

	/*
	 * Este método se encarga de actualizar la informacion que aparece en pantalla
	 * con el Jugador que se haya creado o se haya seleccionado en caso de que este
	 * haya sido guardado
	 */
	public void updateInfoResumePlayer() {

		Jugador jugador = continuePlayer(player);

		if (jugador != null) {
			options.setTxtPlayer(jugador.getNickname());
			options.setTxtLevel(String.valueOf(partida.getNivel()));
			options.setTxtScore(String.valueOf(jugador.getScore()));
		} else {

			iniciar();

		}
	}

	/*
	 * Este método se encarga de mostrar los jugadores que han sido guardados por si
	 * el usuario quiere continuar jugando con uno de esos.
	 */
	public void resumeGame() {

		recuperarInfo();

		ArrayList<String> nicknames = new ArrayList<String>();

		for (int i = 0; i < partida.getPlayers().size(); i++) {

			nicknames.add(partida.getPlayers().get(i).getNickname());

		}

		String[] nicks = new String[partida.getPlayers().size()];
		nicks = nicknames.toArray(nicks);

		ImageIcon icon = new ImageIcon("./data/imagenes/mandoFinal.jpg");

		player = (String) JOptionPane.showInputDialog(null, "Seleccione su nickname para continuar jugando",
				"Jugadores registrados", JOptionPane.DEFAULT_OPTION, icon, nicks, nicks[0]);

	}

	// Este método se encarga de encontrar el Jugador que ha sido seleccionado
	// cuando el usuario quiere jugar con un Jugador ya registrado.
	public Jugador continuePlayer(String nickname) {

		ArrayList<Jugador> players = partida.getPlayers();
		Jugador elJugador = null;

		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).getNickname().equals(nickname)) {

				elJugador = players.get(i);

			}

		}

		return elJugador;

	}

	// Este método se encarga de mostrar la info de los jugadores con base en sus
	// puntajes, utilizando el método de ordenamiento que se realiza en la clase
	// principal del modelo. (Partida)
	public void ordenarJugadoresPorPuntaje() {

		ArrayList<Jugador> ordenado = partida.ordenarJugadoresPorPuntaje(partida.getPlayers());
		int num = 1;

		String[] columnas = { "#", "Nickname", "Score" };
		Object[][] data = new Object[ordenado.size()][ordenado.size()];

		for (int i = 0; i < data.length; i++) {

			data[i][0] = num;
			data[i][1] = ordenado.get(i).getNickname();
			data[i][2] = ordenado.get(i).getScore();
			num++;
		}

		JTable myTable = new JTable(data, columnas);
		myTable.setEnabled(false);
		myTable.getTableHeader().setReorderingAllowed(false);
		myTable.setFont(new Font("Garamond", 1, 16));
		JScrollPane scrollPane = new JScrollPane(myTable);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		ImageIcon icon = new ImageIcon("./data/imagenes/trophyFinal.jpg");
		JOptionPane.showMessageDialog(null, scrollPane, "Highscores", 1, icon);

	}

	// Este método se encarga de mostrar la info de los jugadores organizados por su
	// nombre.
	public void ordenarJugadoresPorNombre() {

		ArrayList<Jugador> ordenado = partida.ordenarJugadoresPorNombre(partida.getPlayers());

		String[] columnas = { "Nickname", "Score" };
		Object[][] data = new Object[ordenado.size()][ordenado.size()];

		for (int i = 0; i < data.length; i++) {

			data[i][0] = ordenado.get(i).getNickname();
			data[i][1] = ordenado.get(i).getScore();
		}

		JTable myTable = new JTable(data, columnas);
		myTable.setEnabled(false);
		myTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(myTable);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		myTable.setFont(new Font("Garamond", 1, 16));

		JOptionPane.showMessageDialog(null, scrollPane, "Players by name", 1);

	}

	// Este método se encarga de mostrar el jugador que se busque utilizando el
	// método de busqueda binaria implementado en el modelo. (Clase Partida)
	public Jugador buscarJugadorPorPuntaje() {

		Jugador player = null;
		String puntaje = JOptionPane.showInputDialog(null, "Introduzca un puntaje", "Search a player", 1);

		if (puntaje.equals("") || puntaje == null) {

			JOptionPane.showMessageDialog(null, "Debe ingresar un valor para realizar la búsqueda");

		}

		else {
			int score = Integer.parseInt(puntaje);

			try {
				player = partida.buscarJugadorPorPuntaje(score);
				JOptionPane.showMessageDialog(null, "El jugador con " + score + " puntos es " + player.getNickname());
			} catch (PlayerNotFoundByScoreException ex) {

				JOptionPane.showMessageDialog(null, ex.getMessage());

			}
		}
		return player;

	}

	// Refresca el panel dibujo
	public void refreshDibujos() {

		dibujo.invalidate();
		dibujo.validate();
		dibujo.repaint();

	}

	// Este método se encarga de archivar los jugadores registrados en un archivo
	// txt.
	public void archivarJugadoresRegistrados() throws IOException {

		recuperarInfo();

		File archivo = new File("./files/jugadoresRegistrados.txt");

		if (archivo.exists()) {

			System.out.println("El archivo ha sido sobreescrito y guardado exitosamente.");

		} else {

			try {
				archivo.createNewFile();
			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

		FileWriter fW = new FileWriter("./files/jugadoresRegistrados.txt");
		BufferedWriter bW = new BufferedWriter(fW);

		for (int i = 0; i < partida.getPlayers().size(); i++) {

			int nivelAlcanzado = 0;

			if (partida.getPlayers().get(i).getScore() < 100) {

				nivelAlcanzado = 1;

			}

			if (partida.getPlayers().get(i).getScore() >= 100) {

				nivelAlcanzado = 2;

			}

			if (partida.getPlayers().get(i).getScore() >= 1000) {

				nivelAlcanzado = 3;

			}

			if (partida.getPlayers().get(i).getScore() >= 15000) {

				nivelAlcanzado = 4;

			}

			bW.write(partida.getPlayers().get(i).toString() + "  -  " + "	Nivel Alcanzado: " + nivelAlcanzado);
			bW.newLine();

		}

		bW.close();

	}

	// Métodos para obtener los hilos
	public HiloPartida getHiloFacil() {

		return hiloFacil;

	}

	public HiloPartidaMedio getHiloMedio() {

		return hiloMedio;

	}

	public HiloPartidaDificil getHiloDificil() {

		return hiloDificil;

	}

	public HiloPartidaExtremo getHiloExtremo() {

		return hiloExtremo;

	}

	// Este método se encarga de mostrarle informacion relacionada a los niveles y
	// los puntos del juego.
	public void infoGame() {

		String info = "Nivel 1: De 0 a 99 puntos \n" + "Nivel 2: De 100 a 999 puntos \n"
				+ "Nivel 3: De 1000 a 14999 puntos \n" + "Nivel 4: +15000";
		String infoPuntos = "Nivel 1: Se suman los puntos de a 10 cada vez que se atrape a Dory.\nSi no se atrapa a Dory se le restan 5 puntos al Jugador \n"
				+ "Nivel 2: Se suman los puntos de a 100 cada vez que se atrape a Dory.\nSi no se atrapa a Dory se le restan 100 puntos al Jugador \n"
				+ "Nivel 3: Se suman los puntos de a 1000 cada vez que se atrape a Dory.\nSi no se atrapa a Dory se le restan 500 puntos al Jugador \n"
				+ "Nivel 4: Se suman los puntos de a 1500 cada vez que se atrape a Dory.\nSi no se atrapa a Dory se le restan 750 puntos al Jugador \n";

		JOptionPane.showMessageDialog(null, info, "Info", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, infoPuntos, "Info", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "¡¡ Buena Suerte !!");

	}

	// Refresca la ventana principal.
	public void refreshFrame() {

		invalidate();
		validate();
		repaint();

	}

	public static void main(String[] args) {

		VentanaPrincipal game = new VentanaPrincipal();

	}

}
