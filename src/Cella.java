import java.util.ArrayList;

public class Cella {
	//accesso?
	//la lista effettiva delle persone in una cella
	private ArrayList<Persona> fila = new ArrayList<>();

	//prendi la persona dalla fila in base alla posizione in essa
	public Persona pos_get (int pos) {
		return fila.get(pos);
	}

	//prendi la persona dalla fila in base al suo ID
	public Persona id_get (int id) {
		for (int i = 0; i < fila.size(); i++) {
			if ( fila.get(i).getID() == id )
				return fila.get(i);
		}
		throw new PersonNotFoundException("la persona con l'id indicato non esiste nella cella");
	}

	// rimuovi la persona dalla fila
	public void remove (Persona p) {
		for (int i = 0; i < fila.size(); i++) {
			if ( fila.get(i) == p )
				fila.remove(i);
				return;
		}
		throw new PersonNotFoundException("la persona indicata non esiste nella cella");
	}

	//aggiungi in fondo alla fila una persona
	public void add(Persona p) {
		fila.add(p);
	}

	//ritorna la dimensione della fila
	public int size() {
		return fila.size();
	}
}
