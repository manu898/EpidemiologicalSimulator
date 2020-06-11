import java.util.Random;
import java.util.ArrayList;
public class Arena {

	private int altezza;
	private int larghezza;
	private Cella[][] matrice;
	private Random r;
	private int spostamentoMax = 10;

	public Arena (int altezza, int larghezza){
		r = new Random();
		this.altezza = altezza;
		this.larghezza = larghezza;
		matrice = new Cella[altezza][larghezza];  // creo la matrice
		
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				matrice[i][j] = new Cella();
			}
		}
	}

	// Andiamo a distribuire le persone (le abbiamo già generate in universo). In una cella possono anche esserci più persone inizialmente
	public void distribuisciPersone(ArrayList<Persona> persone){

		// Per ogni persona nell'arrayList scegliamo una cella randomicamente in cui andremo a posizionarla
		for (Persona persona: persone) {

			int x = r.nextInt(larghezza);
			int y = r.nextInt(altezza);

			matrice[y][x].add(persona);
			persona.setPosizione(y,x);

		}


	}

	public void move(ArrayList<Persona> persone) throws PersonNotFoundException{

		for(Persona persona : persone ){
			if(persona.getMovimento()){
				int low;
				int high;
				int newY;
				int newX;

				int y = persona.getPosizione().getY();
				int x = persona.getPosizione().getX();

				// in ogni cella abbiamo una lista di Persone
				matrice[y][x].remove(persona.getID());

				if(y >= spostamentoMax){
					low = - spostamentoMax;
					if(altezza - y >= spostamentoMax){
						high = spostamentoMax;
					}else{
						high = altezza - y - 1;
					}
					newY = r.nextInt(high - low + 1) + low + y;
				}else{
					low = -y;
					if (altezza - y >= spostamentoMax) {
						high = spostamentoMax;
					} else {
						high = altezza - y - 1;
					}
					newY = r.nextInt(high - low + 1) + low + y;
				}


				if(x >= spostamentoMax){
					low = - spostamentoMax;
					if(larghezza - x >= spostamentoMax){
						high = spostamentoMax;
					}else{
						high = larghezza - x - 1;
					}
					newX = r.nextInt(high - low + 1) + low + x;
				}else{
					low = - x;
					if (larghezza - x >= spostamentoMax) {
						high = spostamentoMax;
					} else {
						high = larghezza - x - 1;
					}
					newX = r.nextInt(high - low + 1) + low + x;
				}

				/*
				if(y >= spostamentoMax && altezza - y >= spostamentoMax){
					low = - spostamentoMax;
					high = spostamentoMax;
					newY = r.nextInt(high - low) + low + y;
				}else if(y < spostamentoMax && altezza - y >= spostamentoMax){
					low = - y;
					high = spostamentoMax;
					newY = r.nextInt(high - low) + low + y;
				}else if(y >= spostamentoMax && altezza - y < spostamentoMax){
					low = - spostamentoMax;
					high = altezza - y;
					newY = r.nextInt(high - low) + low + y;
				}else{
					low = - y;
					high = altezza - y;
					newY = r.nextInt(high - low) + low + y;

				}

				if(x >= spostamentoMax && larghezza - x >= spostamentoMax){
					low = - spostamentoMax;
					high = spostamentoMax;
					newX = r.nextInt(high - low) + low + x;
				}else if(x < spostamentoMax && larghezza - x >= spostamentoMax){
					low = - x;
					high = spostamentoMax;
					newX = r.nextInt(high - low) + low + x;
				}else if(x >= spostamentoMax && larghezza - x < spostamentoMax){
					low = - spostamentoMax;
					high = larghezza - x;
					newX = r.nextInt(high - low) + low + x;
				}else{
					low = - x;
					high = altezza - x;
					newX = r.nextInt(high - low) + low + x;
				}*/


				matrice[newY][newX].add(persona);
				persona.setPosizione(newY,newX);

			}
		}
	}



	public int check_incontri(){
		int n_incontrate = 0;
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				Cella c = matrice[i][j];
				if (c.size() > 1) {
					for (int k = 0; k < c.size(); k++) {
						for (int z = k+1; z < c.size();z++) {
							n_incontrate = n_incontrate + 2;
							// qui andiamo ad inserire l'incontro in questione nelle liste incontri di entrambe le persone
							incontra(c.pos_get(k), c.pos_get(z)); // prendo le due persone scelte dalla fila e le faccio incontrare
						}
					}
				}
			}
		}
		return n_incontrate;
	}

	// qui andiamo a testare l'infettività
	public void incontra(Persona p1, Persona p2) {
		StatoSalute s1 = p1.getStato();
		StatoSalute s2 = p2.getStato();
		if (s1 == StatoSalute.VERDE && (s2 == StatoSalute.GIALLO || s2 == StatoSalute.ROSSO))  {
			p1.contatto(p2.getVir());
		} else
		if ((s1 == StatoSalute.GIALLO || s1 == StatoSalute.ROSSO) && s2 == StatoSalute.VERDE) {
			p2.contatto(p1.getVir());
		}

	}

	// setter

	public void setAltezza(int altezza) {
		this.altezza = altezza;
	}

	public void setLarghezza(int larghezza) {
		this.larghezza = larghezza;
	}

	public void setMatrice(Cella[][] matrice) {
		this.matrice = matrice;
	}

	public void setSpostamentoMax(int spostamentoMax) {
		this.spostamentoMax = spostamentoMax;
	}


	// getter

	public Cella[][] getMatrice() {
		return matrice;
	}

	public int getAltezza() {
		return altezza;
	}

	public int getLarghezza() {
		return larghezza;
	}

	public int getSpostamentoMax() {
		return spostamentoMax;
	}


}
