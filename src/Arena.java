import java.util.Random;
import java.util.ArrayList;

public class Arena {

	public int altezza;
	public int larghezza;
	public Cella[][] matrice;
	public ArrayList<Persona> persInMov;
	public Random r;

	public Arena (int altezza, int larghezza){
		r = new Random();
		this.altezza = altezza;
		this.larghezza = larghezza;
		matrice = new Cella[altezza][larghezza];
		persInMov = new ArrayList<Persona>();
		
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				matrice[i][j] = new Cella();
			}
		}
	}

	public void init(int persone) {
		//Random r = new Random();
		int id = 0;
		while (id < persone) {
			int x = r.nextInt(larghezza);
			int y = r.nextInt(altezza);
			if (matrice[y][x].size() == 0) {
				Persona p = new Persona(id, y, x);
				matrice[y][x].add(p);
				persInMov.add(p);
				id++;
			}
		}
		persInMov.get(0).stato = Stato.GIALLO;
	}

	public void move() throws PersonNotFoundException{
		//Random r = new Random();
		for (Persona p : persInMov) {
			int y = p.posizione.getY();
			int x = p.posizione.getX();
			matrice[y][x].remove(p.ID);
			int y_app = r.nextInt(altezza);
			int x_app = r.nextInt(larghezza);
			p.posizione.y = y_app;
			p.posizione.x = x_app;
			matrice[y_app][x_app].add(p);

		}
	}
}
