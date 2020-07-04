import java.util.ArrayList;
import java.lang.Math.*;

public class Governo {

    //campo usato per verificare se c'è stato un primo individuo sintomatico
    private boolean primoSintomatico;

    //campo usato per verificare se si deve applicare la strategia o meno   //CANCELLA
    //private boolean applicaStrategia;

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

    public Governo() {} //CANCELLA

    // devo passare al costruttore del governo l'oggetto relativo alla strategia che ha scelto l'utente
    public Governo(int risorse, int costo_tampone, Strategia strategia, ArrayList<Persona> persone, Giorno giorno) {
        this.risorse = risorse;
        this.costo_tampone = costo_tampone;
        this.primoSintomatico = false;
        //this.applicaStrategia = true;   //CANCELLA
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
        //non ha senso togliere un nuovo_sintomatico dai nuovi_asintomatici poiché significherebbe che la persona ha fatto il tampone prima di aver chiamato il metodo checkVirus e aver comunicato la sua sintomaticita'
        nuovi_sintomatici.add(p);
        primoSintomatico = true;
    }

    //aggiunge un guarito alla lista dei nuovi_guariti per il giorno corrente (assume che p non sia null)
    public void add_guarito ( Persona p ) {
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non guarita ai nuovi_guariti");
        //non ha senso togliere un nuovo_guarito dai nuovi_asintomatici poiché significherebbe che la persona ha fatto il tampone prima di aver chiamato il metodo checkVirus e aver comunicato la sua guarigione
        nuovi_sintomatici.remove(p);  // ha senso? si se la persona diventa sintomatica lo stesso giorno (ma forse succede solo se la durata è 0) TODO
        if (!(database.getGuariti().contains(p))) { //si può verificare se la persona aveva un giorno in cui doveva comunicare la guarigione ma è diventata rossa ed è guarita prima
            nuovi_guariti.add(p);
        }
    }

    //aggiunge un morto alla lista dei nuovi_morti per il giorno corrente (assume che p non sia null)
    public void add_morto ( Persona p ) {
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non morta ai nuovi_morti");
        //non ha senso togliere un nuovo_morto dai nuovi_asintomatici poiché significherebbe che la persona ha fatto il tampone prima di aver chiamato il metodo checkVirus e aver comunicato la sua morte
        nuovi_sintomatici.remove(p);  // ha senso? si se la persona diventa sintomatica lo stesso giorno
        nuovi_morti.add(p);
    }

    //effettua il tampone alle persone passate come argomento
    public void faiTampone(ArrayList<Persona> persone){
        for(Persona persona : persone){
            if(persona.getVir() != null ) {
                if (persona.getStato() == StatoSalute.GIALLO || persona.getStato() == StatoSalute.ROSSO/*non dovrebbe mai verificarsi*/) {
                    if (persona.getStato() == StatoSalute.ROSSO)
                        throw new IllegalArgumentException("tampone a un rosso!");  //CANCELLA
                    nuovi_asintomatici.add(persona);
                    persona.setGiornoComunicaGuarigione(database.getGiorno().getValore() + (int) Math.ceil(((5 * Virus.getD()) / 6.0))); //altrimenti potrei 'perdermi' qualche giorno
                }
            }
        }
    }

    //ferma le persone passate come argomento
    public void fermaPersone(ArrayList<Persona> persone){
        for(Persona persona : persone){
            persona.setMovimento(false);
            nuove_personeFerme.add(persona);
        }
    }

    //mette in movimento le persone passate come argomento
    public void muoviPersone(ArrayList<Persona> persone){
        for(Persona persona : persone)
            persona.setMovimento(true);
    }


    public void aggiornamento(){

        int numeroSintomatici = database.getSintomatici().size();
        int numeroFermi = database.getPersoneFerme().size();
        risorse = risorse + (numeroSintomatici  * ( -3 * costo_tampone ) + ( -1 * numeroFermi));  // va tolto 1 R per ogni persona ferma +  0 R per quelle morte + 3C R per ogni persona rossa + costo tamponi fatto nel giorno
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

        System.out.println("Nuovi_sintomatici: " + nuovi_sintomatici.size());  //CANCELLA
        System.out.println("Nuovi_guariti: " + nuovi_guariti.size());  //CANCELLA
        System.out.println("Nuovi_morti: " + nuovi_morti.size());  //CANCELLA

        if(primoSintomatico){   //se c'è stato un primo sintomatico
            //devo fare a prescindere setPositivi(nuovi sintomatici) qua poiché altrimenti
            //potrei mantenere nell'hashtable futuri_tamponi della strategia persone su cui dovrei fare il tampone proprio oggi
            //ma che proprio oggi sono diventate sintomatiche, dunque dovendo fare il tampone oggi viene aggiunta alle persone da tamponare,
            //ma non va tamponata! Perché è diventata rossa!
            //stesso discorso per i morti, se una persona diventa rossa e muore nello stesso giorno
            strategia.setPositivi(nuovi_morti);   //si comunicano alla strategia le persone morte (che effettivamente sono positive)
            strategia.setPositivi(nuovi_sintomatici);   //comunica alla strategia i sintomatici del giorno
            //nota: per la strategia 3 e 4 comunicare i nuovi_sintomatici in questo punto permette di evitare che venga fatto il tampone sui sintomatici

            // invoca la strategia scelta
            strategia.applica(database);   //la strategia calcola le persone su cui fare i tamponi e eventualmente alcune da fermare (v. strategia2)
            faiTampone(strategia.getNuovi_tamponi());   //vengono effettuati i tamponi sulle persone individuate dalla strategia
            strategia.setPositivi(nuovi_asintomatici);   //si comunicano alla strategia le persone risultate positive al tampone
            risorse = risorse + (-1 * costo_tampone * strategia.getNuovi_tamponi().size());  //si sottraggono le spese effettuate per i tamponi del giorno
            System.out.println("Tamponi effettuati: " + strategia.getNuovi_tamponi().size());  //CANCELLA
            database.add_asintomatici(nuovi_asintomatici);
            System.out.println("Nuovi asintomatici trovati: " + nuovi_asintomatici.size());
            fermaPersone(strategia.getNuovi_daFermare());  //ferma le persone selezionate dalla stategia tra: morte, sintomatiche, positive al tampone, altre selezionate dalla strategia
            System.out.println("Persone fermate: " + strategia.getNuovi_daFermare().size());
            database.addPersoneFerme(nuove_personeFerme);
        }

        muoviPersone(nuovi_guariti);
        for (Persona p: nuovi_guariti) {
           database.remove_personaFerma(p);
        }
        System.out.println("Persone rimesse in movimento: " + nuovi_guariti.size());
        strategia.pulisci();
        pulisci();
    }

    public void pulisci() {
        nuovi_asintomatici.clear();
        nuovi_sintomatici.clear();
        nuovi_morti.clear();
        nuovi_guariti.clear();
        nuove_personeFerme.clear();
    }

    //getter
    public boolean getPrimoSintomatico() { return this.primoSintomatico; }

    //public boolean getApplicaStrategia() { return this.applicaStrategia; }

    public Strategia getStrategia() {
        return strategia;
    }
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
    public ArrayList<Persona> getNuove_personeFerme() { return nuove_personeFerme; }

    //setter
    public void setStrategia(Strategia strategia) { this.strategia = strategia; }

    public void setCosto_tampone(int costo_tampone) { this.costo_tampone = costo_tampone; }

    public void setRisorse(int risorse) { this.risorse = risorse; }

    public void setDatabase(DBGoverno database) { this.database = database; }

    public void setNuovi_asintomatici(ArrayList<Persona> na) {
        for (Persona p: na) {
            if (p.getStato() != StatoSalute.GIALLO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono asintomatiche");
        }
        this.nuovi_asintomatici = nuovi_asintomatici;
    }

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
