import java.util.ArrayList;

public abstract class Strategia {
    //classe astratta che rappresenta in modo astratto una strategia e definisce metodi e variabili che
    //le classi che la implementano devono sfruttare/implementare/sovrascrivere

    //le persone che risultano positive al tampone
    public ArrayList<Persona> positivi = new ArrayList<>();

    //le persone che devono fare il tampone
    public ArrayList<Persona> nuovi_tamponi = new ArrayList<>();

    //le persone che vanno fermate
    public ArrayList<Persona> nuovi_daFermare = new ArrayList<>();

    //copia dei nuovi_sintomatici del governo
    public ArrayList<Persona> nuovi_sintomatici = new ArrayList<>();

    //setta le persone positive al tampone, nelle varie implementazioni delle sottoclassi stabilisce alcune persone da fermare
    public void setPositivi(ArrayList<Persona> positivi) {
        this.positivi = positivi;
    }

    //setta i nuovi_sintomatici
    public void setNuovi_sintomatici(ArrayList<Persona> nuovi_sintomatici) {
        this.nuovi_sintomatici = nuovi_sintomatici;
    }

    //azzera tutte le liste di questa classe
    public void pulisci() {
        positivi.clear();
        nuovi_tamponi.clear();
        nuovi_daFermare.clear();
        nuovi_sintomatici.clear();
    }

    //applica la strategia per calcolare le persone su cui fare i tamponi e alcune da fermare
    public abstract void applica(DBGoverno dbGoverno);

    //ritorna la lista delle persone su cui fare il tampone
    public ArrayList<Persona> getNuovi_tamponi() { return nuovi_tamponi; }

    //ritorna la lista delle persone da fermare
    public ArrayList<Persona> getNuovi_daFermare() { return nuovi_daFermare; }

}
