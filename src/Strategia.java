import java.util.ArrayList;

public abstract class Strategia {
    public ArrayList<Persona> nuovi_tamponi;
    public ArrayList<Persona> nuovi_daFermare;

    public abstract void applica();

    public ArrayList<Persona> getNuovi_tamponi() { return nuovi_tamponi; }
    public ArrayList<Persona> getNuovi_daFermare() { return nuovi_daFermare; }

}
