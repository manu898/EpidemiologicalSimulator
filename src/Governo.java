import java.util.ArrayList;

public class Governo {

    //le risorse del governo
    private static int risorse;

    //il costo del tampone
    private static int costo_tampone;

    //variabile di riferimento al database del governo
    private DBGoverno database;

    //le persone diventate sintomatiche giorno per giorno
    private ArrayList<Persona> nuovi_sintomatici;

    //le persone guarite giorno per giorno (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato rifatto il tampone) //CONTROLLA
    private ArrayList<Persona> nuovi_guariti;

    //le persone morte giorno per giorno
    private ArrayList<Persona> nuovi_morti;

    public Governo() {
        database = new DBGoverno();
        nuovi_sintomatici = new ArrayList<Persona>();
        nuovi_guariti = new ArrayList<Persona>();
        nuovi_morti = new ArrayList<Persona>();
    }


    //aggiunge un sintomatico alla lista dei nuovi_sintomatici per il giorno corrente (assume che p non sia null)
    public void add_sintomatico( Persona p ) {
        if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_sintomatici.add(p);
    }

    //aggiunge un guarito alla lista dei nuovi_guariti per il giorno corrente (assume che p non sia null)
    //la persona va tolta da qualche lista? Dei nuovi_sintomatici  o dei sintomatici in DBGoverno?  TODO
    public void add_guarito ( Persona p ) {
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non guarita ai nuovi_guariti");
        nuovi_guariti.add(p);
    }

    //aggiunge un morto alla lista dei nuovi_morti per il giorno corrente (assume che p non sia null)
    //la persona va tolta da qualche lista? Dei nuovi_sintomatici  o dei sintomatici in DBGoverno?   TODO
    public void add_morto ( Persona p ) {
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non morta ai nuovi_morti");
        nuovi_morti.add(p);
    }


    //static getter
    public static int getCosto_tampone() {
        return costo_tampone;
    }
    public static int getRisorse() {
        return risorse;
    }

    //getter
    public DBGoverno getDatabase() { return database; }
    public ArrayList<Persona> getNuovi_sintomatici() { return nuovi_sintomatici;}
    public ArrayList<Persona> getNuovi_guariti() { return nuovi_guariti; }
    public ArrayList<Persona> getNuovi_morti() { return nuovi_morti; }

    //static setter
    public static void setCosto_tampone(int costo_tampone) {
        Governo.costo_tampone = costo_tampone;
    }
    public static void setRisorse(int risorse) {
        Governo.risorse = risorse;
    }

    //setter
    public void setDatabase(DBGoverno database) { this.database = database; }
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
}
