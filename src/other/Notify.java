package other;

import java.util.ArrayList;
import java.util.List;

public class Notify<T> {
	private List<Observer<T>> observers = new ArrayList<>();

	public void addObserver(Observer<T> obs) {
		observers.add(obs);
	}

	public void removeObserver(Observer<T> obs) {
		observers.remove(obs);
	}

	public void notifyAll(T o) {
		for (var obs : observers)
			obs.Update(o);
	}
}
