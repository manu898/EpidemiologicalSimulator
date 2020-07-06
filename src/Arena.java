import java.util.Random;
import java.util.ArrayList;
public class Arena {

	//altezza della matrice
	private int altezza;

	//larghezza della matrice
	private int larghezza;

	//la matrice di celle in cui possono andare le persone
	private Cella[][] matrice;

	//variabile di tipo Random per calcolare lo spostamento
	public static Random r = new Random(); //rendi private (e non static?)

	//massimo spostamento di una persona nella matrice
	private int spostamentoMax;

	public Arena (int altezza, int larghezza, int spostamentoMax){
		r = new Random();
		setSpostamentoMax(spostamentoMax);
		this.altezza = altezza;
		this.larghezza = larghezza;
		matrice = new Cella[altezza][larghezza];  // creo la matrice
		
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				matrice[i][j] = new Cella();
			}
		}
	}

	// Andiamo a distribuire le persone (le abbiamo già generate in simulazione). In una cella possono anche esserci più persone inizialmente
	public void distribuisciPersone(ArrayList<Persona> persone){

		// Per ogni persona nell'arrayList scegliamo una cella randomicamente in cui andremo a posizionarla
		for (Persona persona: persone) {

			int x = r.nextInt(larghezza);
			int y = r.nextInt(altezza);

			matrice[y][x].add(persona);
			persona.setPosizione(y,x);

		}


	}

	//fai spostare le persone all'interno dell'arena
	public void move(ArrayList<Persona> persone) {  //TEST sembra OK, si puo' snellire il codice

		for(Persona persona : persone ){
			if(persona.getMovimento()){
				//spostamento max a sx o in alto
				int low;
				//spostamento max a dx o in basso
				int high;
				//la nuova coordinata y in cui andra' la persona
				int newY;
				//la nuova coordinata x in cui andra' la persona
				int newX;

				int y = persona.getPosizione().getY();
				int x = persona.getPosizione().getX();
				//System.out.println("Persona " + persona.getID() + " e' in " + persona.getPosizione().getY() + "," + persona.getPosizione().getX());

				matrice[y][x].remove(persona);
				//System.out.println("rimuovo la persona dalla cella");

				//calcoliamo la nuova y
				if(y >= spostamentoMax){
					low = - spostamentoMax;
					if(altezza - y > spostamentoMax){
						high = spostamentoMax;
					}else{
						high = altezza - y - 1;
					}
					newY = r.nextInt(high - low + 1) + low + y;
				}else{
					low = -y;
					if (altezza - y > spostamentoMax) {
						high = spostamentoMax;
					} else {
						high = altezza - y - 1;
					}
					newY = r.nextInt(high - low + 1) + low + y;
				}

				//calcoliamo la nuova x
				if(x >= spostamentoMax){
					low = - spostamentoMax;
					if(larghezza - x > spostamentoMax){
						high = spostamentoMax;
					}else{
						high = larghezza - x - 1;
					}
					newX = r.nextInt(high - low + 1) + low + x;
				}else{
					low = - x;
					if (larghezza - x > spostamentoMax) {
						high = spostamentoMax;
					} else {
						high = larghezza - x - 1;
					}
					newX = r.nextInt(high - low + 1) + low + x;
				}
				matrice[newY][newX].add(persona);
				//System.out.println("Aggiungo la persona " + persona.getID() + "alla cella " + newY + "," + newX);
				persona.setPosizione(newY,newX);

			}
		}
	}


	//verifica gli incontri che ci sono in ogni cella dell'arena
	public int check_incontri(){  //TEST OK
		int n_incontrate = 0;
		//System.out.println("Incontrate in arena all'inizio: " + n_incontrate);

		boolean res;
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				Cella c = matrice[i][j];
				if (c.size() > 1) {
					//System.out.println(c.size());
					//System.out.println("SONO NELLA CELLA " + i + "," + j);
					for (int k = 0; k < c.size(); k++) {
						for (int z = k+1; z < c.size();z++) {
							//System.out.println("SONO NELLA CELLA " + i + "," + j);
							Persona p1 = c.pos_get(k);
							Persona p2 = c.pos_get(z);
							//System.out.println("Incontro sta per esserci tra: " + p1.getID() + " e " + p2.getID());

							if (p1.getMovimento() == true || p2.getMovimento() == true) {
								res = incontra(p1, p2); // prendo le due persone scelte dalla fila e le faccio incontrare
								if (res) {
									//System.out.println("n_incontrate durante gli incontri: " + n_incontrate);
									//System.out.println("Incontro tra: " + p1.getID() + " e " + p2.getID());
									n_incontrate = n_incontrate + 2;
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Incontrate in arena: " + n_incontrate);
		return n_incontrate;
	}

	// fa incontrare due persone
	public boolean incontra(Persona p1, Persona p2) {
		StatoSalute s1 = p1.getStato();
		StatoSalute s2 = p2.getStato();
		if (s1 == StatoSalute.NERO || s2 == StatoSalute.NERO) {
			return false;
		}
		if (s1 == StatoSalute.VERDE && (s2 == StatoSalute.GIALLO || s2 == StatoSalute.ROSSO))  {
			p1.contatto(p2.getVir());
		} else
		if ((s1 == StatoSalute.GIALLO || s1 == StatoSalute.ROSSO) && s2 == StatoSalute.VERDE) {
			p2.contatto(p1.getVir());
		}
		p1.addPersona_incontrata(p2);
		p2.addPersona_incontrata(p1);
		return true;
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

	public int getSpostamentoMax() { return spostamentoMax; }

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
		if ( spostamentoMax < 1) throw new IllegalArgumentException("Lo spostamento deve essere almeno di 1");
		this.spostamentoMax = spostamentoMax;
	}

}
