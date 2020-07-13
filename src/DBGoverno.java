import java.util.ArrayList;

public class DBGoverno {
    //permette di aver un oggetto che rappresenta il database del governo, con tutti i dati piu' importanti che gli possono servire

    //riferimento al giorno attuale della simulazione
    private Giorno giorno;

    //la lista delle persone nella simulazione
    private ArrayList<Persona> persone;

    //le persone ferme nella simulazione
    private ArrayList<Persona> personeFerme;

    //la lista delle persone asintomatiche rilevate dal governo
    private ArrayList<Persona> asintomatici;

    //la lista di tutti i sintomatici durante la simulazione
    private ArrayList<Persona> sintomatici;

    //il numero di tutti i morti durante la simulazione
    private ArrayList<Persona> morti;

    //il numero di tutti i guariti durante la simulazione (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato fatto il tampone e dal giorno del tampone sono passati 5*D/6 giorni)
    private ArrayList<Persona> guariti;

    //costruttore
    public DBGoverno() {
        asintomatici = new ArrayList<Persona>();
        sintomatici = new ArrayList<Persona>();
        morti = new ArrayList<Persona>();
        guariti = new ArrayList<Persona>();
        personeFerme = new ArrayList<Persona>();
    }


    //rimuovi una persona dalla lista degli asintomatici (assume che p era asintomatico e quindi si trova nella lista)
    public void remove_asintomatico(Persona p){
        asintomatici.remove(p); }

    //rimuovi una persona dalla lista dei sintomatici (assume che p era sintomatico e quindi si trova nella lista)
    public void remove_sintomatico(Persona p) {
        sintomatici.remove(p);
    }

    //rimuovi una persona dalla lista delle persone ferme (assume che p sia stato rimesso in movimento)
    public void remove_personaFerma(Persona p) { personeFerme.remove(p); }

    //aggiorna la lista delle persone ferme
    public void addPersoneFerme(ArrayList<Persona> persone){  personeFerme.addAll(persone); }

    //aggiorna la lista degli asintomatici (assume che ps sia di soli asintomatici)
    public void add_asintomatici(ArrayList<Persona> ps){   //TEST
        asintomatici.addAll(ps);
    }

    //aggiorna la lista dei sintomatici (assume che ps sia di soli sintomatici)
    public void add_sintomatici(ArrayList<Persona> ps){  //TEST
        sintomatici.addAll(ps);
    }

    //aggiorna la lista dei morti (assume che pm sia di soli morti)
    public void add_morti(ArrayList<Persona> pm) { morti.addAll(pm); }

    //aggiorna la lista dei guariti (assume che pg sia di soli guariti)
    public void add_guariti(ArrayList<Persona> pg){  guariti.addAll(pg); }

    //getter
    public ArrayList<Persona> getPersone() { return persone; }
    public ArrayList<Persona> getAsintomatici() { return asintomatici; }
    public ArrayList<Persona> getSintomatici() { return sintomatici; }
    public ArrayList<Persona> getMorti() { return morti; }
    public ArrayList<Persona> getGuariti() { return guariti; }
    public ArrayList<Persona> getPersoneFerme() { return personeFerme; }
    public Giorno getGiorno() { return giorno; }


    //setter
    public void setPersone( ArrayList<Persona> persone) { this.persone = persone; }

    public void setGiorno( Giorno giorno ) { this.giorno = giorno; }
}
