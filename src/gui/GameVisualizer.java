package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel {
	private final Timer m_timer = initTimer();

	private static Timer initTimer() {
		Timer timer = new Timer("events generator", true);
		return timer;
	}

	private static int round(double value) {
		return (int) (value + 0.5);
	}

	private Robot robot;

	public GameVisualizer(ArrayList<Observer> robotPosition) {
		robot = new Robot();
		for (Observer o : robotPosition)
			robot.addObserver(o);

		m_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				onRedrawEvent();
			}
		}, 0, 50);
		m_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				robot.onModelUpdateEvent();
			}
		}, 0, 10);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				robot.setTargetPosition(e.getPoint());
				repaint();
			}
		});
		setDoubleBuffered(true);
	}

	protected void onRedrawEvent() {
		EventQueue.invokeLater(this::repaint);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		drawRobot(g2d, round(robot.getRobotPos().x), round(robot.getRobotPos().y),
				Math.atan2(robot.getDirection().y, robot.getDirection().x));
		drawTarget(g2d, round(robot.getTargetPos().x), round(robot.getTargetPos().y));
	}

	private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
		g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
	}

	private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
		g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
	}

	private void drawRobot(Graphics2D g, int x, int y, double direction) {
		int robotCenterX = round(robot.getRobotPos().x);
		int robotCenterY = round(robot.getRobotPos().y);
		AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
		g.setTransform(t);
		g.setColor(Color.MAGENTA);
		fillOval(g, robotCenterX, robotCenterY, 30, 10);
		g.setColor(Color.BLACK);
		drawOval(g, robotCenterX, robotCenterY, 30, 10);
		g.setColor(Color.WHITE);
		fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
		g.setColor(Color.BLACK);
		drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
	}

	private void drawTarget(Graphics2D g, int x, int y) {
		AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
		g.setTransform(t);
		g.setColor(Color.GREEN);
		fillOval(g, x, y, 5, 5);
		g.setColor(Color.BLACK);
		drawOval(g, x, y, 5, 5);
	}
}
