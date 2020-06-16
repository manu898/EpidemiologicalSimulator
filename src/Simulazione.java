import java.util.ArrayList;

public class Simulazione {
    //il giorno della simulazione
    private int giorno;
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


    public Simulazione( Governo governo, Arena arena, int popolazione, double velocita ) {
        giorno = 1;
        this.governo = governo;
        this.arena = arena;
        this.persone = new ArrayList<Persona>(popolazione);
        init_persone(popolazione);
        //scelgo la prima persona gialla
        //TEST
        Persona primo_giallo = persone.get(0);
        primo_giallo.setVir(new Virus(this));
        primo_giallo.setStato(StatoSalute.GIALLO);
        primo_giallo.setMustcheckvirus(true);
        primo_giallo.getVir().calcola_giornoDadoS();
        //TEST
        arena.distribuisciPersone(persone);
        this.velocita = velocita;
        R0 = velocita * Virus.getD() * Virus.getI(); //TEST
        perc_mov = velocita * 100 / popolazione;   //TEST
    }

    //esegui la simulazione per 'giorni' giorni
    public boolean run(int giorni) {     //TEST
        for (int i = 0; i < giorni ; i++) {
            verdi_sani = 0;
            gialli = 0;
            rossi = 0;
            neri = 0;
            blu = 0;
            int in_movimento = 0;
            for (Persona p : persone) {
                if (p.getMovimento())
                    in_movimento++;
            }
            velocita = in_movimento * perc_mov / 100;
            int n_incontrate = 0;
            while (n_incontrate / in_movimento < velocita) {
                arena.move(persone);
                n_incontrate += arena.check_incontri();
            }
            for (Persona p: persone) {
                p.checkVirus();
                check_stato(p);
            }
            //governo.controlla()
            if (risorse_finite() || vittoria_malattia() || (verdi_sani + blu + neri == getPopolazione()) )
                return false;
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
    private void check_stato(Persona p) {  //TEST
        switch (p.getStato()) {
            case VERDE:
                if (p.getVir() != null)
                    verdi_sani++;
                break;
            case GIALLO:
                gialli++;
                break;
            case ROSSO:
                rossi++;
                break;
            case NERO:
                neri++;
                break;
            case BLU:
                blu++;
                break;
        }
    }

    //crea le persone
    private void init_persone(int popolazione) {
        for (int i = 0; i < popolazione; i++) {
            persone.add(new Persona(i, governo, this));
        }
    }

    //fornisce il numero di persone della simulazione
    public int getPopolazione() {
        return persone.size();
    }


    //getter
    public int getGiorno() { return giorno; }

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
    public void setGiorno(int g) { giorno = g; }

    public void setVelocita(double velocita) {
        velocita = velocita;
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
