import java.util.ArrayList;

public class Simulazione {
    //il giorno della simulazione
    private Giorno giorno;  //TEST

    //la velocita delle persone (numero medio di incontri in un giorno)
    private double velocita;

    //la lista di tutte le persone
    private ArrayList<Persona> persone;

    //il governo che agisce nella simulazione
    private Governo governo;

    //l'arena in cui si muovo e vivono le persone
    private Arena arena;

    //il fattore di contagiosita' R0
    private double R0;

    //la percentuale di persone da incontrare rispettando il valore velocita, calcolata sulle persone in movimento
    private double perc_mov;

    //potrebbero essere solo variabili locali nel metodo run
    //il numero di persone verdi e che non hanno contratto il virus
    private int verdi_sani;

    //il numero di asintomatici
    private int gialli;

    //il numero di sintomatici
    private int rossi;

    //il numero di morti
    private int neri;

    //il numero di guariti
    private int blu;

    private DatiStatistici statistiche;  // TEST

    //costruttore, si occupa di inizializzare anche le persone, il governo e l'arena
    public Simulazione(ParametriSimulazione par) {
        statistiche = new DatiStatistici();  // TEST
        giorno = new Giorno(1);
        Virus.setI(par.getInfettivita());
        Virus.setS(par.getSintomaticita());
        Virus.setL(par.getLetalita());
        Virus.setD(par.getDurata());
        this.persone = new ArrayList<Persona>(par.getPopolazione());
        governo = new Governo(par.getRisorse(), par.getCosto_tampone(), par.getStrategia(), persone, giorno);
        init_persone(par.getPopolazione());
        //TEST
        Persona primo_giallo = persone.get(0);
        primo_giallo.setVir(new Virus(giorno));
        primo_giallo.setStato(StatoSalute.GIALLO);
        primo_giallo.getVir().setGiornoFineIncubazione(giorno.getValore());
        primo_giallo.setMustcheckvirus(true);
        primo_giallo.getVir().calcola_giornoDadoS();
        //TEST

        arena = new Arena(par.getArenaH(), par.getArenaL(), par.getSpostamentoMax());
        arena.distribuisciPersone(persone);
        velocita = par.getVelocita();
        R0 = velocita * Virus.getD() * Virus.getI();
        perc_mov = velocita * 100 / getPopolazione();

    }

    public DatiStatistici getDati(){  // TEST
        return statistiche;
    }

    public void aggiornaDati(){  // TEST

        // fare arrayList per frase e risorseRimaste
        statistiche.risorseRimaste.add(governo.getRisorse());
        statistiche.morti.add(governo.getDatabase().getMorti().size());
        statistiche.sintomatici.add(governo.getDatabase().getSintomatici().size());
        statistiche.asintomaticiGoverno.add(governo.getDatabase().getAsintomatici().size());
        statistiche.asintomaticiSimulazione.add(governo.getDatabase().getAsintomatici().size());
        statistiche.guaritiGoverno.add(governo.getDatabase().getGuariti().size());
        statistiche.guaritiSimulazione.add(governo.getDatabase().getGuariti().size());
        String risultato = "Tutto bene";
        if(vittoria_malattia())
            risultato = "Ha vinto il virus !";
        if(risorse_finite())
            risultato = "Sono finite le risorse";
        statistiche.risultato.add(risultato);

        // aggiungere i verdi sia di simulazione che di governo
        // e calcolare in modo diverso i guaritiSimulazione e gli asintomaticiSimulazione
    }

    //esegui la simulazione per 'giorni' giorni
    public boolean run(int giorni) {     //TEST
        for (int i = 0; i < giorni ; i++) {
            verdi_sani = 0;
            gialli = 0;
            rossi = 0;
            neri = 0;
            blu = 0;
            int in_movimento = 0;        //TEST OK
            for (Persona p : persone) {
                if (p.getMovimento())
                    in_movimento++;
            }
            setVelocita(in_movimento * perc_mov / 100);  //TEST OK
            R0 = velocita * Virus.getD() * Virus.getI(); //TEST OK
            int n_incontrate = 0;
            while (n_incontrate / in_movimento < velocita) { //TEST  OK
                arena.move(persone);    //TEST OK
                n_incontrate += arena.check_incontri(); //TEST OK
            }
            for (Persona p: persone) { //TEST
                p.checkVirus();
                check_stato(p);
            }
            governo.aggiornamento(); //TEST
            aggiornaDati();  // TEST
            if (risorse_finite() || vittoria_malattia() || (verdi_sani + blu + neri == getPopolazione()) )  //TEST OK
                return false;
            giorno.incrementa(1);
        }
        return true;
    }

    //verifica se la malattia ha vinto
    public boolean vittoria_malattia() {   //TEST
        return (neri == getPopolazione());
    }

    //verifica se sono finite le risorse
    public boolean risorse_finite() {     //TEST
        return (governo.getRisorse() <= 0);
    }

    //controlla lo StatoSalute di una persona e aumenta il contatore relativo
    private void check_stato(Persona p) {  //TEST OK
        switch (p.getStato()) {
            case VERDE:
                if (p.getVir() == null)
                    verdi_sani++;
                break;
            case GIALLO:  //assume che le persone abbiano il virus
                gialli++;
                break;
            case ROSSO:   //assume che le persone abbiano il virus
                rossi++;
                break;
            case NERO:    //assume che le persone abbiano il virus
                neri++;
                break;
            case BLU:     //assume che le persone abbiano avuto il virus
                blu++;
                break;
        }
    }

    //crea le persone
    private void init_persone(int popolazione) {   //TEST  OK
        for (int i = 0; i < popolazione; i++) {
            persone.add(new Persona(i, governo, giorno));
        }
    }

    //fornisce il numero di persone della simulazione
    public int getPopolazione() {
        return persone.size();
    }


    //getter
    public Giorno getGiorno() { return giorno; }

    public double getVelocita() {
        return velocita;
    }

    public ArrayList<Persona> getPersone() { return persone; }

    public Governo getGoverno() { return governo; }

    public Arena getArena() { return arena; }

    public double getR0() { return R0; }

    public double getPerc_mov() { return perc_mov; }

    public int getVerdi_sani() { return verdi_sani; }

    public int getGialli() { return gialli; }

    public int getRossi() { return rossi; }

    public int getNeri() { return neri; }

    public int getBlu() { return blu; }

    //setter
    public void setGiorno(Giorno giorno) { this.giorno = giorno; }

    public void setVelocita(double velocita) {
        if (velocita < 0) throw new IllegalArgumentException("La velocita' non puo' essere negativa");
        this.velocita = velocita;
    }

    public void setPersone(ArrayList<Persona> persone) { this.persone = persone; }

    public void setGoverno(Governo governo) { this.governo = governo; }

    public void setArena(Arena arena) { this.arena = arena; }

    public void setR0(double R0) { this.R0 = R0; }

    public void setPerc_mov(double perc_mov) { this.perc_mov = perc_mov; }

    public void setVerdi_sani(int verdi_sani) { this.verdi_sani = verdi_sani; }

    public void setGialli(int gialli) { this.gialli = gialli; }

    public void setRossi(int rossi) { this.rossi = rossi; }

    public void setNeri(int neri) { this.neri = neri; }

    public void setBlu(int blu) { this.blu = blu; }
}
