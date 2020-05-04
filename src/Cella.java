import java.util.ArrayList;

public class Cella {
	//accesso?
	public ArrayList<Persona> fila = new ArrayList<>();

	public Persona pos_get (int pos) {
		return fila.get(pos);
	}

	public Persona id_get (int id) throws PersonNotFoundException {
		for (int i = 0; i < fila.size(); i++) {
			if ( fila.get(i).ID == id )
				return fila.get(i);
		}
		throw new PersonNotFoundException();	
	}

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
