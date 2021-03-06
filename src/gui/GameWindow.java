package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
	private final GameVisualizer m_visualizer;

	public GameWindow(ArrayList<Observer> robotPosition) {
		super("Игровое поле", true, true, true, true);
		m_visualizer = new GameVisualizer(robotPosition);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(m_visualizer, BorderLayout.CENTER);
		getContentPane().add(panel);
		pack();
	}
}
