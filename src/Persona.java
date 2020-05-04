import java.util.ArrayList;
import java.util.Random;

public class Persona {

    public int ID;

    public Coppia posizione;

    public boolean inMovimento;

	public Stato stato = Stato.VERDE;

	public boolean inIncubazione;

	public int periodoIncubazione;

    public boolean inCura;



    //public ArrayList<Persona> incontrate;

    public Persona(int ID, int y, int x){
        this.ID = ID;
        posizione = new Coppia(y, x);
		inMovimento = true;
		inIncubazione = false;
		periodoIncubazione = -1;
		inCura = false;
        //incontrate = new ArrayList<Persona>();
    }

    public void azzera(){

        //incontrate.clear();

    }

    //public int a;




}
