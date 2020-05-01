import java.util.ArrayList;

public class Cella {
	//accesso?
	public ArrayList<Persona> fila = new ArrayList<>();

	public void remove (int id) throws PersonNotFoundException {
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
