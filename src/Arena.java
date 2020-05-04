import java.util.Random;
import java.util.ArrayList;

public class Arena {

	public int altezza;
	public int larghezza;
	public Cella[][] matrice;
	public ArrayList<Persona> persInMov;
	public ArrayList<Persona> persInIncubazione;
	//public ArrayList<Persona> persInCura;
	//public ArrayList<Persona> persMorte;
	//public ArrayList<Persona> persCurate;
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

	public int check_incontri(){
		int n_incontrate;
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				Cella c = matrice[i][j];
				if (c.size() > 1) {
					for (int k = 0; k < c.size(); k++) {
						for (int z = k+1; z < c.size()) {
							n_incontrate = n_incontrate + 2;
							incontra(c.pos_get(k), c.pos_get(z));
						}
					}
				}
			}
		}
		return n_incontrate;
	}

	public void incontra(Persona p1, Persona p2) {
		Stato s1 = p1.stato;
		Stato s2 = p2.stato;
		if (s1 == Stato.VERDE && (s2 == Stato.GIALLO || s2 == Stato.ROSSO))  {
			if (r.nextInt(100) < /*classe?*/Prova.I) {
				if (!persInIncubazione.contains(p1)) {
					persInIncubazione.add(p1);
					//p1.periodoIncubazione = Prova.D / 6;
				}
			}
		} else
		if ((s1 == Stato.GIALLO || s1 == Stato.ROSSO) && s2 == Stato.VERDE) {
			if (r.nextInt(100) < /*classe?*/Prova.I) {
				if (!persInIncubazione.contains(p2)) {
					persInIncubazione.add(p2);
					//p2.periodoIncubazione = Prova.D / 6;
				}
			}
		}

	}
}
