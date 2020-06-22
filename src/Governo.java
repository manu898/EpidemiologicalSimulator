import java.util.ArrayList;

public class Governo {

    private boolean primoSintomatico;  // TEST

    private boolean applicaStrategia;   // TEST

    // il Governo ha un campo relativo alla strategia che a simulazione inizata verrà immesso
    // al suo interno un oggetto della classe relativo alla simulazione
    private Strategia strategia; //TEST

    //le risorse del governo
    private int risorse;

    //il costo del tampone
    private int costo_tampone;

    //variabile di riferimento al database del governo
    private DBGoverno database;

    private ArrayList<Persona> nuove_personeFerme;  //TEST

    //le persone asintomatiche rilevate dal governo
    private ArrayList<Persona> nuovi_asintomatici;

    //le persone diventate sintomatiche giorno per giorno
    private ArrayList<Persona> nuovi_sintomatici;

    //le persone guarite giorno per giorno (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato rifatto il tampone) //CONTROLLA  (le persone che da gialle diventano blu
    //non lo comunicano direttamente al governo, ma bisogna fare un altro tampone su di esse per verificare che sono guarite)
    private ArrayList<Persona> nuovi_guariti;

    //le persone morte giorno per giorno
    private ArrayList<Persona> nuovi_morti;


    // devo passare al costruttore del governo l'oggetto relativo alla strategia che ha scelto l'utente
    public Governo(int risorse, int costo_tampone, Strategia strategia, ArrayList<Persona> persone, Giorno giorno) {
        this.risorse = risorse;
        this.costo_tampone = costo_tampone;
        this.primoSintomatico = false;  // TEST
        this.applicaStrategia = true;   // TEST
        database = new DBGoverno();
        database.setPersone(persone);   //TEST
        database.setGiorno(giorno);   //TEST
        this.strategia = strategia;   //TEST
        nuovi_asintomatici = new ArrayList<Persona>();  //TEST OK
        nuovi_sintomatici = new ArrayList<Persona>();
        nuovi_guariti = new ArrayList<Persona>();
        nuovi_morti = new ArrayList<Persona>();
        nuove_personeFerme = new ArrayList<Persona>();   //TEST

    }


    //aggiunge un sintomatico alla lista dei nuovi_sintomatici per il giorno corrente (assume che p non sia null)
    public void add_sintomatico( Persona p ) {  //TEST OK
        if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_sintomatici.add(p);
        primoSintomatico = true;
    }

    //aggiunge un guarito alla lista dei nuovi_guariti per il giorno corrente (assume che p non sia null)
    public void add_guarito ( Persona p ) {
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non guarita ai nuovi_guariti");
        nuovi_sintomatici.remove(p);  //TEST OK ha senso? si se la persona diventa sintomatica lo stesso giorno
        nuovi_guariti.add(p);
    }

    //aggiunge un morto alla lista dei nuovi_morti per il giorno corrente (assume che p non sia null)
    public void add_morto ( Persona p ) {
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona non morta ai nuovi_morti");
        nuovi_sintomatici.remove(p);  //TEST OK ha senso? si se la persona diventa sintomatica lo stesso giorno
        nuovi_morti.add(p);
        // p.setMovimento(false);  // Qualche minchione ancora ci sta pensando
    }

    //effettua il tampone alle persone passate come argomento
    public void faiTampone(ArrayList<Persona> persone){  //TEST
        for(Persona persona : persone){
            if(persona.getVir() != null )
                if(persona.getStato() != StatoSalute.VERDE) {
                    nuovi_asintomatici.add(persona);
                    persona.setGiornoComunicaGuarigione(database.getGiorno().getValore() + ((5 * Virus.getD()) / 6));
                }
            // comunica alla strategia che c'è un nuovo positivo
        }
    }

    //ferma le persone passate come argomento
    public void fermaPersone(ArrayList<Persona> persone){  //TEST
        for(Persona persona : persone){
            persona.setMovimento(false);
            nuove_personeFerme.add(persona);
        }
    }

    //mette in movimento le persone passate come argomento
    public void muoviPersone(ArrayList<Persona> persone){ //TEST
        for(Persona persona : persone)
            persona.setMovimento(true);
    }


    public void aggiornamento(){   //TEST megatest

        int numeroSintomatici = database.getSintomatici().size();
        int numeroFermi = database.getPersoneFerme().size();
        risorse = risorse + (numeroSintomatici  * ( -3 * costo_tampone ) + ( -1 * numeroFermi));  // va tolto 1 R per ogni persona ferma +  0 R per quelle morte + 3C R per ogni persona rossa + costo tamponi fatto nel giorno
        //vanno tolti i tamponi (dopo aver chiamato la strategia)
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

        database.add_sintomatici(nuovi_sintomatici);
        database.add_guariti(nuovi_guariti);
        database.add_morti(nuovi_morti);



        if(primoSintomatico){
            if(applicaStrategia){
                //se sfrutto il metodo setPositivi(nuovi sintomatici) qua, per la strategia4 non c'e' bisogno
                //di usare il metodo setNuovi_sintomatici(nuovi_sintomatici)
                strategia.setNuovi_sintomatici(nuovi_sintomatici);
                // invoca la strategia scelta
                applicaStrategia = strategia.applica(database);
                faiTampone(strategia.getNuovi_tamponi());
                strategia.setPositivi(nuovi_asintomatici);
                strategia.setPositivi(nuovi_sintomatici);
                risorse = risorse + (-1 * costo_tampone * strategia.getNuovi_tamponi().size());
                fermaPersone(strategia.getNuovi_daFermare());
            }
        }

        database.add_asintomatici(nuovi_asintomatici);
        fermaPersone(nuovi_morti);
        //fermaPersone(nuovi_sintomatici);
        database.addPersoneFerme(nuove_personeFerme);
        muoviPersone(nuovi_guariti);
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
