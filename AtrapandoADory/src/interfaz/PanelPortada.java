package interfaz;

import java.awt.*;
import javax.swing.*;

public class PanelPortada extends JPanel {

	private JLabel portada;

	public PanelPortada() {

		setLayout(new BorderLayout());

		portada = new JLabel();

		ImageIcon foto = new ImageIcon("./data/imagenes/portadaCrop.jpg");
		portada.setIcon(foto);

		add(portada);
			
	}

}
