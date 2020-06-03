package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PosWindow extends JInternalFrame  implements Observer {
	private Point position = new Point(0, 0);
	private TextArea text;

	public PosWindow() {
		super("Координаты", false, true, false);
		text = new TextArea("");
		text.setSize(this.getSize());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(text, BorderLayout.CENTER);
		getContentPane().add(panel);
		pack();
	}

	@Override
	public void update(Observable o, Object obj) {
		if (!obj.equals(position)) {
			Point p = (Point)obj;
			position = p;
			text.setText(String.format("X=%s\nY=%s", p.x, p.y));
			text.invalidate();
		}
	}
}
