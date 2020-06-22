import java.util.ArrayList;

public abstract class Strategia {

    public ArrayList<Persona> positivi = new ArrayList<>();
    public ArrayList<Persona> nuovi_tamponi;
    public ArrayList<Persona> nuovi_daFermare;
    public ArrayList<Persona> nuovi_sintomatici = new ArrayList<>();

    public void setPositivi(ArrayList<Persona> positivi) {
        this.positivi = positivi;
    }

    public void setNuovi_sintomatici(ArrayList<Persona> nuovi_sintomatici) {
        this.nuovi_sintomatici = nuovi_sintomatici;
    }

    public void pulisci() {
        positivi.clear();
        nuovi_tamponi.clear();
        nuovi_daFermare.clear();
        nuovi_sintomatici.clear();
    }

    public abstract boolean applica(DBGoverno dbGoverno);

    public ArrayList<Persona> getNuovi_tamponi() { return nuovi_tamponi; }
    public ArrayList<Persona> getNuovi_daFermare() { return nuovi_daFermare; }

}
