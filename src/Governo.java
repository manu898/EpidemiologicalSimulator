import java.util.ArrayList;
import java.lang.Math.*;

public class Governo {
    //permette di aver un oggetto che rappresenta un governo

    //campo usato per verificare se c'è stato un primo individuo sintomatico
    private boolean primoSintomatico;

    //la strategia utilizzata dal governo durante la simulazione
    private Strategia strategia;

    //le risorse del governo
    private int risorse;

    //il costo del tampone
    private int costo_tampone;

    //variabile di riferimento al database del governo
    private DBGoverno database;

    //le persone fermate giorno per giorno
    private ArrayList<Persona> nuove_personeFerme;

    //le persone asintomatiche rilevate dal governo giorno per giorno
    private ArrayList<Persona> nuovi_asintomatici;

    //le persone diventate sintomatiche giorno per giorno
    private ArrayList<Persona> nuovi_sintomatici;

    //le persone guarite giorno per giorno (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato fatto il tampone e dal giorno del tampone sono passati 5*D/6 giorni)
    private ArrayList<Persona> nuovi_guariti;

    //le persone morte giorno per giorno
    private ArrayList<Persona> nuovi_morti;

    //costruttore
    public Governo(int risorse, int costo_tampone, Strategia strategia, ArrayList<Persona> persone, Giorno giorno) {
        this.risorse = risorse;
        this.costo_tampone = costo_tampone;
        this.primoSintomatico = false;
        database = new DBGoverno();
        database.setPersone(persone);
        database.setGiorno(giorno);
        this.strategia = strategia;
        nuovi_asintomatici = new ArrayList<Persona>();
        nuovi_sintomatici = new ArrayList<Persona>();
        nuovi_guariti = new ArrayList<Persona>();
        nuovi_morti = new ArrayList<Persona>();
        nuove_personeFerme = new ArrayList<Persona>();

    }


    //aggiunge un sintomatico alla lista dei nuovi_sintomatici per il giorno corrente (assume che p non sia null)
    public void add_sintomatico( Persona p ) {
        if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non sintomatica ai nuovi_sintomatici");
        nuovi_sintomatici.add(p);
        primoSintomatico = true;
    }

    //aggiunge un guarito alla lista dei nuovi_guariti per il giorno corrente (assume che p non sia null)
    public void add_guarito ( Persona p ) {
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non guarita ai nuovi_guariti");
        nuovi_sintomatici.remove(p);
        if (!(database.getGuariti().contains(p)) && !(nuovi_guariti.contains(p))) { //si può verificare se la persona aveva un giorno in cui doveva comunicare la guarigione ma è diventata rossa ed è guarita prima (o lo stesso giorno in cui doveva comunicare)
            nuovi_guariti.add(p);
        }
    }

    //aggiunge un morto alla lista dei nuovi_morti per il giorno corrente (assume che p non sia null)
    public void add_morto ( Persona p ) {
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non morta ai nuovi_morti");
        nuovi_sintomatici.remove(p);
        nuovi_morti.add(p);
    }

    //effettua il tampone alle persone passate come argomento e se positive le aggiunge alla lista delle persone risultanti asintomatiche in giornata
    public void faiTampone(ArrayList<Persona> persone){
        for(Persona persona : persone){
            if(persona.getVir() != null ) {
                if (persona.getStato() == StatoSalute.GIALLO || persona.getStato() == StatoSalute.ROSSO/*non dovrebbe mai verificarsi*/) {
                    if (persona.getStato() == StatoSalute.ROSSO) {
                        throw new IllegalArgumentException("tampone a un rosso!");
                    }
                    nuovi_asintomatici.add(persona);
                    persona.setGiornoComunicaGuarigione(database.getGiorno().getValore() + (int) Math.ceil(((5 * Virus.getD()) / 6.0)));
                }
            }
        }
    }

    //ferma le persone passate come argomento e le aggiunge alla lista delle persone fermate in giornata
    public void fermaPersone(ArrayList<Persona> persone){
        for(Persona persona : persone){
            if (persona.getMovimento() == true) {
                persona.setMovimento(false);
                nuove_personeFerme.add(persona);
            }
        }
    }

    //mette in movimento le persone passate come argomento
    public void muoviPersone(ArrayList<Persona> persone){
        for(Persona persona : persone)
            persona.setMovimento(true);
    }

    //aggiorna i dati presenti nel database, applica la strategia, scala le risorse
    public void aggiornamento(){

        int numeroSintomatici = database.getSintomatici().size();
        int numeroFermi = database.getPersoneFerme().size();
        //qui vengono scalate le risorse solo per le persone che sono state sintomatiche e ferme tutto il giorno
        risorse = risorse + (numeroSintomatici  * ( -3 * costo_tampone ) + ( -1 * numeroFermi) + database.getMorti().size());

        for(Persona persona : nuovi_sintomatici){
            database.remove_asintomatico(persona);   //la persona potrebbe non essere tra gli asintomatici
        }
        for(Persona persona : nuovi_guariti){
            database.remove_asintomatico(persona);  //la persona potrebbe non essere tra gli asintomatici
            database.remove_sintomatico(persona);   //la persona potrebbe non essere tra i sintomatici
        }
        for(Persona persona : nuovi_morti){
            database.remove_asintomatico(persona);  //la persona potrebbe non essere tra gli asintomatici (c'è se aveva fatto il tampone e in uno stesso giorno e' diventata sintomatica ed è morta
            database.remove_sintomatico(persona);   //la persona potrebbe non essere tra i sintomatici (se si trova negli asintomatici o se e' diventata sintomatica ed e' morta lo stesso giorno)
        }

        database.add_sintomatici(nuovi_sintomatici);
        database.add_guariti(nuovi_guariti);
        database.add_morti(nuovi_morti);

        //dalla prima volta che compare un sintomatico viene applicata la strategia
        if(primoSintomatico){

            //bisogna chiamare il metodo setPositivi(nuovi sintomatici) qua poiché altrimenti
            //si potrebbe mantenere nell'hashtable futuri_tamponi della strategia (3 e 4) persone su cui si dovrebbe fare il tampone proprio nel giorno corrente
            //ma che nello stesso giorno sono diventate sintomatiche, dunque dovendo fare il tampone nel giorno corrente vengono aggiunte alle persone su cui fare il tampone,
            //ma questo non va effettuato! Perché sono diventate rosse!
            //stesso discorso per i morti, se una persona diventa rossa e muore nello stesso giorno

            strategia.setPositivi(nuovi_morti);   //si comunicano alla strategia le persone morte (che effettivamente sono positive)
            //nota: per la strategia 3 e 4 comunicare i nuovi_morti in questo punto permette di evitare che venga fatto il tampone sui morti

            strategia.setPositivi(nuovi_sintomatici);   //comunica alla strategia i sintomatici del giorno
            //nota: per la strategia 3 e 4 comunicare i nuovi_sintomatici in questo punto permette di evitare che venga fatto il tampone sui sintomatici

            // invoca la strategia scelta
            strategia.applica(database);   //la strategia calcola le persone su cui fare i tamponi e eventualmente alcune da fermare

            faiTampone(strategia.getNuovi_tamponi());   //vengono effettuati i tamponi sulle persone individuate dalla strategia

            strategia.setPositivi(nuovi_asintomatici);   //si comunicano alla strategia le persone risultate positive al tampone

            risorse = risorse + (-1 * costo_tampone * strategia.getNuovi_tamponi().size());  //si sottraggono le spese effettuate per i tamponi del giorno

            database.add_asintomatici(nuovi_asintomatici);

            fermaPersone(strategia.getNuovi_daFermare());  //ferma le persone selezionate dalla stategia tra: morte, sintomatiche, positive al tampone, altre selezionate dalla strategia

            database.addPersoneFerme(nuove_personeFerme);
        }

        muoviPersone(nuovi_guariti);
        for (Persona p: nuovi_guariti) {
           database.remove_personaFerma(p);
        }
    }

    //azzera tutte le liste che presentano dati giornalieri
    public void pulisci() {
        nuovi_asintomatici.clear();
        nuovi_sintomatici.clear();
        nuovi_morti.clear();
        nuovi_guariti.clear();
        nuove_personeFerme.clear();
    }

    //getter
    public Strategia getStrategia() {
        return strategia;
    }

    public int getRisorse() {
        return risorse;
    }

    public DBGoverno getDatabase() { return database; }

    public ArrayList<Persona> getNuovi_asintomatici() { return nuovi_asintomatici; } //TEST

    public ArrayList<Persona> getNuovi_sintomatici() { return nuovi_sintomatici;}

    public ArrayList<Persona> getNuovi_guariti() { return nuovi_guariti; }

    public ArrayList<Persona> getNuovi_morti() { return nuovi_morti; }

    public ArrayList<Persona> getNuove_personeFerme() { return nuove_personeFerme; }


}
