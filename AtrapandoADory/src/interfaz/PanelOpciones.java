package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelOpciones extends JPanel implements ActionListener {

	// Constantes para las acciones de los botones
	public final static String SAVE = "save";
	public final static String HIGHSCORES = "highscores";
	public final static String START = "start";
	public final static String INFO = "info";
	public final static String PLAYERS = "players";
	public final static String SEARCH = "search";

	// Botones, labels y paneles de texto
	private VentanaPrincipal ventana;
	private JButton butSave;
	private JButton butHighscores;
	private JButton butStart;
	private JButton butInfo;
	private JButton butSearch;
	private JLabel labScore;
	private JLabel labPlayer;
	private JLabel labLevel;
	private JButton butPlayers;
	private JTextField txtScore;
	private JTextField txtPlayer;
	private JTextField txtLevel;

	// Paneles Auxiliares
	private JPanel panelAuxiliarGame;
	private JPanel panelAuxiliarOptions;

	// Constructor
	public PanelOpciones(VentanaPrincipal window) {

		ventana = window;

		setLayout(new GridLayout(2, 1));

		// Se inicializan los paneles auxiliares
		panelAuxiliarGame = new JPanel();
		panelAuxiliarOptions = new JPanel();

		// Panel Auxiliar para la info que se muestra en el juego.
		panelAuxiliarGame.setLayout(new GridLayout(2, 3));
		labPlayer = new JLabel("Player:");
		txtPlayer = new JTextField();
		labScore = new JLabel("Score:");
		txtScore = new JTextField();
		labLevel = new JLabel("Level:");
		txtLevel = new JTextField();

		labPlayer.setFont(new Font("Garamond", 1, 20));
		labLevel.setFont(new Font("Garamond", 1, 20));
		labScore.setFont(new Font("Garamond", 1, 20));
		txtLevel.setFont(new Font("Garamond", 1, 20));
		txtPlayer.setFont(new Font("Garamond", 1, 20));
		txtScore.setFont(new Font("Garamond", 1, 20));

		TitledBorder borderGame = BorderFactory.createTitledBorder(" Game ");
		borderGame.setTitleColor(Color.BLACK);
		borderGame.setTitleFont(new Font("Garamond", 1, 20));
		panelAuxiliarGame.setBorder(borderGame);
		panelAuxiliarGame.add(labPlayer);
		panelAuxiliarGame.add(labScore);
		panelAuxiliarGame.add(labLevel);
		panelAuxiliarGame.add(txtPlayer);
		panelAuxiliarGame.add(txtScore);
		panelAuxiliarGame.add(txtLevel);

		txtPlayer.setEditable(false);
		txtScore.setEditable(false);
		txtLevel.setEditable(false);

		// Panel auxiliar para las opciones
		panelAuxiliarOptions.setLayout(new GridLayout(1, 5));

		butHighscores = new JButton("HIGHSCORES");
		butSave = new JButton("SAVE");
		butStart = new JButton("START");
		butInfo = new JButton("INFO");
		butPlayers = new JButton("SEE PLAYERS BY NAME");
		butSearch = new JButton("SEARCH A PLAYER");

		butHighscores.setActionCommand(HIGHSCORES);
		butHighscores.addActionListener(this);
		butSave.setActionCommand(SAVE);
		butSave.addActionListener(this);
		butStart.setActionCommand(START);
		butStart.addActionListener(this);
		butInfo.setActionCommand(INFO);
		butInfo.addActionListener(this);
		butPlayers.setActionCommand(PLAYERS);
		butPlayers.addActionListener(this);
		butSearch.setActionCommand(SEARCH);
		butSearch.addActionListener(this);

		TitledBorder borderOptions = BorderFactory.createTitledBorder(" Options ");
		borderOptions.setTitleColor(Color.BLACK);
		borderOptions.setTitleFont(new Font("Garamond", 1, 20));
		panelAuxiliarOptions.setBorder(borderOptions);
		panelAuxiliarOptions.add(butInfo);
		panelAuxiliarOptions.add(butPlayers);
		panelAuxiliarOptions.add(butHighscores);
		panelAuxiliarOptions.add(butSearch);
		panelAuxiliarOptions.add(butSave);
		panelAuxiliarOptions.add(butStart);

		add(panelAuxiliarGame);
		add(panelAuxiliarOptions);

	}

	public int getTxtScore() {
		return Integer.parseInt(txtScore.getText());
	}

	public void setTxtScore(String text) {
		txtScore.setText(text);
	}

	public String getTxtPlayer() {
		return txtPlayer.getText();
	}

	public void setTxtPlayer(String text) {
		txtPlayer.setText(text);
	}

	public int getTxtLevel() {
		return Integer.parseInt(txtLevel.getText());
	}

	public void setTxtLevel(String text) {

		txtLevel.setText(text);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals(START)) {

			ventana.remove(ventana.getFondo());
			ventana.add(ventana.getPanelDibujo(), BorderLayout.CENTER);
			ventana.refreshFrame();
			ventana.refreshDibujos();

		}

		if (command.equals(SAVE)) {

			ventana.save();
			try {
				ventana.archivarJugadoresRegistrados();
			} catch (Exception ex) {

				ex.printStackTrace();

			}
			JOptionPane.showMessageDialog(null, "Juego guardado con éxito", "Save", JOptionPane.INFORMATION_MESSAGE);

		}
		
		if(command.equals(SEARCH)) {
			
		
		ventana.buscarJugadorPorPuntaje();	
			
			
		}

		if (command.equals(HIGHSCORES)) {

			ventana.ordenarJugadoresPorPuntaje();

		}

		if (command.equals(PLAYERS)) {

			ventana.ordenarJugadoresPorNombre();

		}

		if (command.equals(INFO)) {

			ventana.infoGame();

		}

	}

}
