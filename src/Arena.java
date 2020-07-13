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
	private static Random r = new Random();

	//massimo spostamento di una persona nella matrice
	private int spostamentoMax;

	//costruttore
	public Arena (int altezza, int larghezza, int spostamentoMax){
		this.spostamentoMax = spostamentoMax;
		this.altezza = altezza;
		this.larghezza = larghezza;
		matrice = new Cella[altezza][larghezza];  // viene creata la matrice
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

	//fa spostare le persone all'interno dell'arena
	public void move(ArrayList<Persona> persone) {

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

				matrice[y][x].remove(persona);

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
				persona.setPosizione(newY,newX);

			}
		}
	}


	//verifica gli incontri che ci sono in ogni cella dell'arena
	public int check_incontri(){
		//il numero totale di persone incontrate (nota: se due persone si incontrano allora le persone
		//incontrate in totale aumentano di 2)
		int n_incontrate = 0;

		boolean res;
		for (int i = 0; i < altezza; i++) {
			for (int j = 0; j < larghezza; j++) {
				Cella c = matrice[i][j];
				if (c.size() > 1) {
					//ogni persona può incontrare le persone che vengono dopo di lei nella cella (ovvero in posizioni
					//superiori all'interno della lista delle persone nella cella)
					for (int k = 0; k < c.size(); k++) {
						for (int z = k+1; z < c.size();z++) {
							Persona p1 = c.pos_get(k);
							Persona p2 = c.pos_get(z);
							if (p1.getMovimento() == true || p2.getMovimento() == true) {
								res = incontra(p1, p2); // prendo le due persone scelte dalla fila e le faccio incontrare
								if (res) {
									n_incontrate = n_incontrate + 2;
								}
							}
						}
					}
				}
			}
		}
		return n_incontrate;
	}

	//fa incontrare due persone se nessuna delle due e' nera, fa avvenire un contatto con potenziale contagio nel caso
	//in cui una delle due e' gialla o rossa e l'altra verde
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

}
