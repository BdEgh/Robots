package window;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaverWindowState {
	private JInternalFrame[] _frames;

	public SaverWindowState(JInternalFrame[] frames) {
		_frames = frames;
	}

	public void SaveStateWindows() throws IOException {
		File fileJSON = new File("positions.json");
		ArrayList<Position> windowList = new ArrayList<>();
		for (int i = 0; i < _frames.length; i++) {
			Position window = new Position();
			window.setWidth(_frames[i].getWidth());
			window.setHeight(_frames[i].getHeight());
			window.setX(_frames[i].getX());
			window.setY(_frames[i].getY());
			window.setIconState(_frames[i].isIcon());
			windowList.add(window);
		}
		var positionWindow = new WindowsPosition();
		positionWindow.setWindows(windowList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(fileJSON, positionWindow);
	}

	public void RestoreStateWindows() throws IOException, PropertyVetoException {
		ObjectMapper mapper = new ObjectMapper();
		WindowsPosition windowsPosition = mapper.readValue(new File("positions.json"), WindowsPosition.class);
		for (int i = 0; i < windowsPosition.getWindows().size(); i++) {
			Position window = windowsPosition.getWindows().get(i);
			_frames[i].setSize(window.getWidth(), window.getHeight());
			_frames[i].setLocation(window.getX(), window.getY());
			_frames[i].setIcon(window.getIconState());
		}
	}
}
