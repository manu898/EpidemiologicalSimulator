import java.util.Random;
import java.util.ArrayList;
public class Arena {

	public int altezza;
	public int larghezza;
	public Cella[][] matrice;
	public Random r;
	public int spostamentoMax = 10;
	public int prova;

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
				int y = persona.getPosizione().getY();
				int x = persona.getPosizione().getX();

				// in ogni cella abbiamo una lista di Persone
				matrice[y][x].remove(persona.getID());

				if(y >= spostamentoMax && altezza - y >= spostamentoMax){
					int low = - spostamentoMax;
					int high = spostamentoMax;
					int result = r.nextInt(high - low) + low;
				}else if(y < spostamentoMax && altezza - y >= spostamentoMax){
					int low = - y;
					int high = spostamentoMax;
					int result = r.nextInt(high - low) + low;
				}else if(y >= spostamentoMax && altezza - y < spostamentoMax){
					int low = spostamentoMax;
					int high = altezza - y;
					int result = r.nextInt(high - low) + low;
				}else{
					int low = - y;
					int high = altezza - y;
					int result = r.nextInt(high - low) + low;

				}

				if(x >= spostamentoMax && larghezza - x >= spostamentoMax){
					int low = - spostamentoMax;
					int high = spostamentoMax;
					int result = r.nextInt(high - low) + low;
				}else if(x < spostamentoMax && larghezza - x >= spostamentoMax){
					int low = - x;
					int high = spostamentoMax;
					int result = r.nextInt(high - low) + low;
				}else if(x >= spostamentoMax && larghezza - x < spostamentoMax){
					int low = spostamentoMax;
					int high = larghezza - x;
					int result = r.nextInt(high - low) + low;
				}else{
					int low = - x;
					int high = altezza - x;
					int result = r.nextInt(high - low) + low;
				}


				matrice[y][x].add(persona);
				persona.setPosizione(y,x);

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
}
