package gui;

import other.Notify;
import other.Observer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

class Vector {
	public volatile double x;
	public volatile double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector rotate(double angle) {
		return new Vector(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}
}

public class GameVisualizer extends JPanel {
	private final Timer m_timer = initTimer();

	private static Timer initTimer() {
		Timer timer = new Timer("events generator", true);
		return timer;
	}

	private final Notify<Point> notify = new Notify<>();

	private volatile Vector m_robotPosition = new Vector(100, 100);
	private volatile Vector m_robotDirection = new Vector(1, 0);
	private volatile Vector m_targetPosition = new Vector(150, 100);

	private static final double maxVelocity = 0.1;
	private static final double maxAngularVelocity = 0.001;

	public GameVisualizer() {
		m_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				onRedrawEvent();
			}
		}, 0, 50);
		m_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				onModelUpdateEvent();
			}
		}, 0, 10);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setTargetPosition(e.getPoint());
				repaint();
			}
		});
		setDoubleBuffered(true);
	}

	protected void setTargetPosition(Point p) {
		m_targetPosition = new Vector(p.x, p.y);
	}

	protected void onRedrawEvent() {
		EventQueue.invokeLater(this::repaint);
	}

	private static double distance(double x1, double y1, double x2, double y2) {
		double diffX = x1 - x2;
		double diffY = y1 - y2;
		return Math.sqrt(diffX * diffX + diffY * diffY);
	}

	private static double angleTo(Vector from, Vector to, Vector dir) {
		Vector rot = new Vector(to.x-from.x, to.y-from.y)
				.rotate(-Math.atan2(dir.y, dir.x));
		return Math.atan2(rot.y, rot.x);
	}

	protected void onModelUpdateEvent() {
		double distance = distance(m_targetPosition.x, m_targetPosition.y, m_robotPosition.x, m_robotPosition.y);
		if (distance < 0.5)
			return;

		double angleToTarget = angleTo(m_robotPosition, m_targetPosition, m_robotDirection);
		double angularVelocity = angleToTarget >= 0 ? maxAngularVelocity : -maxAngularVelocity;
		moveRobot(maxVelocity, angularVelocity, 10);
	}

	private static double applyLimits(double value, double min, double max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}

	private void moveRobot(double velocity, double angularVelocity, double duration) {
		angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
		velocity = applyLimits(velocity, 0, maxVelocity);
		double moveX = m_robotDirection.x * velocity * duration, moveY = m_robotDirection.y * velocity * duration;
		m_robotPosition.x += moveX;
		m_robotPosition.y += moveY;
		m_robotDirection = m_robotDirection.rotate(angularVelocity * duration);
		notify.notifyAll(new Point(round(m_robotPosition.x), round(m_robotPosition.y)));
	}

	private static int round(double value) {
		return (int) (value + 0.5);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		drawRobot(g2d, round(m_robotPosition.x), round(m_robotPosition.y),
				Math.atan2(m_robotDirection.y, m_robotDirection.x));
		drawTarget(g2d, round(m_targetPosition.x), round(m_targetPosition.y));
	}

	private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
		g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
	}

	private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
		g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
	}

	private void drawRobot(Graphics2D g, int x, int y, double direction) {
		int robotCenterX = round(m_robotPosition.x);
		int robotCenterY = round(m_robotPosition.y);
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

	public void addPositionObserver(Observer<Point> obs)
	{
		notify.addObserver(obs);
	}
}
