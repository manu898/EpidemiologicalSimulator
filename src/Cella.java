import java.util.ArrayList;

public class Cella {
	//accesso?
	//la lista effettiva delle persone in una cella
	private ArrayList<Persona> fila = new ArrayList<>();

	//prendi la persona dalla fila in base alla posizione in essa
	public Persona pos_get (int pos) {
		return fila.get(pos);
	}

	//si potrebbe fare come input la persona stessa invece del suo ID?
	//prendi la persona dalla fila in base al suo ID
	public Persona id_get (int id) throws PersonNotFoundException {
		for (int i = 0; i < fila.size(); i++) {
			//sostituisci ID con getID()
			if ( fila.get(i).ID == id )
				return fila.get(i);
		}
		throw new PersonNotFoundException();	
	}

	//si potrebbe fare come input la persona stessa invece del suo ID?
	// rimuovi la persona dalla fila in base al suo id
	public void remove (int id) throws PersonNotFoundException {
		for (int i = 0; i < fila.size(); i++) {
			//sostituisci ID con getID()
			if ( fila.get(i).ID == id )
				fila.remove(i);
				return;
		}
		throw new PersonNotFoundException();
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
