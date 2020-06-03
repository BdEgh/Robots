package gui;

import other.Observer;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
	private final GameVisualizer m_visualizer;

	public GameWindow(Observer<Point> robotPosition) {
		super("Игровое поле", true, true, true, true);
		m_visualizer = new GameVisualizer();
		m_visualizer.addPositionObserver(robotPosition);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(m_visualizer, BorderLayout.CENTER);
		getContentPane().add(panel);
		pack();
	}
}
