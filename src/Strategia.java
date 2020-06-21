import java.util.ArrayList;

public abstract class Strategia {

    public ArrayList<Persona> positivi = new ArrayList<>();
    public ArrayList<Persona> nuovi_tamponi;
    public ArrayList<Persona> nuovi_daFermare;

    public void setPositivi(ArrayList<Persona> positivi) {
        this.positivi = positivi;
    }

    public abstract boolean applica(DBGoverno dbGoverno);

    public ArrayList<Persona> getNuovi_tamponi() { return nuovi_tamponi; }
    public ArrayList<Persona> getNuovi_daFermare() { return nuovi_daFermare; }

}
