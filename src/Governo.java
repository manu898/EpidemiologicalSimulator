import java.util.ArrayList;

public class Governo {

    // il Governo ha un campo relativo alla strategia che a simulazione inizata verrà immesso
    // al suo interno un oggetto della classe relativo alla simulazione
    private Strategia strategia;

    //le risorse del governo
    private int risorse;

    //il costo del tampone
    private int costo_tampone;

    //variabile di riferimento al database del governo
    private DBGoverno database;

    //le persone asintomatiche rilevate dal governo
    private ArrayList<Persona> nuovi_asintomatici;   //TEST

    //le persone diventate sintomatiche giorno per giorno
    private ArrayList<Persona> nuovi_sintomatici;

    //le persone guarite giorno per giorno (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato rifatto il tampone) //CONTROLLA  (le persone che da gialle diventano blu
    //non lo comunicano direttamente al governo, ma bisogna fare un altro tampone su di esse per verificare che sono guarite)
    private ArrayList<Persona> nuovi_guariti;

    //le persone morte giorno per giorno
    private ArrayList<Persona> nuovi_morti;

    // devo passare al costruttore del governo l'oggetto relativo alla strategia che ha scelto l'utente
    public Governo(int risorse, int costo_tampone, Strategia strategia) {
        this.risorse = risorse;
        this.costo_tampone = costo_tampone;
        database = new DBGoverno();
        nuovi_asintomatici = new ArrayList<Persona>();  //TEST
        nuovi_sintomatici = new ArrayList<Persona>();
        nuovi_guariti = new ArrayList<Persona>();
        nuovi_morti = new ArrayList<Persona>();
        this.strategia = strategia;
    }


    //aggiunge un sintomatico alla lista dei nuovi_sintomatici per il giorno corrente (assume che p non sia null)
    public void add_sintomatico( Persona p ) {
        if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_asintomatici.remove(p); //TEST ha senso?
        nuovi_sintomatici.add(p);
    }

    //aggiunge un guarito alla lista dei nuovi_guariti per il giorno corrente (assume che p non sia null)
    public void add_guarito ( Persona p ) {
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non guarita ai nuovi_guariti");
        nuovi_sintomatici.remove(p);  //TEST ha senso?
        nuovi_asintomatici.remove(p);  //TEST ha senso?
        nuovi_guariti.add(p);
    }

    //aggiunge un morto alla lista dei nuovi_morti per il giorno corrente (assume che p non sia null)
    public void add_morto ( Persona p ) {
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non morta ai nuovi_morti");
        nuovi_sintomatici.remove(p);  //TEST ha senso?
        nuovi_morti.add(p);
    }


    //getter
    public int getCosto_tampone() {
        return costo_tampone;
    }
    public int getRisorse() {
        return risorse;
    }
    public DBGoverno getDatabase() { return database; }

    public ArrayList<Persona> getNuovi_asintomatici() { return nuovi_asintomatici; } //TEST

    public ArrayList<Persona> getNuovi_sintomatici() { return nuovi_sintomatici;}
    public ArrayList<Persona> getNuovi_guariti() { return nuovi_guariti; }
    public ArrayList<Persona> getNuovi_morti() { return nuovi_morti; }

    //setter
    public void setCosto_tampone(int costo_tampone) { this.costo_tampone = costo_tampone; }

    public void setRisorse(int risorse) { this.risorse = risorse; }

    public void setDatabase(DBGoverno database) { this.database = database; }

    public void setNuovi_asintomatici(ArrayList<Persona> nuovi_asintomatici) { this.nuovi_asintomatici = nuovi_asintomatici; }

    public void setNuovi_sintomatici(ArrayList<Persona> ns ) {
        for (Persona p: ns){
            if (p.getStato() != StatoSalute.ROSSO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono sintomatiche");
        }
        nuovi_sintomatici = ns;
    }

    public void setNuovi_guariti(ArrayList<Persona> ng) {
        for (Persona p: ng){
            if (p.getStato() != StatoSalute.BLU) throw new IllegalArgumentException("Non tutte le persone aggiunte sono guarite");
        }
        nuovi_guariti = ng;
    }

    public void setNuovi_morti(ArrayList<Persona> nm) {
        for (Persona p: nm){
            if (p.getStato() != StatoSalute.NERO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono morte");
        }
        nuovi_morti = nm;
    }

    public void aggiornamento(){   //TEST megatest

        int numeroSintomatici = database.getSintomatici().size();
        // servono le persone ferme e le persone morte ... aggiungo ... sei un uomo morto TODO
        risorse = risorse + (numeroSintomatici  * ( -3 * costo_tampone ));  // va tolto 1 R per ogni persona ferma +  0 R per quelle morte + 3C R per ogni persona rossa + costo tamponi fatto nel giorno

        for(Persona persona : nuovi_sintomatici){
            database.remove_asintomatico(persona);
        }

        for(Persona persona : nuovi_guariti){
            database.remove_asintomatico(persona); // se sta cosa genera eccezzioni va dato un PUGNO a qualcuno che non faccio nome TODO
            database.remove_sintomatico(persona);
        }


        for(Persona persona : nuovi_morti){
            database.remove_sintomatico(persona);
        }

        database.add_asintomatici(nuovi_asintomatici);
        database.add_sintomatici(nuovi_sintomatici);
        database.add_guariti(nuovi_guariti.size());
        database.add_morti(nuovi_morti.size());



    }
}
