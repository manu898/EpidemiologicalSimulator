import java.util.ArrayList;
import java.util.Random;

public class Persona {

    public int ID;

    Coppia posizione;

    public boolean movimento;

    public boolean inCura;

    ArrayList<Persona> incontrate;

    public Persona(int ID, int y, int x){
        this.ID = ID;
        posizione = new Coppia(y, x);
        incontrate = new ArrayList<Persona>();
    }

    public void azzera(){

        incontrate.clear();

    }

    public int a;




}
