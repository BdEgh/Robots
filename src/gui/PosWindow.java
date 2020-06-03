package gui;

import other.Observer;

import javax.swing.*;
import java.awt.*;

public class PosWindow extends JInternalFrame  implements Observer<Point> {
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
	public void Update(Point o) {
		if (!o.equals(position)) {
			position = o;
			text.setText(String.format("X=%s\nY=%s", o.x, o.y));
			text.invalidate();
		}
	}
}
