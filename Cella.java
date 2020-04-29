import java.util.ArrayList;

public class Cella {
	//accesso?
	ArrayList<Persona> fila = new ArrayList<>();

	public void remove throws PersonNotFoundException (int id) {
		for (int i = 0; i < fila.size(); i++) {
			if ( fila.get(i).ID == id )
				fila.remove(i);
				return;
		}
		throw new PersonNotFoundException();
	}

	public void add(Persona p) {
		fila.add(p);
	}

	public int size() {
		return fila.size();
	}
}
